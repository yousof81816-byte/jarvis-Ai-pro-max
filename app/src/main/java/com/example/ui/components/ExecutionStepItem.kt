package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExecutionStep
import com.example.data.model.StepStatus
import com.example.data.model.SubsystemType
import com.example.ui.theme.BrowserColor
import com.example.ui.theme.ExecutorColor
import com.example.ui.theme.JarvisAmber
import com.example.ui.theme.JarvisCyan
import com.example.ui.theme.JarvisDarkBorder
import com.example.ui.theme.JarvisDarkCard
import com.example.ui.theme.JarvisDarkSurface
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
fun ExecutionStepItem(
    step: ExecutionStep,
    isCurrentRunning: Boolean,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(step.status == StepStatus.RUNNING || step.status == StepStatus.SUCCESS) }

    val subsystemColor = when (step.subsystem) {
        SubsystemType.PLANNER -> PlannerColor
        SubsystemType.EXECUTOR -> ExecutorColor
        SubsystemType.MEMORY -> MemoryColor
        SubsystemType.BROWSER_AGENT -> BrowserColor
        SubsystemType.AI_ROUTER -> RouterColor
        SubsystemType.SECURITY -> SecurityColor
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(
                1.dp,
                if (isCurrentRunning) JarvisAmber else JarvisDarkBorder,
                RoundedCornerShape(12.dp)
            )
            .clickable { isExpanded = !isExpanded }
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    // Status Badge
                    when (step.status) {
                        StepStatus.PENDING -> {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(JarvisDarkCard)
                                    .border(1.dp, JarvisDarkBorder, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${step.stepNumber}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    color = JarvisTextMuted
                                )
                            }
                        }
                        StepStatus.RUNNING -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = JarvisAmber,
                                strokeWidth = 2.dp
                            )
                        }
                        StepStatus.SUCCESS -> {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Step Success",
                                tint = JarvisEmerald,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        StepStatus.FAILED -> {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Step Failed",
                                tint = JarvisRuby,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        StepStatus.SKIPPED -> {
                            Icon(
                                imageVector = Icons.Default.HourglassEmpty,
                                contentDescription = "Step Skipped",
                                tint = JarvisTextMuted,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "STEP ${step.stepNumber}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = subsystemColor
                            )
                            Text(
                                text = "•",
                                color = JarvisTextMuted,
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = step.subsystem.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = subsystemColor
                            )
                        }

                        Text(
                            text = step.actionName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            ),
                            color = JarvisTextPrimary
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (step.latencyMs > 0) {
                        Text(
                            text = "${step.latencyMs}ms",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            color = JarvisTextMuted
                        )
                    }

                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expand Step",
                        tint = JarvisTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        ),
                        color = JarvisTextSecondary
                    )

                    if (!step.toolUsed.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "TOOL:",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = JarvisCyan
                            )
                            Text(
                                text = step.toolUsed,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = JarvisTextPrimary
                            )
                        }
                    }

                    if (step.inputPayload.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        PayloadTerminal(
                            label = "PAYLOAD INPUT",
                            content = step.inputPayload,
                            color = JarvisCyan
                        )
                    }

                    if (step.outputPayload.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        PayloadTerminal(
                            label = "EXECUTION RESULT",
                            content = step.outputPayload,
                            color = JarvisEmerald
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PayloadTerminal(
    label: String,
    content: String,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(JarvisDarkCard)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(6.dp))
            .padding(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 8.5.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            color = color
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                lineHeight = 15.sp
            ),
            color = JarvisTextPrimary
        )
    }
}
