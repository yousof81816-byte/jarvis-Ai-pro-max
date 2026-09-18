package com.example.data.model

data class MemoryEntry(
    val id: String,
    val key: String,
    val value: String,
    val category: MemoryCategory,
    val vectorDimension: Int = 1536,
    val similarityScore: Float = 0.94f,
    val accessCount: Int = 1,
    val timestamp: Long = System.currentTimeMillis(),
    val tags: List<String> = emptyList()
)

enum class MemoryCategory {
    EPISODIC,
    SEMANTIC_VECTOR,
    USER_PREFERENCE,
    KNOWLEDGE_GRAPH,
    SYSTEM_STATE
}

data class SecurityEvent(
    val id: String,
    val eventType: SecurityEventType,
    val severity: SecuritySeverity,
    val sourceClient: ClientPlatform,
    val payloadSnippet: String,
    val mitigationApplied: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class SecurityEventType {
    PROMPT_INJECTION_BLOCKED,
    PII_ANONYMIZED,
    SANDBOX_VIOLATION_PREVENTED,
    RATE_LIMIT_THROTTLED,
    TOOL_PERMISSION_GRANTED
}

enum class SecuritySeverity {
    INFO,
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

data class AIRouteRule(
    val modelName: String,
    val provider: String,
    val targetWorkload: String,
    val costPer1kTokens: Double,
    val avgLatencyMs: Long,
    val contextWindow: String,
    val isPrimary: Boolean,
    val reasoningScore: Int // 1-100
)

data class BrowserSession(
    val url: String,
    val title: String,
    val pageStatus: String,
    val domNodesExtracted: Int,
    val actionHistory: List<BrowserAction>,
    val screenshotTaken: Boolean,
    val extractedData: String = ""
)

data class BrowserAction(
    val type: String, // NAVIGATE, CLICK, EXTRACT, SCREENSHOT, INPUT
    val target: String,
    val timestamp: Long,
    val status: String
)
