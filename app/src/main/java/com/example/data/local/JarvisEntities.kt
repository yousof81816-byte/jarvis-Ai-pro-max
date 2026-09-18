package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "missions")
data class MissionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val prompt: String,
    val clientPlatform: String,
    val status: String,
    val timestamp: Long,
    val totalTokens: Int,
    val latencyMs: Long,
    val summaryResult: String
)

@Entity(tableName = "mission_steps")
data class StepEntity(
    @PrimaryKey val stepId: String,
    val missionId: String,
    val stepNumber: Int,
    val subsystem: String,
    val actionName: String,
    val description: String,
    val toolUsed: String?,
    val inputPayload: String,
    val outputPayload: String,
    val status: String,
    val latencyMs: Long,
    val tokensUsed: Int,
    val dependencyStepId: String?
)

@Entity(tableName = "memory_vault")
data class MemoryEntity(
    @PrimaryKey val id: String,
    val key: String,
    val value: String,
    val category: String,
    val similarityScore: Float,
    val accessCount: Int,
    val timestamp: Long,
    val tagsRaw: String
)

@Entity(tableName = "security_logs")
data class SecurityLogEntity(
    @PrimaryKey val id: String,
    val eventType: String,
    val severity: String,
    val sourceClient: String,
    val payloadSnippet: String,
    val mitigationApplied: String,
    val timestamp: Long
)
