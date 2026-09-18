package com.example.data.model

enum class ErrorCategory(val displayName: String, val badgeColorHex: Long) {
    NETWORK("Network Issue", 0xFFFF5252),
    API_RATE_LIMIT("Rate Limit (429)", 0xFFFFAB00),
    API_SERVER("AI Server (500/503)", 0xFFFF7043),
    API_AUTH("Auth / Key (401/403)", 0xFFE040FB),
    APP_RUNTIME("App Runtime / Crash", 0xFFFF1744),
    TIMEOUT("Request Timeout", 0xFFFF9100),
    OFFLINE_BLOCKED("Offline Execution", 0xFF00E5FF)
}

enum class ErrorRecoveryAction {
    AUTO_RETRY_EXPONENTIAL,
    PROVIDER_FALLBACK,
    SWITCH_TO_OFFLINE_MODE,
    CACHE_HEURISTIC_REPLY,
    TOKEN_REFRESH,
    USER_PROMPT
}

data class ErrorIncident(
    val id: String,
    val timestamp: Long = System.currentTimeMillis(),
    val category: ErrorCategory,
    val title: String,
    val description: String,
    val endpointOrTarget: String,
    val recoveryAction: ErrorRecoveryAction,
    val isResolved: Boolean = false,
    val resolutionMessage: String = "",
    val attemptCount: Int = 1
)

enum class NetworkStateStatus(val label: String) {
    ONLINE("ONLINE • 5G / High-Speed"),
    DEGRADED("DEGRADED • High Latency / Packet Loss"),
    OFFLINE("OFFLINE • No Connectivity"),
    SIMULATED_OFFLINE("OFFLINE SIMULATION ACTIVE")
}

data class ProviderFallbackNode(
    val providerId: String,
    val providerName: String,
    val modelId: String,
    val priorityOrder: Int,
    val isAvailable: Boolean = true,
    val healthScore: Int = 100, // 0 - 100
    val lastError: String? = null
)

data class RetryPolicyConfig(
    val maxRetries: Int = 3,
    val initialDelayMs: Long = 1000L,
    val backoffMultiplier: Double = 2.0,
    val maxDelayMs: Long = 8000L,
    val isAutoRetryEnabled: Boolean = true,
    val isProviderFallbackEnabled: Boolean = true,
    val isAutoOfflineFallbackEnabled: Boolean = true
)

data class AuthSessionState(
    val isAuthenticated: Boolean = true,
    val username: String = "Commander Yousof",
    val email: String = "yousof81816@gmail.com",
    val role: String = "Chief AI Architect",
    val tokenPreview: String = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.jarvis_sec_v2",
    val tokenExpiresInSeconds: Long = 3600L,
    val isBiometricsActive: Boolean = true,
    val secureStorageStatus: String = "Hardware Keystore Encrypted (AES-256-GCM)"
)

data class OfflineCacheCapability(
    val id: String,
    val title: String,
    val localRuleCount: Int,
    val vectorEmbeddingCacheSize: String,
    val isReady: Boolean = true
)
