package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.automirrored.filled.AltRoute
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClientPlatform
import com.example.data.model.SubsystemType
import com.example.ui.theme.BrowserColor
import com.example.ui.theme.ExecutorColor
import com.example.ui.theme.JarvisAmber
import com.example.ui.theme.JarvisCyan
import com.example.ui.theme.JarvisCyanGlow
import com.example.ui.theme.JarvisDarkBorder
import com.example.ui.theme.JarvisDarkCard
import com.example.ui.theme.JarvisDarkSurface
import com.example.ui.theme.JarvisElectricBlue
import com.example.ui.theme.JarvisEmerald
import com.example.ui.theme.JarvisRuby
import com.example.ui.theme.JarvisTextMuted
import com.example.ui.theme.JarvisTextPrimary
import com.example.ui.theme.JarvisTextSecondary
import com.example.ui.theme.MemoryColor
import com.example.ui.theme.PlannerColor
import com.example.ui.theme.RouterColor
import com.example.ui.theme.SecurityColor

@Composable
fun TopologyDiagramView(
    activeClient: ClientPlatform,
    isExecuting: Boolean,
    onSelectClient: (ClientPlatform) -> Unit,
    onSelectSubsystem: (SubsystemType) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "signalFlow")
    val signalOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "signalAnim"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        // Topology Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(
                    imageVector = Icons.Default.Hub,
                    contentDescription = "Topology Map",
                    tint = JarvisCyan,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "SYSTEM ARCHITECTURE TOPOLOGY",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = JarvisCyan
                )
            }
            Text(
                text = "INTERACTIVE HUD",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = JarvisTextMuted
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Main Visual Flow Layout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Column: 4 Client Platforms
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ClientNode(
                    client = ClientPlatform.ANDROID,
                    icon = Icons.Default.PhoneAndroid,
                    isSelected = activeClient == ClientPlatform.ANDROID,
                    onClick = { onSelectClient(ClientPlatform.ANDROID) }
                )
                ClientNode(
                    client = ClientPlatform.IPHONE,
                    icon = Icons.Default.PhoneIphone,
                    isSelected = activeClient == ClientPlatform.IPHONE,
                    onClick = { onSelectClient(ClientPlatform.IPHONE) }
                )
                ClientNode(
                    client = ClientPlatform.WEB,
                    icon = Icons.Default.Web,
                    isSelected = activeClient == ClientPlatform.WEB,
                    onClick = { onSelectClient(ClientPlatform.WEB) }
                )
                ClientNode(
                    client = ClientPlatform.WINDOWS,
                    icon = Icons.Default.Computer,
                    isSelected = activeClient == ClientPlatform.WINDOWS,
                    onClick = { onSelectClient(ClientPlatform.WINDOWS) }
                )
            }

            // Middle: Animated Gateway & API Conduit
            ConduitConnector(
                isExecuting = isExecuting,
                signalOffset = signalOffset,
                modifier = Modifier
                    .width(42.dp)
                    .height(180.dp)
            )

            // Center Column: JARVIS API & AGENT ENGINE CORE
            Column(
                modifier = Modifier.weight(1.1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // JARVIS API Hub
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(JarvisDarkCard, Color(0xFF162A4A))
                            )
                        )
                        .border(1.dp, JarvisCyan.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "JARVIS API",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = JarvisCyan
                        )
                        Text(
                            text = "Gateway Ingress",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = JarvisTextSecondary
                        )
                    }
                }

                // Pulse Arrow
                Text(
                    text = "▼",
                    color = if (isExecuting) JarvisAmber else JarvisElectricBlue,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                )

                // AGENT ENGINE CORE
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.radialGradient(
                                listOf(Color(0xFF1E3A5F), JarvisDarkCard)
                            )
                        )
                        .border(
                            1.5.dp,
                            if (isExecuting) JarvisAmber else JarvisElectricBlue,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Engine Core",
                                tint = if (isExecuting) JarvisAmber else JarvisElectricBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Agent Engine",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                ),
                                color = JarvisTextPrimary
                            )
                        }
                        Text(
                            text = if (isExecuting) "Active Cycle" else "Autonomous Loop",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                color = if (isExecuting) JarvisAmber else JarvisEmerald
                            )
                        )
                    }
                }
            }

            // Right Connector
            ConduitConnector(
                isExecuting = isExecuting,
                signalOffset = signalOffset,
                modifier = Modifier
                    .width(28.dp)
                    .height(180.dp)
            )

            // Right Column: 6 Subsystem Nodes
            Column(
                modifier = Modifier.weight(1.3f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                SubsystemMiniNode(
                    subsystem = SubsystemType.PLANNER,
                    icon = Icons.Default.AccountTree,
                    color = PlannerColor,
                    onClick = { onSelectSubsystem(SubsystemType.PLANNER) }
                )
                SubsystemMiniNode(
                    subsystem = SubsystemType.EXECUTOR,
                    icon = Icons.Default.Terminal,
                    color = ExecutorColor,
                    onClick = { onSelectSubsystem(SubsystemType.EXECUTOR) }
                )
                SubsystemMiniNode(
                    subsystem = SubsystemType.MEMORY,
                    icon = Icons.Default.Memory,
                    color = MemoryColor,
                    onClick = { onSelectSubsystem(SubsystemType.MEMORY) }
                )
                SubsystemMiniNode(
                    subsystem = SubsystemType.BROWSER_AGENT,
                    icon = Icons.Default.Public,
                    color = BrowserColor,
                    onClick = { onSelectSubsystem(SubsystemType.BROWSER_AGENT) }
                )
                SubsystemMiniNode(
                    subsystem = SubsystemType.AI_ROUTER,
                    icon = Icons.AutoMirrored.Filled.AltRoute,
                    color = RouterColor,
                    onClick = { onSelectSubsystem(SubsystemType.AI_ROUTER) }
                )
                SubsystemMiniNode(
                    subsystem = SubsystemType.SECURITY,
                    icon = Icons.Default.Security,
                    color = SecurityColor,
                    onClick = { onSelectSubsystem(SubsystemType.SECURITY) }
                )
            }
        }
    }
}

