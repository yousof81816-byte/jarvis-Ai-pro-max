package com.example.data.model

// ==================== HR MODELS ====================
data class HREmployee(
    val id: String,
    val name: String,
    val role: String,
    val department: String,
    val performanceRating: Float, // 1.0 - 5.0
    val attendanceRate: Float, // e.g. 98.5%
    val status: String, // ACTIVE, ON_LEAVE, ONBOARDING
    val avatarInitials: String
)

data class HRCandidateCV(
    val id: String,
    val candidateName: String,
    val targetRole: String,
    val experienceYears: Int,
    val matchScore: Int, // 1 - 100
    val keySkills: List<String>,
    val aiAnalysisSummary: String,
    val interviewStatus: String // PENDING, SCHEDULED, PASSED, REJECTED
)

data class HRInterview(
    val id: String,
    val candidateName: String,
    val role: String,
    val dateString: String,
    val timeString: String,
    val interviewers: String,
    val automatedQuestionCount: Int = 5
)

data class HROnboardingTask(
    val id: String,
    val employeeName: String,
    val title: String,
    val isCompleted: Boolean,
    val category: String
)

data class HRLeaveRequest(
    val id: String,
    val employeeName: String,
    val leaveType: String, // ANNUAL, SICK, REMOTE, PARENTAL
    val durationDays: Int,
    val status: String, // APPROVED, PENDING, REJECTED
    val aiRecommendation: String
)

// ==================== SALES MODELS ====================
data class SalesLead(
    val id: String,
    val contactName: String,
    val company: String,
    val dealValueUsd: Long,
    val leadScore: Int, // 1 - 100
    val stage: String, // DISCOVERY, QUALIFIED, PROPOSAL_SENT, NEGOTIATION, WON
    val lastContactDate: String,
    val priority: String, // HIGH, MEDIUM, LOW
    val aiGeneratedEmailDraft: String,
    val recommendedNextStep: String
)

data class SalesProposal(
    val id: String,
    val clientName: String,
    val projectTitle: String,
    val amountUsd: Long,
    val validityDate: String,
    val status: String, // DRAFT, AI_GENERATED, SENT, SIGNED
    val executiveSummary: String
)

data class SalesCRMActivity(
    val id: String,
    val clientName: String,
    val activityType: String, // CALL, EMAIL, DEMO, CONTRACT
    val timestamp: String,
    val sentiment: String // POSITIVE, NEUTRAL, CRITICAL
)

// ==================== MANAGEMENT & PROJECTS MODELS ====================
data class JarvisProject(
    val id: String,
    val title: String,
    val description: String,
    val domain: String, // HR, SALES, TECH, DESIGN
    val progressPercentage: Int, // 0 - 100
    val sprintNumber: Int,
    val activeTasksCount: Int,
    val riskLevel: String, // LOW, MEDIUM, HIGH
    val deadline: String
)

data class ProjectTaskItem(
    val id: String,
    val projectId: String,
    val title: String,
    val assignedAgent: String, // PLANNER, EXECUTOR, BROWSER_AGENT, HR_AGENT, SALES_AGENT
    val isCompleted: Boolean,
    val priority: String,
    val estimatedHours: Int
)

// ==================== CROSS-MODULE ANALYTICS ====================
data class EnterpriseAnalyticsSummary(
    val activeEmployees: Int = 148,
    val cvAnalysisCompletedToday: Int = 34,
    val salesPipelineUsd: Long = 1845000L,
    val activeLeadsCount: Int = 89,
    val closedWonDealsCount: Int = 24,
    val ongoingProjectsCount: Int = 12,
    val autonomousTasksCompleted: Int = 4120,
    val humanHoursSavedEstimated: Int = 860,
    val systemHealthRate: Float = 99.8f
)
