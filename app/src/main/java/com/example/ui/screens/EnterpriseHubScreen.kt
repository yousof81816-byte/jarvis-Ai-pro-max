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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.HRCandidateCV
import com.example.data.model.HREmployee
import com.example.data.model.HROnboardingTask
import com.example.data.model.JarvisProject
import com.example.data.model.SalesLead
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

enum class EnterpriseTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    HR("HR Suite", Icons.Default.People),
    SALES("Sales CRM", Icons.Default.MonetizationOn),
    PROJECTS("Projects", Icons.Default.Assignment),
    ANALYTICS("Analytics", Icons.Default.Analytics)
}

@Composable
fun EnterpriseHubScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(EnterpriseTab.HR) }

    val employees by viewModel.employees.collectAsState()
    val candidates by viewModel.candidates.collectAsState()
    val onboardingTasks by viewModel.onboardingTasks.collectAsState()
    val salesLeads by viewModel.salesLeads.collectAsState()
    val projects by viewModel.projects.collectAsState()
    val analytics by viewModel.analyticsSummary.collectAsState()

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
                    .testTag("enterprise_back_button")
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
                        text = "ENTERPRISE BUSINESS SUITE",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = JarvisCyan
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(JarvisElectricBlue.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "JARVIS ➔ HR | SALES | PROJECTS ➔ ANALYTICS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = JarvisElectricBlue
                        )
                    }
                }
                Text(
                    text = "Autonomous workforce, pipeline scoring, and executive intelligence",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
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
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EnterpriseTab.values().forEach { tab ->
                val isSelected = selectedTab == tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) JarvisCyan.copy(alpha = 0.15f) else JarvisDarkCard)
                        .border(1.dp, if (isSelected) JarvisCyan else JarvisDarkBorder, RoundedCornerShape(10.dp))
                        .clickable { selectedTab = tab }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) JarvisCyan else JarvisTextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            ),
                            color = if (isSelected) JarvisCyan else JarvisTextSecondary
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (selectedTab) {
                EnterpriseTab.HR -> {
                    // HR Section: CV Analysis & Screening
                    item {
                        SectionHeader(title = "AI RECRUITMENT & CV ANALYSIS", count = "${candidates.size} Candidates")
                    }

                    items(candidates) { cv ->
                        CandidateCVCard(cv = cv, onAnalyze = { viewModel.analyzeCandidateCV(cv.id) })
                    }

                    // Onboarding Checklist
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                        SectionHeader(title = "AUTONOMOUS ONBOARDING WORKFLOW", count = "${onboardingTasks.count { it.isCompleted }}/${onboardingTasks.size} Done")
                    }

                    items(onboardingTasks) { task ->
                        OnboardingTaskCard(task = task, onToggle = { viewModel.toggleOnboardingTask(task.id) })
                    }

                    // Employee Roster
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                        SectionHeader(title = "ACTIVE EMPLOYEE ROSTER & ATTENDANCE", count = "${employees.size} Members")
                    }

                    items(employees) { emp ->
                        EmployeeItemCard(employee = emp)
                    }
                }

                EnterpriseTab.SALES -> {
                    // Sales Leads & Scoring
                    item {
                        SectionHeader(title = "SALES LEADS & AI SCORING CRM", count = "${salesLeads.size} Deals")
                    }

                    items(salesLeads) { lead ->
                        SalesLeadCard(
                            lead = lead,
                            onDraftEmail = { viewModel.generateSalesEmailDraft(lead.id) }
                        )
                    }
                }

                EnterpriseTab.PROJECTS -> {
                    item {
                        SectionHeader(title = "MANAGEMENT & PROJECT SPRINT ROADMAP", count = "${projects.size} Active")
                    }

                    items(projects) { prj ->
                        JarvisProjectCard(project = prj)
                    }
                }

                EnterpriseTab.ANALYTICS -> {
                    item {
                        SectionHeader(title = "CROSS-MODULE ENTERPRISE ROI & VELOCITY", count = "Live KPIs")
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(JarvisDarkSurface)
                                .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "EXECUTIVE SUMMARY MATRIX",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = JarvisCyan
                            )

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                AnalyticsMetricTile(
                                    label = "Active Pipeline Value",
                                    value = "$1.84M",
                                    trend = "+18.4%",
                                    color = JarvisEmerald,
                                    modifier = Modifier.weight(1f)
                                )
                                AnalyticsMetricTile(
                                    label = "Hours Saved (AI)",
                                    value = "860 hrs",
                                    trend = "+42 hrs/wk",
                                    color = JarvisCyan,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                AnalyticsMetricTile(
                                    label = "Autonomous Tasks",
                                    value = "4,120",
                                    trend = "99.8% Pass",
                                    color = JarvisElectricBlue,
                                    modifier = Modifier.weight(1f)
                                )
                                AnalyticsMetricTile(
                                    label = "System Health SLA",
                                    value = "99.8%",
                                    trend = "18ms Latency",
                                    color = JarvisAmber,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, count: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            ),
            color = JarvisCyan
        )
        Text(
            text = count,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            ),
            color = JarvisTextSecondary
        )
    }
}

