package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ClientPlatform
import com.example.ui.theme.JarvisAmber
import com.example.ui.theme.JarvisCyan
import com.example.ui.theme.JarvisCyanGlow
import com.example.ui.theme.JarvisDarkBorder
import com.example.ui.theme.JarvisDarkCard
import com.example.ui.theme.JarvisDarkSurface
import com.example.ui.theme.JarvisEmerald
import com.example.ui.theme.JarvisTextMuted
import com.example.ui.theme.JarvisTextPrimary
import com.example.ui.theme.JarvisTextSecondary
import com.example.ui.viewmodel.JarvisSystemMetrics

@Composable
fun JarvisTopBar(
    activeClient: ClientPlatform,
    metrics: JarvisSystemMetrics,
    isExecuting: Boolean,
    onClientSelectorClick: () -> Unit,
    onSecurityShieldClick: () -> Unit,
    onVoiceAgentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(JarvisDarkSurface)
            .border(width = 1.dp, color = JarvisDarkBorder, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // JARVIS Brand & Heartbeat
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(JarvisCyan.copy(alpha = 0.3f), Color.Transparent)
                            )
                        )
                        .border(1.5.dp, JarvisCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_jarvis_logo),
                        contentDescription = "JARVIS Core Logo",
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                    )
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "JARVIS",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            ),
                            color = JarvisTextPrimary
                        )
                        Text(
                            text = "AGENT ENGINE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = JarvisCyan,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .background(JarvisCyanGlow, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .scale(if (isExecuting) pulseScale else 1f)
                                .background(if (isExecuting) JarvisAmber else JarvisEmerald, CircleShape)
                        )
                        Text(
                            text = if (isExecuting) "ORCHESTRATING MISSION..." else "ONLINE / MESH ACTIVE",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isExecuting) JarvisAmber else JarvisEmerald
                        )
                    }
                }
            }

            // Quick Client Switcher Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(JarvisDarkCard)
                        .border(1.dp, JarvisDarkBorder, RoundedCornerShape(20.dp))
                        .clickable { onClientSelectorClick() }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(
                            imageVector = Icons.Default.Devices,
                            contentDescription = "Client Selector",
                            tint = JarvisCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = activeClient.displayName.removeSuffix(" App"),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = JarvisTextPrimary
                        )
                    }
                }

                // Voice Agent Mic Badge
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(JarvisCyan.copy(alpha = 0.15f))
                        .border(1.dp, JarvisCyan, CircleShape)
                        .clickable { onVoiceAgentClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Agent",
                        tint = JarvisCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Security Shield Badge
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(JarvisDarkCard)
                        .border(1.dp, JarvisDarkBorder, CircleShape)
                        .clickable { onSecurityShieldClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Security Firewall",
                        tint = JarvisEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Telemetry Metrics Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JarvisDarkCard.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .border(1.dp, JarvisDarkBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TelemetryItem(
                label = "GATEWAY LATENCY",
                value = "${metrics.gatewayLatencyMs}ms",
                accentColor = JarvisCyan
            )
            TelemetryDivider()
            TelemetryItem(
                label = "ACTIVE CLIENTS",
                value = "${metrics.connectedClientsCount}/4",
                accentColor = JarvisEmerald
            )
            TelemetryDivider()
            TelemetryItem(
                label = "TOTAL TOKENS",
                value = String.format("%,d", metrics.totalTokensConsumed),
                accentColor = JarvisAmber
            )
            TelemetryDivider()
            TelemetryItem(
                label = "FIREWALL",
                value = "ARMED",
                accentColor = JarvisEmerald
            )
        }
    }
}

@Composable
private fun TelemetryItem(
    label: String,
    value: String,
    accentColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            ),
            color = JarvisTextMuted
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            ),
            color = accentColor
        )
    }
}

@Composable
private fun TelemetryDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(18.dp)
            .background(JarvisDarkBorder)
    )
}
