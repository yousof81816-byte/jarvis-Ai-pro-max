package com.example.data.model

data class AgentMission(
    val id: String,
    val title: String,
    val prompt: String,
    val clientPlatform: ClientPlatform,
    val status: MissionStatus,
    val timestamp: Long = System.currentTimeMillis(),
    val totalTokens: Int = 0,
    val latencyMs: Long = 0,
    val steps: List<ExecutionStep> = emptyList(),
    val summaryResult: String = "",
    val securityLevel: String = "GUARDRAILS_MAX"
)

enum class MissionStatus {
    QUEUED,
    PLANNING,
    EXECUTING,
    COMPLETED,
    FAILED,
    SECURITY_BLOCKED
}

data class ExecutionStep(
    val stepId: String,
    val missionId: String,
    val stepNumber: Int,
    val subsystem: SubsystemType,
    val actionName: String,
    val description: String,
    val toolUsed: String? = null,
    val inputPayload: String = "",
    val outputPayload: String = "",
    val status: StepStatus = StepStatus.PENDING,
    val latencyMs: Long = 0,
    val tokensUsed: Int = 0,
    val dependencyStepId: String? = null
)

enum class StepStatus {
    PENDING,
    RUNNING,
    SUCCESS,
    FAILED,
    SKIPPED
}
