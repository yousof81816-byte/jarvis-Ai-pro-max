package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClientPlatform
import com.example.ui.theme.JarvisCyan
import com.example.ui.theme.JarvisDarkBackground
import com.example.ui.theme.JarvisDarkBorder
import com.example.ui.theme.JarvisDarkCard
import com.example.ui.theme.JarvisDarkSurface
import com.example.ui.theme.JarvisEmerald
import com.example.ui.theme.JarvisTextMuted
import com.example.ui.theme.JarvisTextPrimary
import com.example.ui.theme.JarvisTextSecondary
import com.example.ui.viewmodel.JarvisViewModel

data class ClientPlatformMetadata(
    val platform: ClientPlatform,
    val icon: ImageVector,
    val protocol: String,
    val virtualEndpoint: String,
    val simulatedPingMs: Long,
    val runtimeEngine: String
)

@Composable
fun ClientPlatformsScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeClient by viewModel.activeClient.collectAsState()

    val clients = listOf(
        ClientPlatformMetadata(
            platform = ClientPlatform.ANDROID,
            icon = Icons.Default.PhoneAndroid,
            protocol = "gRPC Streaming / TLS 1.3",
            virtualEndpoint = "grpc://android-node-01.mesh.local:50051",
            simulatedPingMs = 12L,
            runtimeEngine = "Kotlin Jetpack Compose Native"
        ),
        ClientPlatformMetadata(
            platform = ClientPlatform.IPHONE,
            icon = Icons.Default.PhoneIphone,
            protocol = "HTTP/3 QUIC Bidirectional",
            virtualEndpoint = "quic://ios-gateway-us-east.mesh.local:443",
            simulatedPingMs = 19L,
            runtimeEngine = "Swift / SwiftUI Gateway Bridge"
        ),
        ClientPlatformMetadata(
            platform = ClientPlatform.WEB,
            icon = Icons.Default.Web,
            protocol = "Secure WebSocket (WSS)",
            virtualEndpoint = "wss://app.jarvis-mesh.cloud/api/v2/stream",
            simulatedPingMs = 24L,
            runtimeEngine = "React / WASM Holographic Console"
        ),
        ClientPlatformMetadata(
            platform = ClientPlatform.WINDOWS,
            icon = Icons.Default.Computer,
            protocol = "Named Pipes Local IPC",
            virtualEndpoint = "\\\\.\\pipe\\jarvis_engine_windows_ipc",
            simulatedPingMs = 2L,
            runtimeEngine = "WinUI 3 / C++ Native Microkernel"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(JarvisDarkBackground)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JarvisDarkSurface)
                .border(1.dp, JarvisDarkBorder, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onBack) {
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
                    .background(JarvisCyan.copy(alpha = 0.2f))
                    .border(1.dp, JarvisCyan, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Devices,
                    contentDescription = "Client Platforms",
                    tint = JarvisCyan,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = "CLIENT INGRESS PLATFORMS",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisTextPrimary
                )
                Text(
                    text = "Multi-Client Mesh Gateway: Android, iPhone, Web, Windows",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = JarvisCyan
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "CONNECTED DISPATCH CLIENTS (4/4 ACTIVE)",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisCyan
                )
            }

            items(clients) { c ->
                val isCurrent = activeClient == c.platform

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(JarvisDarkSurface)
                        .border(
                            1.5.dp,
                            if (isCurrent) JarvisCyan else JarvisDarkBorder,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { viewModel.selectClient(c.platform) }
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(if (isCurrent) JarvisCyan.copy(alpha = 0.2f) else JarvisDarkCard)
                                        .border(1.dp, if (isCurrent) JarvisCyan else JarvisDarkBorder, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = c.icon,
                                        contentDescription = c.platform.displayName,
                                        tint = if (isCurrent) JarvisCyan else JarvisTextSecondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = c.platform.displayName,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        ),
                                        color = if (isCurrent) JarvisCyan else JarvisTextPrimary
                                    )
                                    Text(
                                        text = c.runtimeEngine,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = JarvisTextSecondary
                                    )
                                }
                            }

                            if (isCurrent) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier
                                        .background(JarvisCyan.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                        .border(1.dp, JarvisCyan, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Active",
                                        tint = JarvisEmerald,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "SELECTED ORIGIN",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        ),
                                        color = JarvisEmerald
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Virtual Endpoint: ${c.virtualEndpoint}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp
                            ),
                            color = JarvisTextMuted
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(JarvisDarkCard, RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "PROTOCOL: ${c.protocol}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = JarvisTextSecondary
                            )
                            Text(
                                text = "PING: ${c.simulatedPingMs}ms",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = JarvisEmerald
                            )
                        }

                        if (!isCurrent) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = { viewModel.selectClient(c.platform) },
                                colors = ButtonDefaults.buttonColors(containerColor = JarvisDarkCard),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, JarvisDarkBorder, RoundedCornerShape(8.dp)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "SWITCH DISPATCH TO ${c.platform.displayName.uppercase()}",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = JarvisTextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
