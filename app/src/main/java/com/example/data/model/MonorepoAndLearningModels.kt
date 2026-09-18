package com.example.data.model

data class MonorepoNode(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val description: String,
    val children: List<MonorepoNode> = emptyList(),
    val fileLanguage: String? = null,
    val codeSnippet: String? = null
)

data class SelfLearningCheckpoint(
    val id: String,
    val cycleNumber: Int,
    val timestamp: Long,
    val benchmarkTask: String,
    val accuracyScore: Float, // 0.0 - 100.0%
    val reasoningLatencyMs: Long,
    val selfCorrectionCount: Int,
    val memoryInsightsAbsorbed: Int,
    val reflectionSummary: String
)

data class SelfImprovementState(
    val totalCyclesRun: Int = 1840,
    val autonomousAccuracyRate: Float = 98.4f,
    val errorAutoFixSuccessRate: Float = 94.2f,
    val activeLearningRate: Float = 0.0035f,
    val memoryRetentionScore: Float = 99.1f,
    val recentCheckpoints: List<SelfLearningCheckpoint> = emptyList()
)
