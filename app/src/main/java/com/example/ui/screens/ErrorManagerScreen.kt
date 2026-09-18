package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ErrorCategory
import com.example.data.model.ErrorIncident
import com.example.data.model.NetworkStateStatus
import com.example.data.model.ProviderFallbackNode
import com.example.ui.theme.JarvisAmber
import com.example.ui.theme.JarvisCyan
import com.example.ui.theme.JarvisCyanGlow
import com.example.ui.theme.JarvisDarkBackground
import com.example.ui.theme.JarvisDarkBorder
import com.example.ui.theme.JarvisDarkCard
import com.example.ui.theme.JarvisDarkSurface
import com.example.ui.theme.JarvisElectricBlue
import com.example.ui.theme.JarvisEmerald
import com.example.ui.theme.JarvisRuby
import com.example.ui.theme.JarvisTextMuted
import com.example.ui.theme.JarvisTextPrimary
import com.example.ui.theme.JarvisTextSecondary
import com.example.ui.theme.RouterColor
import com.example.ui.viewmodel.JarvisViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ErrorManagerScreen(
    viewModel: JarvisViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview & Flow", "Simulator & Test", "Provider Cascade", "Incident Logs", "Auth & Session")

    val networkStatus by viewModel.networkStatus.collectAsState()
    val isSimulatedOffline by viewModel.isSimulatedOffline.collectAsState()
    val isOfflineModeActive by viewModel.isOfflineModeActive.collectAsState()
    val errorIncidents by viewModel.errorIncidents.collectAsState()
    val providerCascade by viewModel.providerCascade.collectAsState()
    val activeProviderIndex by viewModel.activeProviderIndex.collectAsState()
    val retryPolicy by viewModel.retryPolicy.collectAsState()
    val isAutoRetrying by viewModel.isAutoRetrying.collectAsState()
    val retryCountdown by viewModel.retryCountdown.collectAsState()
    val authSession by viewModel.authSessionState.collectAsState()
    val offlineCaps by viewModel.offlineCapabilities.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_radar")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "radar_alpha"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(JarvisDarkBackground)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JarvisDarkSurface)
                .border(1.dp, JarvisDarkBorder, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = JarvisTextPrimary
                )
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(JarvisAmber.copy(alpha = 0.2f))
                    .border(1.dp, JarvisAmber, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BugReport,
                    contentDescription = "Error Manager",
                    tint = JarvisAmber,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ERROR MANAGER & RESILIENCY",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisTextPrimary
                )
                Text(
                    text = "Auto-Retry • Provider Fallback • Offline Mode • Zero-Crash",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = JarvisAmber
                )
            }

            // Live Network Status Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when {
                            isOfflineModeActive -> JarvisAmber.copy(alpha = 0.2f)
                            networkStatus == NetworkStateStatus.ONLINE -> JarvisEmerald.copy(alpha = 0.2f)
                            else -> JarvisRuby.copy(alpha = 0.2f)
                        }
                    )
                    .border(
                        1.dp,
                        when {
                            isOfflineModeActive -> JarvisAmber
                            networkStatus == NetworkStateStatus.ONLINE -> JarvisEmerald
                            else -> JarvisRuby
                        },
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isOfflineModeActive -> JarvisAmber
                                    networkStatus == NetworkStateStatus.ONLINE -> JarvisEmerald
                                    else -> JarvisRuby
                                }
                            )
                    )
                    Text(
                        text = if (isOfflineModeActive) "OFFLINE MODE" else if (networkStatus == NetworkStateStatus.ONLINE) "ONLINE" else "OFFLINE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = when {
                            isOfflineModeActive -> JarvisAmber
                            networkStatus == NetworkStateStatus.ONLINE -> JarvisEmerald
                            else -> JarvisRuby
                        }
                    )
                }
            }
        }

        // Subsystem Tab Navigation
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = JarvisDarkSurface,
            contentColor = JarvisCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = JarvisAmber,
                    height = 2.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            ),
                            color = if (selectedTab == index) JarvisAmber else JarvisTextSecondary
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    // ARCHITECTURE OVERVIEW & LIVE STATUS
                    item {
                        ErrorManagerFlowDiagramCard(
                            networkStatus = networkStatus,
                            isOfflineModeActive = isOfflineModeActive,
                            activeProvider = providerCascade.getOrNull(activeProviderIndex)?.providerName ?: "Gemini",
                            isAutoRetrying = isAutoRetrying,
                            retryCountdown = retryCountdown
                        )
                    }

                    item {
                        NetworkAndOfflineControlCard(
                            networkStatus = networkStatus,
                            isSimulatedOffline = isSimulatedOffline,
                            isOfflineModeActive = isOfflineModeActive,
                            onToggleSimulatedOffline = { viewModel.toggleSimulatedOffline(it) },
                            onToggleOfflineMode = { viewModel.toggleOfflineMode(it) },
                            onOpenConnectionDialog = { viewModel.showConnectionProblemDialog("Manual connection test triggered.") }
                        )
                    }

                    item {
                        QuickIncidentSummaryCard(
                            incidents = errorIncidents,
                            onClear = { viewModel.clearErrorIncidents() },
                            onViewAll = { selectedTab = 3 }
                        )
                    }
                }

                1 -> {
                    // INTERACTIVE ERROR SIMULATORS & RECOVERY TESTING
                    item {
                        ErrorSimulatorCard(
                            onSimulateNetworkDrop = {
                                viewModel.simulateError(ErrorCategory.NETWORK)
                            },
                            onSimulateRateLimit = {
                                viewModel.simulateError(ErrorCategory.API_RATE_LIMIT)
                            },
                            onSimulateServer500 = {
                                viewModel.simulateError(ErrorCategory.API_SERVER)
                            },
                            onSimulateAppCrashRecovery = {
                                viewModel.simulateError(ErrorCategory.APP_RUNTIME)
                            },
                            onSimulateTimeout = {
                                viewModel.simulateError(ErrorCategory.TIMEOUT)
                            }
                        )
                    }

                    item {
                        RetryPolicySettingsCard(
                            policy = retryPolicy,
                            onUpdateMaxRetries = { viewModel.updateRetryPolicy(maxRetries = it) },
                            onToggleAutoRetry = { viewModel.updateRetryPolicy(autoRetry = it) },
                            onToggleProviderFallback = { viewModel.updateRetryPolicy(providerFallback = it) },
                            onToggleAutoOffline = { viewModel.updateRetryPolicy(autoOffline = it) }
                        )
                    }
                }

                2 -> {
                    // PROVIDER FALLBACK CASCADE
                    item {
                        ProviderCascadeOverviewCard(
                            cascade = providerCascade,
                            activeIndex = activeProviderIndex,
                            onTriggerFailover = { viewModel.triggerProviderFailover() }
                        )
                    }

                    items(providerCascade) { node ->
                        val isCurrent = providerCascade.indexOf(node) == activeProviderIndex
                        ProviderNodeCard(
                            node = node,
                            isCurrent = isCurrent,
                            onSelect = { viewModel.setActiveProviderNode(providerCascade.indexOf(node)) }
                        )
                    }
                }

                3 -> {
                    // REAL-TIME INCIDENT LEDGER
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "INCIDENT AUDIT LOGS (${errorIncidents.size})",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = JarvisAmber
                            )

                            if (errorIncidents.isNotEmpty()) {
                                IconButton(onClick = { viewModel.clearErrorIncidents() }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Clear",
                                        tint = JarvisRuby
                                    )
                                }
                            }
                        }
                    }

                    if (errorIncidents.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(JarvisDarkSurface)
                                    .border(1.dp, JarvisDarkBorder, RoundedCornerShape(12.dp))
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "All Green",
                                        tint = JarvisEmerald,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "ZERO ACTIVE INCIDENTS",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        ),
                                        color = JarvisEmerald
                                    )
                                    Text(
                                        text = "All AI APIs, network sockets, and autonomous runtimes operating nominally.",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                        color = JarvisTextSecondary
                                    )
                                }
                            }
                        }
                    } else {
                        items(errorIncidents) { incident ->
                            IncidentItemCard(incident = incident)
                        }
                    }
                }

                4 -> {
                    // AUTHENTICATION, SESSIONS & OFFLINE CACHE
                    item {
                        AuthSessionCard(
                            authSession = authSession,
                            onRefreshToken = { viewModel.refreshAuthToken() },
                            onSimulateLogout = { viewModel.simulateLogout() },
                            onSimulateLogin = { viewModel.simulateLogin() }
                        )
                    }

                    item {
                        OfflineCacheCapabilitiesCard(capabilities = offlineCaps)
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorManagerFlowDiagramCard(
    networkStatus: NetworkStateStatus,
    isOfflineModeActive: Boolean,
    activeProvider: String,
    isAutoRetrying: Boolean,
    retryCountdown: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(JarvisDarkSurface)
            .border(1.5.dp, JarvisAmber.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = Icons.Default.AltRoute,
                        contentDescription = "Architecture",
                        tint = JarvisAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "RESILIENCY ARCHITECTURE PIPELINE",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = JarvisAmber
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(JarvisAmber.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "ACTIVE: $activeProvider",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = JarvisAmber,
                            fontSize = 9.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Visual ASCII-like DAG Diagram Cards
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(JarvisDarkCard, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Tier 1: Client Ingestion
                FlowStageNode(
                    label = "1. INGESTION",
                    description = "React / Next.js → Capacitor → Android Native Core",
                    color = JarvisCyan
                )

                // Tier 2: Error Manager Detection
                FlowStageNode(
                    label = "2. ERROR DETECTION",
                    description = "Network Drop • API Rate Limit (429) • Server (500) • App Runtime",
                    color = JarvisAmber
                )

                // Tier 3: Error Recovery Subsystem
                FlowStageNode(
                    label = "3. RECOVERY ENGINE",
                    description = "Exponential Backoff (1s→2s→4s) • Multi-Provider Cascade • Offline Cache",
                    color = JarvisElectricBlue
                )

                // Tier 4: User UX / Dialog HUD
                FlowStageNode(
                    label = "4. RESOLUTION UX",
                    description = if (isAutoRetrying) "⚠ Retrying in ${retryCountdown}s... Auto-recovering socket" else if (isOfflineModeActive) "🟢 Offline Autonomous Mode Active" else "🟢 Continuous nominal execution",
                    color = if (isAutoRetrying) JarvisRuby else if (isOfflineModeActive) JarvisAmber else JarvisEmerald
                )
            }
        }
    }
}

@Composable
fun FlowStageNode(
    label: String,
    description: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(JarvisDarkSurface, RoundedCornerShape(8.dp))
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.5.sp
                ),
                color = color
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = JarvisTextPrimary
            )
        }
    }
}

