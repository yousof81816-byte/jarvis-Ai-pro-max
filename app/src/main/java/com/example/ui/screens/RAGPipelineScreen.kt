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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storage
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
import com.example.data.model.RAGPhase
import com.example.data.model.RAGStepRun
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
fun RAGPipelineScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeRAG by viewModel.activeRAGQuery.collectAsState()
    val isRunning by viewModel.isRunningRAG.collectAsState()

    var searchQuery by remember {
        mutableStateOf("What are the optimal fault-tolerant fallback strategies in JARVIS multi-agent DAG execution?")
    }

    val presetQueries = listOf(
        "Explain JARVIS 1536-dim vector embedding retrieval" to "Vector Embeddings",
        "How does Browser Agent handle dynamic DOM mutations?" to "Browser DOM OCR",
        "Autonomous security firewall and prompt injection heuristics" to "Firewall Security",
        "Multi-model AI router cost-latency optimization algorithm" to "AI Router Matrix"
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
                    .testTag("rag_back_button")
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
                        text = "RAG KNOWLEDGE PIPELINE",
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
                            text = "1536-DIM HNSW VECTOR GRAPH",
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
                    text = "Request ➔ Research ➔ Knowledge Extraction ➔ Vector DB ➔ Retrieval ➔ Response",
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
            // Search Input Block
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(JarvisDarkSurface)
                        .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "SEMANTIC KNOWLEDGE QUERY",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(presetQueries) { (query, label) ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(JarvisDarkCard)
                                    .border(1.dp, JarvisDarkBorder, RoundedCornerShape(16.dp))
                                    .clickable {
                                        searchQuery = query
                                        viewModel.launchRAGQuery(query)
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.Search, contentDescription = null, tint = JarvisCyan, modifier = Modifier.size(12.dp))
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
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("rag_query_input"),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = JarvisDarkCard,
                                unfocusedContainerColor = JarvisDarkCard,
                                focusedBorderColor = JarvisCyan,
                                unfocusedBorderColor = JarvisDarkBorder,
                                focusedTextColor = JarvisTextPrimary,
                                unfocusedTextColor = JarvisTextPrimary
                            ),
                            maxLines = 2
                        )

                        IconButton(
                            onClick = {
                                if (searchQuery.isNotBlank() && !isRunning) {
                                    viewModel.launchRAGQuery(searchQuery)
                                }
                            },
                            enabled = searchQuery.isNotBlank() && !isRunning,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (searchQuery.isNotBlank() && !isRunning) JarvisCyan else JarvisDarkCard)
                                .testTag("rag_execute_button")
                        ) {
                            if (isRunning) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = JarvisDarkBackground, strokeWidth = 2.dp)
                            } else {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Run RAG", tint = JarvisDarkBackground, modifier = Modifier.size(22.dp))
                            }
                        }
                    }
                }
            }

            // 6-Phase Pipeline Stepper
            item {
                Text(
                    text = "6-PHASE VECTOR KNOWLEDGE FLOW",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisCyan
                )
            }

            val rag = activeRAG
            if (rag != null) {
                items(rag.steps) { step ->
                    RAGStepCard(step = step)
                }

                if (rag.finalSynthesis.isNotBlank()) {
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
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = JarvisEmerald, modifier = Modifier.size(18.dp))
                                    Text(
                                        text = "JARVIS SYNTHESIZED RESPONSE (COSINE SIMILARITY: ${rag.cosineSimilarity})",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        ),
                                        color = JarvisEmerald
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = rag.finalSynthesis,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                    color = JarvisTextPrimary
                                )
                            }
                        }
                    }
                }
            } else {
                items(RAGPhase.values()) { phase ->
                    RAGStepCard(
                        step = RAGStepRun(
                            phase = phase,
                            status = StepStatus.PENDING,
                            latencyMs = 0L,
                            detail = phase.description
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun RAGStepCard(step: RAGStepRun, modifier: Modifier = Modifier) {
    val phase = step.phase
    val statusColor = when (step.status) {
        StepStatus.SUCCESS -> JarvisEmerald
        StepStatus.RUNNING -> JarvisAmber
        StepStatus.FAILED -> JarvisRuby
        StepStatus.PENDING, StepStatus.SKIPPED -> JarvisTextMuted
    }

    val icon = when (phase) {
        RAGPhase.USER_REQUEST -> Icons.Default.Psychology
        RAGPhase.RESEARCH_DOCUMENTS -> Icons.Default.FindInPage
        RAGPhase.KNOWLEDGE_EXTRACTION -> Icons.Default.Hub
        RAGPhase.VECTOR_DATABASE -> Icons.Default.Storage
        RAGPhase.KNOWLEDGE_RETRIEVAL -> Icons.Default.Memory
        RAGPhase.JARVIS_RESPONSE -> Icons.Default.CheckCircle
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(
                if (step.status == StepStatus.RUNNING) 1.5.dp else 1.dp,
                if (step.status == StepStatus.RUNNING) JarvisAmber else JarvisDarkBorder,
                RoundedCornerShape(12.dp)
            )
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
                    Text("STEP ${phase.stepNumber}: ${phase.title.uppercase()}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp), color = JarvisTextPrimary)
                    Text(phase.description, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(JarvisDarkCard)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (step.latencyMs > 0) "${step.latencyMs}ms" else step.status.name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    color = statusColor
                )
            }
        }

        if (step.detail.isNotBlank() && step.status != StepStatus.PENDING) {
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
                    text = step.detail,
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 11.sp, lineHeight = 15.sp),
                    color = if (step.status == StepStatus.SUCCESS) JarvisTextPrimary else JarvisAmber
                )
            }
        }
    }
}
