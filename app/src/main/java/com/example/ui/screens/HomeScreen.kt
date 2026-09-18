package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.model.AgentMission
import com.example.data.model.ClientPlatform
import com.example.data.model.MissionStatus
import com.example.data.model.SubsystemType
import com.example.ui.components.ExecutionStepItem
import com.example.ui.components.JarvisTopBar
import com.example.ui.components.SubsystemCard
import com.example.ui.components.TopologyDiagramView
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
import com.example.ui.viewmodel.JarvisViewModel

@Composable
fun HomeScreen(
    viewModel: JarvisViewModel,
    onNavigateToClients: () -> Unit,
    onNavigateToSubsystem: (SubsystemType) -> Unit,
    onNavigateToSDLC: () -> Unit,
    onNavigateToEnterprise: () -> Unit,
    onNavigateToRAG: () -> Unit,
    onNavigateToCreative: () -> Unit,
    onNavigateToOrchestrator: () -> Unit,
    onNavigateToSelfLearning: () -> Unit,
    onNavigateToMonorepo: () -> Unit,
    onNavigateToVoiceAgent: () -> Unit,
    onNavigateToErrorManager: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeClient by viewModel.activeClient.collectAsState()
    val isExecuting by viewModel.isExecutingMission.collectAsState()
    val currentMission by viewModel.currentMission.collectAsState()
    val metrics by viewModel.systemMetrics.collectAsState()
    val missionsList by viewModel.missionsList.collectAsState()
    val networkStatus by viewModel.networkStatus.collectAsState()
    val isOfflineModeActive by viewModel.isOfflineModeActive.collectAsState()

    var promptInput by remember { mutableStateOf("") }

    val missionPresets = listOf(
        "Audit server security logs and generate DAG patch" to "Security & Vulnerability Audit",
        "Autonomous research on 2026 AI Agent benchmarks" to "2026 AI Agent Benchmarks Research",
        "Build full-stack microservice API with tests" to "Autonomous Code Synthesis",
        "Scrape competitor SaaS pricing and synthesize matrix" to "DOM Scraper & Market Synthesis"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(JarvisDarkBackground)
    ) {
        // Sticky Header
        JarvisTopBar(
            activeClient = activeClient,
            metrics = metrics,
            isExecuting = isExecuting,
            onClientSelectorClick = onNavigateToClients,
            onSecurityShieldClick = { onNavigateToSubsystem(SubsystemType.SECURITY) },
            onVoiceAgentClick = onNavigateToVoiceAgent
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Visual Interactive Topology HUD
            item {
                TopologyDiagramView(
                    activeClient = activeClient,
                    isExecuting = isExecuting,
                    onSelectClient = { viewModel.selectClient(it) },
                    onSelectSubsystem = { onNavigateToSubsystem(it) }
                )
            }

            // Quick Autonomous Workflows Row
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "AUTONOMOUS CAPABILITIES & WORKFLOWS",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = JarvisCyan
                    )

                    // Hero Voice Agent Banner
                    AutonomousWorkflowTile(
                        title = "JARVIS Live Voice Agent (ভয়েস এজেন্ট)",
                        subtitle = "Bilingual Speech Recognition • Real-time Neural TTS • 7 Voice Personas",
                        badge = "LIVE VOICE",
                        badgeColor = JarvisRuby,
                        onClick = onNavigateToVoiceAgent,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AutonomousWorkflowTile(
                            title = "9-Phase SDLC Pipeline",
                            subtitle = "Understand ➔ Code ➔ Test ➔ Deploy",
                            badge = "AUTONOMOUS",
                            badgeColor = JarvisCyan,
                            onClick = onNavigateToSDLC,
                            modifier = Modifier.weight(1f)
                        )
                        AutonomousWorkflowTile(
                            title = "Enterprise Hub",
                            subtitle = "HR • Sales CRM • Projects",
                            badge = "BUSINESS",
                            badgeColor = JarvisElectricBlue,
                            onClick = onNavigateToEnterprise,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AutonomousWorkflowTile(
                            title = "RAG Vector Pipeline",
                            subtitle = "1536-dim Embedding Search",
                            badge = "VECTOR DB",
                            badgeColor = JarvisEmerald,
                            onClick = onNavigateToRAG,
                            modifier = Modifier.weight(1f)
                        )
                        AutonomousWorkflowTile(
                            title = "Creative Studio",
                            subtitle = "Diffusion & Website Design",
                            badge = "DESIGN",
                            badgeColor = JarvisAmber,
                            onClick = onNavigateToCreative,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AutonomousWorkflowTile(
                            title = "Agent Orchestrator",
                            subtitle = "Tools • Validators • Recovery",
                            badge = "CORE",
                            badgeColor = JarvisRuby,
                            onClick = onNavigateToOrchestrator,
                            modifier = Modifier.weight(1f)
                        )
                        AutonomousWorkflowTile(
                            title = "Self-Improvement",
                            subtitle = "Continuous Learning Loop",
                            badge = "LEARNING",
                            badgeColor = JarvisEmerald,
                            onClick = onNavigateToSelfLearning,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AutonomousWorkflowTile(
                            title = "Monorepo Tree",
                            subtitle = "Multi-tier Codebase",
                            badge = "ARCH",
                            badgeColor = JarvisCyan,
                            onClick = onNavigateToMonorepo,
                            modifier = Modifier.weight(1f)
                        )
                        AutonomousWorkflowTile(
                            title = "Error Manager",
                            subtitle = "Auto-Retry & Fallback",
                            badge = if (isOfflineModeActive) "OFFLINE" else if (networkStatus == com.example.data.model.NetworkStateStatus.ONLINE) "HEALTHY" else "ATTENTION",
                            badgeColor = if (isOfflineModeActive) JarvisAmber else if (networkStatus == com.example.data.model.NetworkStateStatus.ONLINE) JarvisEmerald else JarvisRuby,
                            onClick = onNavigateToErrorManager,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Section 2: Mission Dispatcher & Prompt Launcher
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(JarvisDarkSurface)
                        .border(1.dp, JarvisDarkBorder, RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(
                                imageVector = Icons.Default.RocketLaunch,
                                contentDescription = "Mission Dispatcher",
                                tint = JarvisCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "DISPATCH AUTONOMOUS MISSION",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = JarvisCyan
                            )
                        }

                        Text(
                            text = "FROM: ${activeClient.displayName.uppercase()}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            color = JarvisElectricBlue
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Preset chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(missionPresets) { (prompt, title) ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(JarvisDarkCard)
                                    .border(1.dp, JarvisDarkBorder, RoundedCornerShape(20.dp))
                                    .clickable {
                                        promptInput = prompt
                                        viewModel.launchMission(prompt, title)
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "Preset",
                                        tint = JarvisCyan,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                        color = JarvisTextPrimary
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Input Field & Dispatch Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = promptInput,
                            onValueChange = { promptInput = it },
                            placeholder = {
                                Text(
                                    "Instruct Agent Engine (e.g. 'Analyze market sentiment and compile report')...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = JarvisTextMuted
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("mission_prompt_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = JarvisDarkCard,
                                unfocusedContainerColor = JarvisDarkCard,
                                focusedBorderColor = JarvisCyan,
                                unfocusedBorderColor = JarvisDarkBorder,
                                focusedTextColor = JarvisTextPrimary,
                                unfocusedTextColor = JarvisTextPrimary
                            ),
                            maxLines = 3
                        )

                        IconButton(
                            onClick = {
                                if (promptInput.isNotBlank()) {
                                    val text = promptInput
                                    promptInput = ""
                                    viewModel.launchMission(text)
                                }
                            },
                            enabled = promptInput.isNotBlank() && !isExecuting,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (promptInput.isNotBlank() && !isExecuting) JarvisCyan else JarvisDarkCard)
                                .testTag("dispatch_mission_button")
                        ) {
                            if (isExecuting) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = JarvisAmber,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Dispatch Mission",
                                    tint = if (promptInput.isNotBlank()) JarvisDarkBackground else JarvisTextMuted,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Section 3: Active Mission Live Trace / Execution Stream
            if (currentMission != null) {
                item {
                    val mission = currentMission!!
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(JarvisDarkSurface)
                            .border(
                                1.5.dp,
                                if (mission.status == MissionStatus.EXECUTING) JarvisAmber else JarvisDarkBorder,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(
                                            when (mission.status) {
                                                MissionStatus.PLANNING, MissionStatus.EXECUTING -> JarvisAmber
                                                MissionStatus.COMPLETED -> JarvisEmerald
                                                else -> JarvisRuby
                                            },
                                            CircleShape
                                        )
                                )
                                Column {
                                    Text(
                                        text = mission.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = JarvisTextPrimary
                                    )
                                    Text(
                                        text = "ID: ${mission.id} • Via ${mission.clientPlatform.displayName}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.Monospace
                                        ),
                                        color = JarvisTextSecondary
                                    )
                                }
                            }

                            Text(
                                text = mission.status.name,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = when (mission.status) {
                                        MissionStatus.PLANNING, MissionStatus.EXECUTING -> JarvisAmber
                                        MissionStatus.COMPLETED -> JarvisEmerald
                                        else -> JarvisRuby
                                    }
                                ),
                                modifier = Modifier
                                    .background(JarvisDarkCard, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Execution Steps Accordion
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            mission.steps.forEach { step ->
                                ExecutionStepItem(
                                    step = step,
                                    isCurrentRunning = step.status == com.example.data.model.StepStatus.RUNNING
                                )
                            }
                        }

                        if (mission.status == MissionStatus.COMPLETED && mission.summaryResult.isNotBlank()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(JarvisDarkCard)
                                    .border(1.dp, JarvisEmerald.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Completed",
                                            tint = JarvisEmerald,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "MISSION SYNTHESIS DELIVERABLE",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            ),
                                            color = JarvisEmerald
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = mission.summaryResult,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            lineHeight = 19.sp,
                                            fontSize = 13.sp
                                        ),
                                        color = JarvisTextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Section 4: 6 Agent Subsystems Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "AGENT ENGINE SUBSYSTEMS",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = JarvisCyan
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SubsystemCard(
                            subsystem = SubsystemType.PLANNER,
                            onClick = { onNavigateToSubsystem(SubsystemType.PLANNER) },
                            modifier = Modifier.weight(1f)
                        )
                        SubsystemCard(
                            subsystem = SubsystemType.EXECUTOR,
                            onClick = { onNavigateToSubsystem(SubsystemType.EXECUTOR) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SubsystemCard(
                            subsystem = SubsystemType.MEMORY,
                            onClick = { onNavigateToSubsystem(SubsystemType.MEMORY) },
                            modifier = Modifier.weight(1f)
                        )
                        SubsystemCard(
                            subsystem = SubsystemType.BROWSER_AGENT,
                            onClick = { onNavigateToSubsystem(SubsystemType.BROWSER_AGENT) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SubsystemCard(
                            subsystem = SubsystemType.AI_ROUTER,
                            onClick = { onNavigateToSubsystem(SubsystemType.AI_ROUTER) },
                            modifier = Modifier.weight(1f)
                        )
                        SubsystemCard(
                            subsystem = SubsystemType.SECURITY,
                            onClick = { onNavigateToSubsystem(SubsystemType.SECURITY) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Section 5: Past Mission History
            if (missionsList.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(JarvisDarkSurface)
                            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "History",
                                tint = JarvisCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "LOCAL MISSION LOG (ROOM DB)",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = JarvisCyan
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            missionsList.take(5).forEach { m ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(JarvisDarkCard)
                                        .padding(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = m.title,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 13.sp
                                                ),
                                                color = JarvisTextPrimary
                                            )
                                            Text(
                                                text = "From ${m.clientPlatform.displayName} • ${m.latencyMs}ms • ${m.totalTokens} tokens",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontSize = 9.sp,
                                                    fontFamily = FontFamily.Monospace
                                                ),
                                                color = JarvisTextMuted
                                            )
                                        }

                                        Text(
                                            text = m.status.name,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (m.status == MissionStatus.COMPLETED) JarvisEmerald else JarvisAmber
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AutonomousWorkflowTile(
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    ),
                    color = JarvisTextPrimary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = JarvisTextSecondary
            )
        }
    }
}