@Composable
fun NetworkAndOfflineControlCard(
    networkStatus: NetworkStateStatus,
    isSimulatedOffline: Boolean,
    isOfflineModeActive: Boolean,
    onToggleSimulatedOffline: (Boolean) -> Unit,
    onToggleOfflineMode: (Boolean) -> Unit,
    onOpenConnectionDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(
                        imageVector = if (networkStatus == NetworkStateStatus.ONLINE) Icons.Default.Wifi else Icons.Default.WifiOff,
                        contentDescription = "Network",
                        tint = if (networkStatus == NetworkStateStatus.ONLINE) JarvisCyan else JarvisRuby,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "NETWORK & OFFLINE CONTROLS",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = JarvisCyan
                    )
                }

                Text(
                    text = networkStatus.label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.sp
                    ),
                    color = if (networkStatus == NetworkStateStatus.ONLINE) JarvisEmerald else JarvisRuby
                )
            }

            // Simulated Offline Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(JarvisDarkCard, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Simulate Network Drop (Offline Test)",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )
                    Text(
                        text = "Forces the app to test offline fallback & connection error flows.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = JarvisTextSecondary
                    )
                }
                Switch(
                    checked = isSimulatedOffline,
                    onCheckedChange = onToggleSimulatedOffline,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = JarvisRuby,
                        checkedTrackColor = JarvisRuby.copy(alpha = 0.5f),
                        uncheckedThumbColor = JarvisTextMuted,
                        uncheckedTrackColor = JarvisDarkSurface
                    )
                )
            }

            // Offline Mode Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(JarvisDarkCard, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Autonomous Offline Mode",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )
                    Text(
                        text = "Uses on-device rule heuristics and local cached knowledge embeddings.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = JarvisTextSecondary
                    )
                }
                Switch(
                    checked = isOfflineModeActive,
                    onCheckedChange = onToggleOfflineMode,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = JarvisAmber,
                        checkedTrackColor = JarvisAmber.copy(alpha = 0.5f),
                        uncheckedThumbColor = JarvisTextMuted,
                        uncheckedTrackColor = JarvisDarkSurface
                    )
                )
            }

            // Test Dialog Launcher Button
            OutlinedButton(
                onClick = onOpenConnectionDialog,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = JarvisAmber)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Test Dialog",
                    tint = JarvisAmber,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Show JARVIS Connection Problem Dialog", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Composable
