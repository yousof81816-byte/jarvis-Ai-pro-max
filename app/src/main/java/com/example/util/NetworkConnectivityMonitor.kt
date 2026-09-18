package com.example.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.data.model.NetworkStateStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkConnectivityMonitor(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    private val _networkStatus = MutableStateFlow(NetworkStateStatus.ONLINE)
    val networkStatus: StateFlow<NetworkStateStatus> = _networkStatus.asStateFlow()

    private val _isSimulatedOffline = MutableStateFlow(false)
    val isSimulatedOffline: StateFlow<Boolean> = _isSimulatedOffline.asStateFlow()

    private val _isOfflineModeActive = MutableStateFlow(false)
    val isOfflineModeActive: StateFlow<Boolean> = _isOfflineModeActive.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            updateStatus()
        }

        override fun onLost(network: Network) {
            updateStatus()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            updateStatus()
        }
    }

    init {
        try {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager?.registerNetworkCallback(request, networkCallback)
        } catch (e: Exception) {
            // Fallback for isolated runtime environments
        }
        updateStatus()
    }

    private fun updateStatus() {
        if (_isSimulatedOffline.value) {
            _networkStatus.value = NetworkStateStatus.SIMULATED_OFFLINE
            return
        }

        try {
            val activeNetwork = connectivityManager?.activeNetwork
            val capabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)
            val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

            if (isConnected) {
                _networkStatus.value = NetworkStateStatus.ONLINE
            } else {
                _networkStatus.value = NetworkStateStatus.OFFLINE
            }
        } catch (e: Exception) {
            _networkStatus.value = NetworkStateStatus.ONLINE
        }
    }

    fun setSimulateOffline(simulate: Boolean) {
        _isSimulatedOffline.value = simulate
        if (simulate) {
            _networkStatus.value = NetworkStateStatus.SIMULATED_OFFLINE
            _isOfflineModeActive.value = true
        } else {
            updateStatus()
            _isOfflineModeActive.value = false
        }
    }

    fun toggleOfflineAutonomousMode(enabled: Boolean) {
        _isOfflineModeActive.value = enabled
    }

    fun isOnline(): Boolean {
        if (_isSimulatedOffline.value) return false
        return _networkStatus.value == NetworkStateStatus.ONLINE
    }
}
