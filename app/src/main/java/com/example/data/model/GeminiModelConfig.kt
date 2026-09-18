package com.example.data.model

data class GeminiModelOption(
    val id: String,
    val displayName: String,
    val category: String,
    val contextWindow: String,
    val description: String,
    val isRecommended: Boolean = false,
    val isVisionSupported: Boolean = true,
    val isAudioSupported: Boolean = false
)

object GeminiModelCatalog {
    val ALL_MODELS = listOf(
        GeminiModelOption(
            id = "gemini-3.5-flash",
            displayName = "Gemini 3.5 Flash",
            category = "General / Fast",
            contextWindow = "1M tokens",
            description = "Default high-speed multimodal model. Optimal for instant chat, workflow planning, and agent tasks.",
            isRecommended = true,
            isVisionSupported = true,
            isAudioSupported = true
        ),
        GeminiModelOption(
            id = "gemini-3.1-pro-preview",
            displayName = "Gemini 3.1 Pro Preview",
            category = "Complex Reasoning",
            contextWindow = "2M tokens",
            description = "Top-tier flagship model for advanced multi-step coding, STEM reasoning, security audits, and RAG.",
            isRecommended = false,
            isVisionSupported = true,
            isAudioSupported = true
        ),
        GeminiModelOption(
            id = "gemini-3.1-flash-lite-preview",
            displayName = "Gemini 3.1 Flash-Lite",
            category = "Ultra-Low Latency",
            contextWindow = "1M tokens",
            description = "Lightweight high-throughput model built for real-time edge streaming and sub-100ms API pings.",
            isRecommended = false,
            isVisionSupported = true,
            isAudioSupported = false
        ),
        GeminiModelOption(
            id = "gemini-2.5-flash-native-audio-preview-12-2025",
            displayName = "Gemini 2.5 Flash Native Audio",
            category = "Voice & Real-Time Audio",
            contextWindow = "1M tokens",
            description = "Specialized model for live bidirectional conversational speech and audio reasoning.",
            isRecommended = false,
            isVisionSupported = true,
            isAudioSupported = true
        ),
        GeminiModelOption(
            id = "gemini-2.5-flash-image",
            displayName = "Gemini 2.5 Flash Image",
            category = "Image Generation",
            contextWindow = "512k tokens",
            description = "Fast diffusion and multimodal image editing model for creative assets and UI generation.",
            isRecommended = false,
            isVisionSupported = true,
            isAudioSupported = false
        ),
        GeminiModelOption(
            id = "gemini-3.1-flash-image-preview",
            displayName = "Gemini 3.1 Flash Image (4K)",
            category = "Ultra-HD Image",
            contextWindow = "512k tokens",
            description = "Ultra high-resolution image synthesis supporting 1K, 2K, and 4K output generation.",
            isRecommended = false,
            isVisionSupported = true,
            isAudioSupported = false
        ),
        GeminiModelOption(
            id = "gemini-2.5-flash-preview-tts",
            displayName = "Gemini 2.5 Flash TTS",
            category = "Text-to-Speech",
            contextWindow = "1M tokens",
            description = "Direct neural expressive voice generation with multiple timbre and pitch profiles.",
            isRecommended = false,
            isVisionSupported = false,
            isAudioSupported = true
        ),
        GeminiModelOption(
            id = "gemini-flash-latest",
            displayName = "Gemini Flash Latest",
            category = "Stable Release",
            contextWindow = "1M tokens",
            description = "Always points to the newest stable production Flash release across Google Cloud APIs.",
            isRecommended = false,
            isVisionSupported = true,
            isAudioSupported = true
        )
    )
}

enum class VoiceAgentState {
    IDLE,
    LISTENING,
    THINKING,
    SPEAKING,
    ERROR
}

enum class VoicePersonality(
    val id: String,
    val title: String,
    val subtitle: String,
    val pitchMultiplier: Float,
    val speechRateMultiplier: Float
) {
    JARVIS("jarvis", "J.A.R.V.I.S.", "Sophisticated, authoritative & calm", 0.95f, 1.05f),
    FRIDAY("friday", "F.R.I.D.A.Y.", "Tactical, efficient & crisp assistant", 1.15f, 1.10f),
    SAMANTHA("samantha", "Samantha", "Warm, expressive & friendly", 1.05f, 1.0f),
    KORE("kore", "Kore", "Crystal-clear studio broadcast timbre", 1.0f, 1.0f),
    FENRIR("fenrir", "Fenrir", "Deep resonant commanding voice", 0.85f, 0.95f),
    AOEDE("aoede", "Aoede", "Melodic & elegant narrative voice", 1.10f, 1.02f),
    PUCK("puck", "Puck", "Energetic, dynamic & quick response", 1.25f, 1.15f)
}

data class VoiceMessage(
    val id: String,
    val sender: VoiceSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val languageCode: String = "en-US",
    val latencyMs: Long = 0L
)

enum class VoiceSender {
    USER,
    JARVIS
}

data class ApiKeyTestResult(
    val isSuccess: Boolean,
    val message: String,
    val latencyMs: Long = 0L,
    val modelTested: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