fun QuickIncidentSummaryCard(
    incidents: List<ErrorIncident>,
    onClear: () -> Unit,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RECENT INCIDENTS & AUTO-RESOLUTIONS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisTextPrimary
                )

                Text(
                    text = "View All →",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = JarvisCyan
                    ),
                    modifier = Modifier.clickable { onViewAll() }
                )
            }

            if (incidents.isEmpty()) {
                Text(
                    text = "No recent errors recorded. All systems running stably with 99.98% uptime.",
                    style = MaterialTheme.typography.bodySmall,
                    color = JarvisTextSecondary
                )
            } else {
                incidents.take(3).forEach { incident ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(JarvisDarkCard, RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(incident.category.badgeColorHex).copy(alpha = 0.2f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = incident.category.displayName,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(incident.category.badgeColorHex)
                                        )
                                    )
                                }
                                Text(
                                    text = incident.title,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = JarvisTextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = incident.resolutionMessage.ifBlank { incident.description },
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                color = if (incident.isResolved) JarvisEmerald else JarvisAmber
                            )
                        }

                        if (incident.isResolved) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Resolved",
                                tint = JarvisEmerald,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorSimulatorCard(
    onSimulateNetworkDrop: () -> Unit,
    onSimulateRateLimit: () -> Unit,
    onSimulateServer500: () -> Unit,
    onSimulateAppCrashRecovery: () -> Unit,
    onSimulateTimeout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(JarvisDarkSurface)
            .border(1.5.dp, JarvisAmber.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(
                    imageVector = Icons.Default.FlashOn,
                    contentDescription = "Simulators",
                    tint = JarvisAmber,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "CHAOS & FAULT INJECTION SIMULATOR",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisAmber
                )
            }

            Text(
                text = "Trigger real-time edge failure conditions to verify JARVIS auto-retry, provider failover, and crash isolation mechanisms.",
                style = MaterialTheme.typography.bodySmall,
                color = JarvisTextSecondary
            )

            // Simulator Buttons Grid
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SimulatorActionRow(
                    title = "⚡ Simulate Network Drop (No Internet)",
                    subtitle = "Triggers immediate Connection Problem Modal HUD",
                    buttonText = "Trigger Drop",
                    buttonColor = JarvisRuby,
                    onClick = onSimulateNetworkDrop
                )

                SimulatorActionRow(
                    title = "⚡ Simulate API 429 Rate Limit",
                    subtitle = "Triggers exponential backoff and cascades to fallback AI provider",
                    buttonText = "Inject 429",
                    buttonColor = JarvisAmber,
                    onClick = onSimulateRateLimit
                )

                SimulatorActionRow(
                    title = "⚡ Simulate AI Server 500 / 503 Outage",
                    subtitle = "Switches model from Gemini to Claude / GPT-4o seamlessly",
                    buttonText = "Inject 500",
                    buttonColor = JarvisElectricBlue,
                    onClick = onSimulateServer500
                )

                SimulatorActionRow(
                    title = "⚡ Simulate App Runtime Parsing Exception",
                    subtitle = "Zero-Crash boundary catches exception and recovers app state",
                    buttonText = "Inject Crash",
                    buttonColor = JarvisRuby,
                    onClick = onSimulateAppCrashRecovery
                )

                SimulatorActionRow(
                    title = "⚡ Simulate Request Gateway Timeout (30s)",
                    subtitle = "Aborts slow socket and executes cached response",
                    buttonText = "Inject Timeout",
                    buttonColor = JarvisAmber,
                    onClick = onSimulateTimeout
                )
            }
        }
    }
}

