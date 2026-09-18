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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
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
import com.example.data.model.SDLCPhase
import com.example.data.model.SDLCStepRun
import com.example.data.model.StepStatus
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

@Composable
fun SDLCWorkflowScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeWorkflow by viewModel.activeSDLCWorkflow.collectAsState()
    val isRunning by viewModel.isRunningSDLC.collectAsState()

    var customGoalInput by remember {
        mutableStateOf("Build a high-performance reactive WebSocket microservice in Kotlin with Room DB and automated test recovery")
    }

    val presetGoals = listOf(
        "Build a high-performance reactive WebSocket microservice in Kotlin with Room DB and automated test recovery" to "Reactive Microservice",
        "Generate SaaS Landing Page with dark mode, interactive 3D canvas, and lead capture form" to "SaaS Landing Page",
        "Autonomous Vulnerability Scanner & zero-day patch generator for REST API endpoints" to "Security Patch Engine",
        "Design and implement an end-to-end RAG Document QA Pipeline with 1536-dim vector indexing" to "Vector QA Pipeline"
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
                    .testTag("sdlc_back_button")
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
                        text = "AUTONOMOUS SDLC PIPELINE",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = JarvisCyan
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isRunning) JarvisAmber.copy(alpha = 0.2f) else JarvisEmerald.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (isRunning) "EXECUTING 9-PHASE" else "READY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            ),
                            color = if (isRunning) JarvisAmber else JarvisEmerald
                        )
                    }
                }
                Text(
                    text = "Understand ➔ Plan ➔ Research ➔ Design ➔ Code ➔ Test ➔ Fix ➔ Deploy ➔ Report",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontFamily = FontFamily.Monospace),
                    color = JarvisTextSecondary
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Section 1: Goal Specification & Trigger
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(JarvisDarkSurface)
                        .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Terminal, contentDescription = null, tint = JarvisCyan, modifier = Modifier.size(16.dp))
                            Text(
                                text = "TASK SPECIFICATION & ARCHITECTURE GOAL",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                ),
                                color = JarvisTextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(presetGoals) { (goal, label) ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(JarvisDarkCard)
                                    .border(1.dp, JarvisDarkBorder, RoundedCornerShape(16.dp))
                                    .clickable {
                                        customGoalInput = goal
                                        viewModel.launchSDLCWorkflow(goal, label)
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = JarvisCyan, modifier = Modifier.size(12.dp))
                                    Text(label, style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = JarvisTextPrimary)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = customGoalInput,
                            onValueChange = { customGoalInput = it },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("sdlc_prompt_input"),
                            shape = RoundedCornerShape(10.dp),
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
                                if (customGoalInput.isNotBlank() && !isRunning) {
                                    viewModel.launchSDLCWorkflow(customGoalInput, "Autonomous Engineering Cycle")
                                }
                            },
                            enabled = customGoalInput.isNotBlank() && !isRunning,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (customGoalInput.isNotBlank() && !isRunning) JarvisCyan else JarvisDarkCard)
                                .testTag("sdlc_start_button")
                        ) {
                            if (isRunning) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = JarvisDarkBackground, strokeWidth = 2.dp)
                            } else {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Run SDLC",
                                    tint = if (customGoalInput.isNotBlank()) JarvisDarkBackground else JarvisTextMuted,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Section 2: Interactive 9-Step Pipeline Stepper
            item {
                Text(
                    text = "9-PHASE AUTONOMOUS EXECUTION GRAPH",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisCyan
                )
            }

            val workflow = activeWorkflow
            if (workflow != null) {
                items(workflow.steps) { step ->
                    SDLCStepCard(step = step, isCurrentPhase = workflow.currentPhase == step.phase)
                }

                if (workflow.isCompleted) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(JarvisDarkSurface)
                                .border(1.5.dp, JarvisEmerald, RoundedCornerShape(12.dp))
                                .padding(14.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = JarvisEmerald, modifier = Modifier.size(18.dp))
                                    Text(
                                        text = "DEPLOYMENT COMPLETE (ALL 9 PHASES VERIFIED)",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        ),
                                        color = JarvisEmerald
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = workflow.outputArtifact,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                    color = JarvisTextPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Total Pipeline Time: ${workflow.totalTimeMs}ms • Zero Human Intervention",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.5.sp,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    color = JarvisTextSecondary
                                )
                            }
                        }
                    }
                }
            } else {
                // Show default idle phase preview
                items(SDLCPhase.values()) { phase ->
                    SDLCStepCard(
                        step = SDLCStepRun(
                            phase = phase,
                            status = StepStatus.PENDING,
                            outputLog = phase.description,
                            durationMs = 0L
                        ),
                        isCurrentPhase = false
                    )
                }
            }
        }
    }
}

@Composable
fun SDLCStepCard(
    step: SDLCStepRun,
    isCurrentPhase: Boolean,
    modifier: Modifier = Modifier
) {
    val phase = step.phase
    val statusColor = when (step.status) {
        StepStatus.SUCCESS -> JarvisEmerald
        StepStatus.RUNNING -> JarvisAmber
        StepStatus.FAILED -> JarvisRuby
        StepStatus.PENDING, StepStatus.SKIPPED -> JarvisTextMuted
    }

    val icon = when (phase) {
        SDLCPhase.UNDERSTAND_REQUEST -> Icons.Default.Psychology
        SDLCPhase.CREATE_PLAN -> Icons.Default.DeveloperBoard
        SDLCPhase.RESEARCH -> Icons.Default.Search
        SDLCPhase.GENERATE_DESIGN -> Icons.Default.AutoAwesome
        SDLCPhase.WRITE_CODE -> Icons.Default.Code
        SDLCPhase.TEST -> Icons.Default.Build
        SDLCPhase.FIX_ERRORS -> Icons.Default.Build
        SDLCPhase.DEPLOY -> Icons.Default.RocketLaunch
        SDLCPhase.REPORT_RESULT -> Icons.Default.CheckCircle
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(
                if (isCurrentPhase && step.status == StepStatus.RUNNING) 1.5.dp else 1.dp,
                if (isCurrentPhase && step.status == StepStatus.RUNNING) JarvisAmber else JarvisDarkBorder,
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
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
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(statusColor.copy(alpha = 0.15f))
                        .border(1.dp, statusColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (step.status == StepStatus.RUNNING) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = JarvisAmber, strokeWidth = 2.dp)
                    } else {
                        Icon(imageVector = icon, contentDescription = phase.title, tint = statusColor, modifier = Modifier.size(16.dp))
                    }
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "PHASE ${phase.stepNumber}: ${phase.title.uppercase()}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            ),
                            color = JarvisTextPrimary
                        )
                    }
                    Text(
                        text = phase.description,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = JarvisTextSecondary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(JarvisDarkCard)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (step.durationMs > 0) "${step.durationMs}ms" else step.status.name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    color = statusColor
                )
            }
        }

        if (step.outputLog.isNotBlank() && step.status != StepStatus.PENDING) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(JarvisDarkCard)
                    .border(1.dp, JarvisDarkBorder, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = step.outputLog,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    ),
                    color = if (step.status == StepStatus.SUCCESS) JarvisTextPrimary else JarvisAmber
                )
            }
        }
    }
}
