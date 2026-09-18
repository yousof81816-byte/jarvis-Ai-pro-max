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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AIImageGenerationTask
import com.example.data.model.WebsiteDesignProject
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

enum class StudioMode {
    AI_IMAGE_GEN,
    WEBSITE_DESIGN
}

@Composable
fun CreativeStudioScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var studioMode by remember { mutableStateOf(StudioMode.AI_IMAGE_GEN) }

    val imageTasks by viewModel.aiImageTasks.collectAsState()
    val websiteProjects by viewModel.websiteProjects.collectAsState()

    var imagePrompt by remember {
        mutableStateOf("Futuristic holographic neural network core with glowing cyan circuits and glass HUD telemetry in obsidian server room")
    }
    var selectedStyle by remember { mutableStateOf("Cyberpunk Neon") }
    var selectedAspectRatio by remember { mutableStateOf("16:9") }
    var selectedResolution by remember { mutableStateOf("2048x2048") }

    val stylePresets = listOf(
        "Cyberpunk Neon", "3D Octane Render", "Photorealistic 8K", "Anime Sci-Fi", "Minimal Vector Logo"
    )

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
                    .testTag("creative_studio_back")
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
                        text = "DESIGN & CREATIVE STUDIO",
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
                            text = "DIFFUSION + JETPACK COMPOSE",
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
                    text = "AI image synthesis, generative wireframing, and responsive UI code",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = JarvisTextSecondary
                )
            }
        }

        // Mode Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JarvisDarkSurface)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (studioMode == StudioMode.AI_IMAGE_GEN) JarvisCyan.copy(alpha = 0.15f) else JarvisDarkCard)
                    .border(1.dp, if (studioMode == StudioMode.AI_IMAGE_GEN) JarvisCyan else JarvisDarkBorder, RoundedCornerShape(10.dp))
                    .clickable { studioMode = StudioMode.AI_IMAGE_GEN }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Image, contentDescription = null, tint = if (studioMode == StudioMode.AI_IMAGE_GEN) JarvisCyan else JarvisTextMuted, modifier = Modifier.size(14.dp))
                    Text("AI Image Generator", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = if (studioMode == StudioMode.AI_IMAGE_GEN) JarvisCyan else JarvisTextSecondary)
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (studioMode == StudioMode.WEBSITE_DESIGN) JarvisCyan.copy(alpha = 0.15f) else JarvisDarkCard)
                    .border(1.dp, if (studioMode == StudioMode.WEBSITE_DESIGN) JarvisCyan else JarvisDarkBorder, RoundedCornerShape(10.dp))
                    .clickable { studioMode = StudioMode.WEBSITE_DESIGN }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Web, contentDescription = null, tint = if (studioMode == StudioMode.WEBSITE_DESIGN) JarvisCyan else JarvisTextMuted, modifier = Modifier.size(14.dp))
                    Text("Website Design Engine", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = if (studioMode == StudioMode.WEBSITE_DESIGN) JarvisCyan else JarvisTextSecondary)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (studioMode == StudioMode.AI_IMAGE_GEN) {
                // Prompt Builder Box
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
                            text = "DIFFUSION IMAGE PROMPT BUILDER",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = JarvisCyan
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = imagePrompt,
                            onValueChange = { imagePrompt = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("image_prompt_input"),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = JarvisDarkCard,
                                unfocusedContainerColor = JarvisDarkCard,
                                focusedBorderColor = JarvisCyan,
                                unfocusedBorderColor = JarvisDarkBorder,
                                focusedTextColor = JarvisTextPrimary,
                                unfocusedTextColor = JarvisTextPrimary
                            ),
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text("Style Preset:", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(stylePresets) { style ->
                                val isSelected = selectedStyle == style
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSelected) JarvisCyan.copy(alpha = 0.2f) else JarvisDarkCard)
                                        .border(1.dp, if (isSelected) JarvisCyan else JarvisDarkBorder, RoundedCornerShape(12.dp))
                                        .clickable { selectedStyle = style }
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = style,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.5.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (isSelected) JarvisCyan else JarvisTextPrimary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Aspect Ratio
                            listOf("1:1", "16:9", "9:16").forEach { aspect ->
                                val isSelected = selectedAspectRatio == aspect
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) JarvisElectricBlue.copy(alpha = 0.2f) else JarvisDarkCard)
                                        .border(1.dp, if (isSelected) JarvisElectricBlue else JarvisDarkBorder, RoundedCornerShape(8.dp))
                                        .clickable { selectedAspectRatio = aspect }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = aspect,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = if (isSelected) JarvisElectricBlue else JarvisTextSecondary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.generateAIImageTask(imagePrompt, selectedStyle, selectedAspectRatio, selectedResolution)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = JarvisCyan),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("generate_image_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = JarvisDarkBackground, modifier = Modifier.size(16.dp))
                                Text("Synthesize Image with Diffusion", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = JarvisDarkBackground)
                            }
                        }
                    }
                }

                // Image History List
                item {
                    Text(
                        text = "GENERATION GALLERY & ARTIFACTS",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        ),
                        color = JarvisCyan
                    )
                }

                items(imageTasks) { task ->
                    AIImageCard(task = task)
                }
            } else {
                // Website Design Mode
                item {
                    Text(
                        text = "AUTONOMOUS WEBSITE DESIGN PROJECTS",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        ),
                        color = JarvisCyan
                    )
                }

                items(websiteProjects) { web ->
                    WebsiteDesignCard(project = web)
                }
            }
        }
    }
}