@Composable
fun SimulatorActionRow(
    title: String,
    subtitle: String,
    buttonText: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(JarvisDarkCard, RoundedCornerShape(10.dp))
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = JarvisTextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = JarvisTextSecondary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp
                ),
                color = JarvisDarkBackground
            )
        }
    }
}

@Composable
fun RetryPolicySettingsCard(
    policy: com.example.data.model.RetryPolicyConfig,
    onUpdateMaxRetries: (Int) -> Unit,
    onToggleAutoRetry: (Boolean) -> Unit,
    onToggleProviderFallback: (Boolean) -> Unit,
    onToggleAutoOffline: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "AUTO-RETRY & EXPONENTIAL BACKOFF POLICY",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = JarvisCyan
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Maximum Retry Attempts: ${policy.maxRetries}",
                    style = MaterialTheme.typography.bodySmall,
                    color = JarvisTextPrimary
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(1, 3, 5).forEach { count ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (policy.maxRetries == count) JarvisCyan else JarvisDarkCard)
                                .border(1.dp, if (policy.maxRetries == count) JarvisCyan else JarvisDarkBorder, RoundedCornerShape(6.dp))
                                .clickable { onUpdateMaxRetries(count) }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "$count",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (policy.maxRetries == count) JarvisDarkBackground else JarvisTextPrimary
                                )
                            )
                        }
                    }
                }
            }

            // Switches
            PolicyToggleItem(
                title = "Auto-Retry On Transient Failure",
                subtitle = "Uses exponential backoff delays (1s, 2s, 4s, 8s)",
                isChecked = policy.isAutoRetryEnabled,
                onCheckedChange = onToggleAutoRetry
            )

            PolicyToggleItem(
                title = "Multi-Provider Failover Cascade",
                subtitle = "Switches to Claude/GPT-4o if primary Gemini endpoint rejects",
                isChecked = policy.isProviderFallbackEnabled,
                onCheckedChange = onToggleProviderFallback
            )

            PolicyToggleItem(
                title = "Auto-Offline Fallback on Complete Blackout",
                subtitle = "Engages local rule-based heuristic AI engine without throwing crash",
                isChecked = policy.isAutoOfflineFallbackEnabled,
                onCheckedChange = onToggleAutoOffline
            )
        }
    }
}

