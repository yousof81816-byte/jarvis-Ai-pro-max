package com.example.data.model

data class AIImagePromptPreset(
    val title: String,
    val prompt: String,
    val style: String,
    val colorTone: String
)

data class AIImageGenerationTask(
    val id: String,
    val prompt: String,
    val enhancedPrompt: String,
    val style: String, // HOLOGRAPHIC, CYBERPUNK, MINIMALIST, PHOTOREALISTIC, UI_WIREFRAME
    val aspectRatio: String, // 1:1, 16:9, 9:16, 4:3
    val resolution: String, // 1024x1024, 1920x1080, 2048x2048
    val isGenerating: Boolean = false,
    val seed: Long = 482910L,
    val generationTimeMs: Long = 1840L,
    val imageDescription: String = "",
    val visualTags: List<String> = emptyList()
)

data class WebsiteDesignProject(
    val id: String,
    val title: String,
    val businessType: String, // SAAS, ECOMMERCE, PORTFOLIO, ENTERPRISE, FINTECH
    val colorPaletteName: String,
    val primaryColorHex: String,
    val secondaryColorHex: String,
    val framework: String, // NEXT_JS, JETPACK_COMPOSE, REACT, TAILWIND
    val sections: List<String>,
    val htmlPreviewCode: String,
    val composeSnippet: String,
    val seoScore: Int = 98,
    val mobileResponsive: Boolean = true
)