@Composable
fun AIImageCard(task: AIImageGenerationTask, modifier: Modifier = Modifier) {
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
                Text(task.id, style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold), color = JarvisCyan)
                Text("Style: ${task.style} • ${task.aspectRatio} • ${task.resolution}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextSecondary)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (task.isGenerating) JarvisAmber.copy(alpha = 0.2f) else JarvisEmerald.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (task.isGenerating) "DIFFUSING..." else "${task.generationTimeMs}ms",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = if (task.isGenerating) JarvisAmber else JarvisEmerald
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Visual Canvas Mockup
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(JarvisDarkCard)
                .border(1.dp, JarvisDarkBorder, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (task.isGenerating) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = JarvisCyan, strokeWidth = 2.dp)
                    Text("Rendering 8K diffusion matrix...", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontFamily = FontFamily.Monospace), color = JarvisCyan)
                }
            } else {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = JarvisCyan, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        task.imageDescription,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center),
                        color = JarvisTextPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tags
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            task.visualTags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(JarvisDarkCard)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("#$tag", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = JarvisElectricBlue))
                }
            }
        }
    }
}

@Composable
fun WebsiteDesignCard(project: WebsiteDesignProject, modifier: Modifier = Modifier) {
    var codeTab by remember { mutableStateOf("PREVIEW") }

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
                Text("${project.businessType} • ${project.framework}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisCyan)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(JarvisEmerald.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("SEO: ${project.seoScore}/100", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = JarvisEmerald))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sections badge
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            project.sections.forEach { section ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(JarvisDarkCard)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(section, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = JarvisTextSecondary))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mini Tab Selector
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("PREVIEW", "HTML", "COMPOSE").forEach { tab ->
                val isSelected = codeTab == tab
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) JarvisCyan.copy(alpha = 0.2f) else JarvisDarkCard)
                        .clickable { codeTab = tab }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(tab, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontWeight = FontWeight.Bold, color = if (isSelected) JarvisCyan else JarvisTextMuted))
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(JarvisDarkCard)
                .border(1.dp, JarvisDarkBorder, RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            when (codeTab) {
                "PREVIEW" -> {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(android.graphics.Color.parseColor(project.primaryColorHex))))
                            Text("Palette: ${project.colorPaletteName}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = JarvisTextPrimary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "✓ Responsive Layout Verified for Mobile & Desktop\n✓ Clean Typography with high contrast\n✓ M3 Components and Glassmorphism Hero HUD",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
                            color = JarvisTextSecondary
                        )
                    }
                }
                "HTML" -> {
                    Text(
                        project.htmlPreviewCode,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 10.sp),
                        color = JarvisCyan
                    )
                }
                "COMPOSE" -> {
                    Text(
                        project.composeSnippet,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 10.sp),
                        color = JarvisElectricBlue
                    )
                }
            }
        }
    }
}