@Composable
private fun ClientNode(
    client: ClientPlatform,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) JarvisCyanGlow else JarvisDarkCard)
            .border(
                1.dp,
                if (isSelected) JarvisCyan else JarvisDarkBorder,
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = client.displayName,
                tint = if (isSelected) JarvisCyan else JarvisTextSecondary,
                modifier = Modifier.size(14.dp)
            )
            Column {
                Text(
                    text = client.displayName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 10.sp
                    ),
                    color = if (isSelected) JarvisCyan else JarvisTextPrimary
                )
                if (isSelected) {
                    Text(
                        text = "ACTIVE DISPATCH",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 7.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        ),
                        color = JarvisEmerald
                    )
                }
            }
        }
    }
}

@Composable
private fun SubsystemMiniNode(
    subsystem: SubsystemType,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(JarvisDarkCard)
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(color, CircleShape)
                )
                Text(
                    text = subsystem.title,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = JarvisTextPrimary
                )
            }
            Text(
                text = subsystem.shortCode,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace,
                    color = color
                )
            )
        }
    }
}

@Composable
private fun ConduitConnector(
    isExecuting: Boolean,
    signalOffset: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val centerY = h / 2

        // Base Line
        drawLine(
            color = Color(0xFF1E293B),
            start = Offset(0f, centerY),
            end = Offset(w, centerY),
            strokeWidth = 2.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
        )

        // Animated Signal Pulse
        val pulseX = w * signalOffset
        drawCircle(
            color = if (isExecuting) JarvisAmber else JarvisCyan,
            radius = 3.5.dp.toPx(),
            center = Offset(pulseX, centerY)
        )
    }
}
