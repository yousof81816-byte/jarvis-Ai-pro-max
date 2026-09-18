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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RecoveryStrategy
import com.example.data.model.SubsystemType
import com.example.data.model.ToolManagerItem
import com.example.data.model.ValidatorRule
import com.example.ui.theme.JarvisAmber
import com.example.ui.theme.JarvisCyan
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
import com.example.ui.viewmodel.JarvisViewModel

enum class OrchestratorTab {
    TOPOLOGY,
    TOOLS,
    VALIDATORS,
    RECOVERY
}

@Composable
fun AgentOrchestratorScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(OrchestratorTab.TOPOLOGY) }

    val tools by viewModel.toolManagerItems.collectAsState()
    val validators by viewModel.validatorRules.collectAsState()
    val recoveries by viewModel.recoveryStrategies.collectAsState()

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
                .border(1.dp, JarvisDarkBorder)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(JarvisDarkCard)
                    .testTag("orchestrator_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = JarvisCyan,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "AGENT ORCHESTRATOR CORE",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = JarvisCyan
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(JarvisEmerald.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "8 SUB-ENGINES ACTIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = JarvisEmerald
                        )
                    }
                }
                Text(
                    text = "Planner • Executor • Browser • Memory • Validator • Recovery • AI Router",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontFamily = FontFamily.Monospace),
                    color = JarvisTextSecondary
                )
            }
        }

        // Tab Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JarvisDarkSurface)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                OrchestratorTab.TOPOLOGY to "Subsystems",
                OrchestratorTab.TOOLS to "Tools (${tools.count { it.isEnabled }})",
                OrchestratorTab.VALIDATORS to "Validators (${validators.size})",
                OrchestratorTab.RECOVERY to "Recovery (${recoveries.size})"
            ).forEach { (tab, label) ->
                val isSelected = selectedTab == tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) JarvisCyan.copy(alpha = 0.2f) else JarvisDarkCard)
                        .border(1.dp, if (isSelected) JarvisCyan else JarvisDarkBorder, RoundedCornerShape(8.dp))
                        .clickable { selectedTab = tab }
                        .padding(vertical = 7.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 10.5.sp
                        ),
                        color = if (isSelected) JarvisCyan else JarvisTextSecondary
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (selectedTab) {
                OrchestratorTab.TOPOLOGY -> {
                    item {
                        Text(
                            text = "ORCHESTRATOR SUBSYSTEM MESH & IPC",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            ),
                            color = JarvisCyan
                        )
                    }

                    items(SubsystemType.values()) { sub ->
                        SubsystemMeshCard(subsystem = sub)
                    }
                }

                OrchestratorTab.TOOLS -> {
                    item {
                        Text(
                            text = "SANDBOXED TOOL EXECUTION MANAGER",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            ),
                            color = JarvisCyan
                        )
                    }

                    items(tools) { tool ->
                        ToolManagerCard(tool = tool, onToggle = { viewModel.toggleToolItem(tool.id) })
                    }
                }

                OrchestratorTab.VALIDATORS -> {
                    item {
                        Text(
                            text = "SAFETY, PROMPT INJECTION & DAG VALIDATORS",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            ),
                            color = JarvisCyan
                        )
                    }

                    items(validators) { valRule ->
                        ValidatorRuleCard(rule = valRule)
                    }
                }

                OrchestratorTab.RECOVERY -> {
                    item {
                        Text(
                            text = "AUTONOMOUS SELF-HEALING & ERROR RECOVERY MATRIX",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            ),
                            color = JarvisCyan
                        )
                    }

                    items(recoveries) { rec ->
                        RecoveryStrategyCard(strategy = rec)
                    }
                }
            }
        }
    }
}

@Composable
fun SubsystemMeshCard(subsystem: SubsystemType, modifier: Modifier = Modifier) {
    val icon = when (subsystem) {
        SubsystemType.PLANNER -> Icons.Default.DeveloperBoard
        SubsystemType.EXECUTOR -> Icons.Default.Code
        SubsystemType.MEMORY -> Icons.Default.Storage
        SubsystemType.BROWSER_AGENT -> Icons.Default.Web
        SubsystemType.AI_ROUTER -> Icons.Default.Psychology
        SubsystemType.SECURITY -> Icons.Default.Security
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(JarvisDarkCard)
                .border(1.dp, JarvisCyan, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = JarvisCyan, modifier = Modifier.size(18.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(subsystem.title.uppercase(), style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
            Text(subsystem.description, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(JarvisEmerald.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("HEALTHY", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = JarvisEmerald))
        }
    }
}

@Composable
fun ToolManagerCard(tool: ToolManagerItem, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(tool.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(JarvisDarkCard)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(tool.category, style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.5.sp), color = JarvisCyan)
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(tool.description, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Calls: ${tool.callCount} • Avg Latency: ${tool.avgRuntimeMs}ms",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontFamily = FontFamily.Monospace),
                color = JarvisElectricBlue
            )
        }

        Switch(
            checked = tool.isEnabled,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = JarvisCyan,
                checkedTrackColor = JarvisCyan.copy(alpha = 0.3f),
                uncheckedThumbColor = JarvisTextMuted,
                uncheckedTrackColor = JarvisDarkCard
            )
        )
    }
}

@Composable
fun ValidatorRuleCard(rule: ValidatorRule, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(rule.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
                Text("${rule.targetSubsystem} • ${rule.checkType}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisCyan)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(JarvisEmerald.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("${rule.passRatePercentage}% PASS", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = JarvisEmerald))
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            rule.description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
            color = JarvisTextSecondary
        )
    }
}

@Composable
fun RecoveryStrategyCard(strategy: RecoveryStrategy, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(strategy.failureTrigger, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = JarvisAmber)
            Text(
                "Success: ${strategy.successRate}%",
                style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = JarvisEmerald)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text("Fallback: ${strategy.fallbackSubsystem} • Max Retries: ${strategy.autoRetryLimit}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisCyan)
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(JarvisDarkCard)
                .padding(8.dp)
        ) {
            Text(
                strategy.recoveryAction,
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 10.5.sp),
                color = JarvisTextPrimary
            )
        }
    }
}