@Composable
fun PolicyToggleItem(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(JarvisDarkCard, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.5.sp), color = JarvisTextSecondary)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = JarvisCyan,
                checkedTrackColor = JarvisCyan.copy(alpha = 0.5f),
                uncheckedThumbColor = JarvisTextMuted,
                uncheckedTrackColor = JarvisDarkSurface
            )
        )
    }
}

@Composable
fun ProviderCascadeOverviewCard(
    cascade: List<ProviderFallbackNode>,
    activeIndex: Int,
    onTriggerFailover: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, RouterColor.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Cascade",
                        tint = RouterColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "MULTI-PROVIDER FALLBACK MATRIX",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = RouterColor
                    )
                }

                Button(
                    onClick = onTriggerFailover,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RouterColor)
                ) {
                    Text(
                        text = "Next Provider",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = JarvisDarkBackground
                    )
                }
            }

            Text(
                text = "When an upstream AI provider experiences rate-limiting (429) or regional downtime, JARVIS instantly routes execution to the next highest priority node in the cascade.",
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
                color = JarvisTextSecondary
            )
        }
    }
}

@Composable
fun ProviderNodeCard(
    node: ProviderFallbackNode,
    isCurrent: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isCurrent) JarvisDarkCard else JarvisDarkSurface)
            .border(
                1.5.dp,
                if (isCurrent) JarvisCyan else JarvisDarkBorder,
                RoundedCornerShape(12.dp)
            )
            .clickable { onSelect() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(if (isCurrent) JarvisCyan.copy(alpha = 0.2f) else JarvisDarkSurface)
                        .border(1.dp, if (isCurrent) JarvisCyan else JarvisDarkBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "#${node.priorityOrder}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = if (isCurrent) JarvisCyan else JarvisTextSecondary
                        )
                    )
                }

                Column {
                    Text(
                        text = node.providerName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (isCurrent) JarvisCyan else JarvisTextPrimary
                    )
                    Text(
                        text = "Model: ${node.modelId}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = JarvisTextSecondary
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isCurrent) JarvisEmerald.copy(alpha = 0.2f) else JarvisDarkBorder)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isCurrent) "ACTIVE PROVIDER" else "STANDBY",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.5.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) JarvisEmerald else JarvisTextMuted
                        )
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Health: ${node.healthScore}%",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = if (node.healthScore > 80) JarvisEmerald else JarvisAmber
                    )
                )
            }
        }
    }
}