@Composable
fun CandidateCVCard(cv: HRCandidateCV, onAnalyze: () -> Unit, modifier: Modifier = Modifier) {
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
                Text(cv.candidateName, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
                Text("${cv.targetRole} • ${cv.experienceYears}y exp", style = MaterialTheme.typography.labelSmall, color = JarvisTextSecondary)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (cv.matchScore >= 90) JarvisEmerald.copy(alpha = 0.15f) else JarvisAmber.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "MATCH: ${cv.matchScore}%",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = if (cv.matchScore >= 90) JarvisEmerald else JarvisAmber
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Skills chips
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            cv.keySkills.forEach { skill ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(JarvisDarkCard)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(skill, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp), color = JarvisCyan)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = cv.aiAnalysisSummary,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
            color = JarvisTextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Status: ${cv.interviewStatus}",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                color = JarvisElectricBlue
            )

            Button(
                onClick = onAnalyze,
                colors = ButtonDefaults.buttonColors(containerColor = JarvisCyan),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.Psychology, contentDescription = null, tint = JarvisDarkBackground, modifier = Modifier.size(14.dp))
                    Text("AI Re-Analyze & Schedule", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp, fontWeight = FontWeight.Bold), color = JarvisDarkBackground)
                }
            }
        }
    }
}

@Composable
fun OnboardingTaskCard(task: HROnboardingTask, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(8.dp))
            .clickable { onToggle() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.Assignment,
            contentDescription = null,
            tint = if (task.isCompleted) JarvisEmerald else JarvisTextMuted,
            modifier = Modifier.size(18.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(task.title, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold), color = JarvisTextPrimary)
            Text("Assignee: ${task.employeeName} • ${task.category}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = JarvisTextSecondary)
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (task.isCompleted) JarvisEmerald.copy(alpha = 0.2f) else JarvisDarkCard)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                if (task.isCompleted) "COMPLETE" else "IN PROGRESS",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.5.sp, fontFamily = FontFamily.Monospace),
                color = if (task.isCompleted) JarvisEmerald else JarvisAmber
            )
        }
    }
}

@Composable
fun EmployeeItemCard(employee: HREmployee, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(JarvisDarkSurface)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(JarvisDarkCard)
                .border(1.dp, JarvisCyan.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(employee.avatarInitials, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = JarvisCyan)
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(employee.name, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
            Text("${employee.role} • ${employee.department}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text("Rating: ${employee.performanceRating}★", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = JarvisAmber)
            Text("Attn: ${employee.attendanceRate}%", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontFamily = FontFamily.Monospace), color = JarvisEmerald)
        }
    }
}

@Composable
fun SalesLeadCard(lead: SalesLead, onDraftEmail: () -> Unit, modifier: Modifier = Modifier) {
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
                Text(lead.company, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
                Text("Contact: ${lead.contactName} • Deal: $${lead.dealValueUsd / 1000}k", style = MaterialTheme.typography.labelSmall, color = JarvisTextSecondary)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(JarvisEmerald.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "SCORE: ${lead.leadScore}/100",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = JarvisEmerald
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(JarvisDarkCard)
                .padding(8.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = JarvisCyan, modifier = Modifier.size(12.dp))
                    Text("AI DRAFTED OUTREACH EMAIL", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold), color = JarvisCyan)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(lead.aiGeneratedEmailDraft, style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 15.sp), color = JarvisTextPrimary)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Stage: ${lead.stage}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontFamily = FontFamily.Monospace), color = JarvisAmber)

            Button(
                onClick = onDraftEmail,
                colors = ButtonDefaults.buttonColors(containerColor = JarvisCyan),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(30.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = JarvisDarkBackground, modifier = Modifier.size(12.dp))
                    Text("Re-Draft & Send", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold), color = JarvisDarkBackground)
                }
            }
        }
    }
}

@Composable
fun JarvisProjectCard(project: JarvisProject, modifier: Modifier = Modifier) {
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
                Text(project.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = JarvisTextPrimary)
                Text(project.description, style = MaterialTheme.typography.labelSmall, color = JarvisTextSecondary)
            }
            Text(
                text = "${project.progressPercentage}%",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = JarvisCyan
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(JarvisDarkCard)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(project.progressPercentage / 100f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(JarvisCyan)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Sprint ${project.sprintNumber} • ${project.activeTasksCount} Agent Tasks", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp), color = JarvisTextSecondary)
            Text("Risk: ${project.riskLevel} • Deadline: ${project.deadline}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, color = JarvisEmerald))
        }
    }
}

@Composable
fun AnalyticsMetricTile(
    label: String,
    value: String,
    trend: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(JarvisDarkCard)
            .border(1.dp, JarvisDarkBorder, RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace), color = color)
            Spacer(modifier = Modifier.height(2.dp))
            Text(trend, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold), color = color)
        }
    }
}
