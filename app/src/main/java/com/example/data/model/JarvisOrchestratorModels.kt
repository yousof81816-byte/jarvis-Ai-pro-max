package com.example.data.model

data class ToolManagerItem(
    val id: String,
    val name: String,
    val category: String, // SYSTEM, BROWSER, DATABASE, CODE, NETWORK, AI
    val description: String,
    val isEnabled: Boolean = true,
    val callCount: Int = 0,
    val avgRuntimeMs: Long = 45L,
    val sandboxed: Boolean = true
)

data class ValidatorRule(
    val id: String,
    val name: String,
    val targetSubsystem: String,
    val checkType: String, // SYNTAX_CHECK, SECURITY_SANDBOX, ACCURACY_TEST, JSON_SCHEMA, PERFORMANCE
    val isStrict: Boolean = true,
    val passRatePercentage: Float = 99.4f,
    val description: String
)

data class RecoveryStrategy(
    val id: String,
    val failureTrigger: String,
    val recoveryAction: String,
    val fallbackSubsystem: String,
    val autoRetryLimit: Int = 3,
    val successRate: Float = 98.7f
)

enum class SDLCPhase(val stepNumber: Int, val title: String, val description: String) {
    UNDERSTAND_REQUEST(1, "Understand Request", "Parse user intent, extract constraints, and formulate semantic goal"),
    CREATE_PLAN(2, "Create Plan", "Decompose into DAG tasks, calculate dependencies, and assign subsystems"),
    RESEARCH(3, "Research", "Retrieve documentation, query RAG vector store, and search web for best practices"),
    GENERATE_DESIGN(4, "Generate Design", "Produce architectural diagrams, UI wireframes, schemas, and API contracts"),
    WRITE_CODE(5, "Write Code", "Synthesize production-grade Kotlin, Python, TypeScript, or SQL implementations"),
    TEST(6, "Test", "Execute sandboxed unit tests, verify edge cases, and run lint audits"),
    FIX_ERRORS(7, "Fix Errors", "Self-healing auto-debugger patches failed assertions and recompiles"),
    DEPLOY(8, "Deploy", "Containerize Docker images, stage to cloud clusters, and verify health checks"),
    REPORT_RESULT(9, "Report Result", "Compile executive metrics, delivery artifacts, and user summary")
}

data class SDLCStepRun(
    val phase: SDLCPhase,
    val status: StepStatus = StepStatus.PENDING,
    val outputLog: String = "",
    val durationMs: Long = 0L,
    val artifactSnippet: String? = null
)

data class SDLCWorkflowRun(
    val id: String,
    val title: String,
    val goalPrompt: String,
    val currentPhase: SDLCPhase = SDLCPhase.UNDERSTAND_REQUEST,
    val steps: List<SDLCStepRun> = emptyList(),
    val isCompleted: Boolean = false,
    val totalTimeMs: Long = 0L,
    val outputArtifact: String = ""
)

enum class RAGPhase(val stepNumber: Int, val title: String, val iconName: String, val description: String) {
    USER_REQUEST(1, "User Request", "Person", "Receive input query from client platform"),
    RESEARCH_DOCUMENTS(2, "Research / Web / Docs", "TravelExplore", "Scan indexed knowledge base and docs"),
    KNOWLEDGE_EXTRACTION(3, "Knowledge Extraction", "Psychology", "Parse tokens and semantic entity graphs"),
    VECTOR_DATABASE(4, "Vector Database (1536d)", "Storage", "Generate 1536-dim embeddings with HNSW index"),
    KNOWLEDGE_RETRIEVAL(5, "Knowledge Retrieval", "FindInPage", "Retrieve top-K nearest neighbors via cosine similarity"),
    JARVIS_RESPONSE(6, "JARVIS Response", "AutoAwesome", "Synthesize context-grounded final response")
}

data class RAGStepRun(
    val phase: RAGPhase,
    val status: StepStatus = StepStatus.PENDING,
    val latencyMs: Long = 0L,
    val detail: String = ""
)

data class RAGQueryRun(
    val id: String,
    val query: String,
    val retrievedChunksCount: Int = 4,
    val cosineSimilarity: Float = 0.94f,
    val steps: List<RAGStepRun> = emptyList(),
    val finalSynthesis: String = ""
)