@Composable
fun IncidentItemCard(
    incident: ErrorIncident,
    modifier: Modifier = Modifier
) {
    val formatter = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }
    val timeString = remember(incident.timestamp) { formatter.format(Date(incident.timestamp)) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(
                1.dp,
                if (incident.isResolved) JarvisDarkBorder else Color(incident.category.badgeColorHex).copy(alpha = 0.6f),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(incident.category.badgeColorHex).copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = incident.category.displayName,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.5.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = Color(incident.category.badgeColorHex)
                            )
                        )
                    }

                    Text(
                        text = incident.title,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )
                }

                Text(
                    text = timeString,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.sp
                    ),
                    color = JarvisTextMuted
                )
            }

            Text(
                text = incident.description,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = JarvisTextSecondary
            )

            // Recovery action & status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(JarvisDarkCard, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recovery: ${incident.recoveryAction.name}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 8.5.sp
                    ),
                    color = JarvisCyan
                )

                Text(
                    text = if (incident.isResolved) "RESOLVED (Attempt ${incident.attemptCount})" else "RETRYING...",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.5.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    color = if (incident.isResolved) JarvisEmerald else JarvisRuby
                )
            }
        }
    }
}

@Composable
fun AuthSessionCard(
    authSession: com.example.data.model.AuthSessionState,
    onRefreshToken: () -> Unit,
    onSimulateLogout: () -> Unit,
    onSimulateLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisCyan.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Auth",
                        tint = JarvisCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "AUTHENTICATION & SESSION SECURITY",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = JarvisCyan
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (authSession.isAuthenticated) JarvisEmerald.copy(alpha = 0.2f) else JarvisRuby.copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (authSession.isAuthenticated) "AUTHENTICATED" else "UNAUTHORIZED",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.5.sp,
                            color = if (authSession.isAuthenticated) JarvisEmerald else JarvisRuby
                        )
                    )
                }
            }

            if (authSession.isAuthenticated) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(JarvisDarkCard, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Commander: ${authSession.username} (${authSession.role})", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
                    Text(text = "Email: ${authSession.email}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
                    Text(text = "JWT: ${authSession.tokenPreview}", style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 9.sp), color = JarvisTextMuted)
                    Text(text = "Storage: ${authSession.secureStorageStatus}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = JarvisEmerald)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onRefreshToken,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Autorenew, contentDescription = "Refresh", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Refresh Token", style = MaterialTheme.typography.labelSmall)
                    }

                    OutlinedButton(
                        onClick = onSimulateLogout,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = JarvisRuby),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Simulate Logout", style = MaterialTheme.typography.labelSmall)
                    }
                }
            } else {
                Button(
                    onClick = onSimulateLogin,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = JarvisCyan),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Authenticate Master Session", color = JarvisDarkBackground, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun OfflineCacheCapabilitiesCard(
    capabilities: List<com.example.data.model.OfflineCacheCapability>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(
                    imageVector = Icons.Default.Storage,
                    contentDescription = "Offline Cache",
                    tint = JarvisAmber,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "OFFLINE AUTONOMOUS CAPABILITY MATRICES",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisAmber
                )
            }

            capabilities.forEach { cap ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(JarvisDarkCard, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = cap.title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
                        Text(text = "${cap.localRuleCount} Local Rules • Cache: ${cap.vectorEmbeddingCacheSize}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(JarvisEmerald.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "READY", style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp, fontWeight = FontWeight.Bold, color = JarvisEmerald))
                    }
                }
            }
        }
    }
}
