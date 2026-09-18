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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.JarvisCyan
import com.example.ui.theme.JarvisDarkBackground
import com.example.ui.theme.JarvisDarkBorder
import com.example.ui.theme.JarvisDarkCard
import com.example.ui.theme.JarvisDarkSurface
import com.example.ui.theme.JarvisEmerald
import com.example.ui.theme.JarvisTextMuted
import com.example.ui.theme.JarvisTextPrimary
import com.example.ui.theme.JarvisTextSecondary
import com.example.ui.theme.PlannerColor
import com.example.ui.viewmodel.JarvisViewModel

data class DagNode(
    val id: String,
    val title: String,
    val type: String,
    val dependencies: List<String>,
    val status: String,
    val estLatencyMs: Long
)

@Composable
fun PlannerScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var customGoalInput by remember { mutableStateOf("") }
    var dagNodes by remember {
        mutableStateOf(
            listOf(
                DagNode("DAG-01", "Deconstruct Goal Semantics", "NLP_PARSE", emptyList(), "COMPLETED", 40L),
                DagNode("DAG-02", "Vector Memory Context Enrichment", "SEMANTIC_RECALL", listOf("DAG-01"), "COMPLETED", 60L),
                DagNode("DAG-03", "Headless Web Intelligence Search", "BROWSER_AGENT", listOf("DAG-02"), "IN_PROGRESS", 220L),
                DagNode("DAG-04", "Sandboxed Code Execution & Verification", "TOOL_RUNNER", listOf("DAG-03"), "READY", 180L),
                DagNode("DAG-05", "Multi-Agent Synthesis & Artifact Packaging", "SYNTHESIS", listOf("DAG-04"), "PENDING", 120L)
            )
        )
    }

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
                    .background(PlannerColor.copy(alpha = 0.2f))
                    .border(1.dp, PlannerColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountTree,
                    contentDescription = "Planner Subsystem",
                    tint = PlannerColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = "PLANNER SUBSYSTEM (PLN-01)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisTextPrimary
                )
                Text(
                    text = "Directed Acyclic Graph (DAG) • Goal Decomposition • Milestone Tree",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = PlannerColor
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Section 1: Goal Decomposition Generator
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
                        text = "DECOMPOSE AUTONOMOUS GOAL",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = PlannerColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customGoalInput,
                        onValueChange = { customGoalInput = it },
                        placeholder = {
                            Text(
                                "Enter target high-level goal (e.g., 'Deploy multi-region cloud cluster')...",
                                style = MaterialTheme.typography.bodySmall,
                                color = JarvisTextMuted
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = JarvisDarkCard,
                            unfocusedContainerColor = JarvisDarkCard,
                            focusedBorderColor = PlannerColor,
                            unfocusedBorderColor = JarvisDarkBorder,
                            focusedTextColor = JarvisTextPrimary,
                            unfocusedTextColor = JarvisTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            if (customGoalInput.isNotBlank()) {
                                val goal = customGoalInput
                                customGoalInput = ""
                                dagNodes = listOf(
                                    DagNode("DAG-10", "Analyze: $goal", "GOAL_PARSE", emptyList(), "COMPLETED", 35L),
                                    DagNode("DAG-11", "Retrieve Constraints & Credentials", "MEMORY_QUERY", listOf("DAG-10"), "COMPLETED", 50L),
                                    DagNode("DAG-12", "Execute Autonomous Sub-actions", "TOOL_DISPATCH", listOf("DAG-11"), "IN_PROGRESS", 280L),
                                    DagNode("DAG-13", "Validate Deliverable Health & Security", "AUDIT_VERIFY", listOf("DAG-12"), "READY", 90L)
                                )
                                viewModel.appendConsoleLog("🌲 [PLANNER] Generated new 4-node execution DAG for: $goal")
                            }
                        },
                        enabled = customGoalInput.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = PlannerColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Generate DAG",
                            tint = JarvisDarkBackground,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "GENERATE EXECUTION DAG",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = JarvisDarkBackground
                        )
                    }
                }
            }

            // Section 2: Active DAG Node Visualizer
            item {
                Text(
                    text = "ACTIVE DEPENDENCY GRAPH (5 NODES)",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisCyan
                )
            }

            items(dagNodes) { node ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(JarvisDarkSurface)
                        .border(
                            1.dp,
                            if (node.status == "IN_PROGRESS") PlannerColor else JarvisDarkBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            when (node.status) {
                                                "COMPLETED" -> JarvisEmerald
                                                "IN_PROGRESS" -> PlannerColor
                                                "READY" -> JarvisCyan
                                                else -> JarvisTextMuted
                                            },
                                            CircleShape
                                        )
                                )
                                Text(
                                    text = node.id,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = PlannerColor
                                )
                                Text(
                                    text = node.title,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = JarvisTextPrimary
                                )
                            }

                            Text(
                                text = node.status,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = when (node.status) {
                                        "COMPLETED" -> JarvisEmerald
                                        "IN_PROGRESS" -> PlannerColor
                                        else -> JarvisTextMuted
                                    }
                                ),
                                modifier = Modifier
                                    .background(JarvisDarkCard, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "DEPS: " + if (node.dependencies.isEmpty()) "ROOT NODE" else node.dependencies.joinToString(", "),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = JarvisTextSecondary
                            )
                            Text(
                                text = "EST: ${node.estLatencyMs}ms",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = JarvisTextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}
