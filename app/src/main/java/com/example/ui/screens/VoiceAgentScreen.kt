package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.data.model.VoiceAgentState
import com.example.data.model.VoiceMessage
import com.example.data.model.VoicePersonality
import com.example.data.model.VoiceSender
import com.example.ui.theme.JarvisAmber
import com.example.ui.theme.JarvisCyan
import com.example.ui.theme.JarvisCyanGlow
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
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VoiceAgentScreen(
    viewModel: JarvisViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val voiceState by viewModel.voiceAgentState.collectAsState()
    val audioLevel by viewModel.voiceAudioLevel.collectAsState()
    val partialText by viewModel.recognizedPartialText.collectAsState()
    val messages by viewModel.voiceMessages.collectAsState()
    val personality by viewModel.selectedVoicePersonality.collectAsState()
    val languageCode by viewModel.selectedVoiceLanguage.collectAsState()
    val isAutoSpeak by viewModel.isAutoSpeakEnabled.collectAsState()
    val selectedModelId by viewModel.selectedGeminiModelId.collectAsState()

    var textInput by remember { mutableStateOf("") }
    var hasRecordPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasRecordPermission = isGranted
        if (isGranted) {
            viewModel.startVoiceListening()
        }
    }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickVoiceCommands = listOf(
        "JARVIS, আজকের সিস্টেম স্ট্যাটাস কী?" to "System Status (বাংলা)",
        "What are the capabilities of Gemini 3.5 Flash?" to "Gemini Specs",
        "Explain multi-model autonomous agent architecture" to "Agent Architecture",
        "আমার জন্য একটি পাইথন স্ক্রিপ্ট লিখে দাও" to "Python Script (বাংলা)",
        "Audit server security logs and find anomalies" to "Security Audit",
        "How does RAG vector search work?" to "RAG Vector"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(JarvisDarkBackground)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JarvisDarkSurface)
                .border(1.dp, JarvisDarkBorder, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = JarvisCyan
                    )
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "JARVIS VOICE AGENT",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = JarvisTextPrimary
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(JarvisCyanGlow)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "BILINGUAL",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = JarvisCyan
                                )
                            )
                        }
                    }

                    Text(
                        text = "Powered by $selectedModelId • Realtime TTS & STT",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = JarvisTextSecondary
                    )
                }
            }

            // Language Switcher & Status Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (languageCode == "bn-BD") JarvisCyan.copy(alpha = 0.2f) else JarvisDarkCard)
                        .border(1.dp, if (languageCode == "bn-BD") JarvisCyan else JarvisDarkBorder, RoundedCornerShape(12.dp))
                        .clickable {
                            val newLang = if (languageCode == "bn-BD") "en-US" else "bn-BD"
                            viewModel.setVoiceLanguage(newLang)
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (languageCode == "bn-BD") "বাংলা" else "EN",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = if (languageCode == "bn-BD") JarvisCyan else JarvisTextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (voiceState) {
                                VoiceAgentState.LISTENING -> JarvisRuby.copy(alpha = 0.2f)
                                VoiceAgentState.THINKING -> JarvisAmber.copy(alpha = 0.2f)
                                VoiceAgentState.SPEAKING -> JarvisEmerald.copy(alpha = 0.2f)
                                else -> JarvisDarkCard
                            }
                        )
                        .border(
                            1.dp,
                            when (voiceState) {
                                VoiceAgentState.LISTENING -> JarvisRuby
                                VoiceAgentState.THINKING -> JarvisAmber
                                VoiceAgentState.SPEAKING -> JarvisEmerald
                                else -> JarvisDarkBorder
                            },
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = voiceState.name,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            color = when (voiceState) {
                                VoiceAgentState.LISTENING -> JarvisRuby
                                VoiceAgentState.THINKING -> JarvisAmber
                                VoiceAgentState.SPEAKING -> JarvisEmerald
                                else -> JarvisTextMuted
                            }
                        )
                    )
                }
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Holographic Soundwave Orb Section
            item {
                VoiceHologramVisualizer(
                    voiceState = voiceState,
                    audioLevel = audioLevel,
                    personality = personality
                )
            }

            // Big Interactive Push-to-Talk Button
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BigVoiceMicButton(
                        voiceState = voiceState,
                        audioLevel = audioLevel,
                        onClick = {
                            if (voiceState == VoiceAgentState.LISTENING) {
                                viewModel.stopVoiceListening()
                            } else if (voiceState == VoiceAgentState.SPEAKING) {
                                viewModel.stopSpeaking()
                            } else {
                                if (hasRecordPermission) {
                                    viewModel.startVoiceListening()
                                } else {
                                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = when (voiceState) {
                            VoiceAgentState.LISTENING -> "Listening to your voice... (কথা বলুন)"
                            VoiceAgentState.THINKING -> "Synthesizing answer with Gemini..."
                            VoiceAgentState.SPEAKING -> "JARVIS Speaking • Tap to stop audio"
                            VoiceAgentState.ERROR -> "Voice engine issue. Tap mic to retry."
                            VoiceAgentState.IDLE -> "Tap microphone to speak (বাংলা বা ইংরেজিতে কথা বলুন)"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.5.sp
                        ),
                        color = when (voiceState) {
                            VoiceAgentState.LISTENING -> JarvisRuby
                            VoiceAgentState.THINKING -> JarvisAmber
                            VoiceAgentState.SPEAKING -> JarvisEmerald
                            else -> JarvisTextSecondary
                        }
                    )
                }
            }

            // Live Partial Speech Card
            if (partialText.isNotBlank() || voiceState == VoiceAgentState.LISTENING) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(JarvisDarkSurface)
                            .border(1.dp, JarvisCyan.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = JarvisCyan,
                                strokeWidth = 2.dp
                            )
                            Text(
                                text = if (partialText.isNotBlank()) "\"$partialText\"" else "Listening...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    fontSize = 13.sp
                                ),
                                color = JarvisCyan
                            )
                        }
                    }
                }
            }

            // Voice Persona Selector Chips
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Persona",
                                tint = JarvisCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "VOICE PERSONALITY MATRIX",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = JarvisCyan
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Auto-Speak",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = JarvisTextSecondary
                            )
                            Switch(
                                checked = isAutoSpeak,
                                onCheckedChange = { viewModel.toggleAutoSpeak() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = JarvisCyan,
                                    checkedTrackColor = JarvisDarkCard,
                                    uncheckedThumbColor = JarvisTextMuted,
                                    uncheckedTrackColor = JarvisDarkSurface
                                ),
                                modifier = Modifier.scale(0.7f)
                            )
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(VoicePersonality.values()) { p ->
                            val isSelected = p == personality
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) JarvisCyan.copy(alpha = 0.15f) else JarvisDarkCard)
                                    .border(
                                        1.dp,
                                        if (isSelected) JarvisCyan else JarvisDarkBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { viewModel.setVoicePersonality(p) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Column {
                                    Text(
                                        text = p.title,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        ),
                                        color = if (isSelected) JarvisCyan else JarvisTextPrimary
                                    )
                                    Text(
                                        text = p.subtitle,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                        color = JarvisTextMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Voice Command Chips
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "QUICK VOICE PROMPTS",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        ),
                        color = JarvisElectricBlue
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(quickVoiceCommands) { (prompt, label) ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(JarvisDarkSurface)
                                    .border(1.dp, JarvisDarkBorder, RoundedCornerShape(16.dp))
                                    .clickable { viewModel.sendVoicePrompt(prompt) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "Prompt",
                                        tint = JarvisCyan,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                        color = JarvisTextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Conversation Log Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VOICE CONVERSATION HISTORY",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = JarvisCyan
                    )

                    if (messages.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.clearVoiceHistory() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Clear",
                                tint = JarvisTextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Voice Conversation Stream
            items(messages) { msg ->
                VoiceMessageBubble(
                    message = msg,
                    onSpeakAgain = { viewModel.speakText(msg.text) }
                )
            }
        }

        // Bottom Text Input Bar Fallback
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JarvisDarkSurface)
                .border(1.dp, JarvisDarkBorder, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = {
                    Text(
                        "Type or speak directive (বাংলা বা English)...",
                        style = MaterialTheme.typography.bodySmall,
                        color = JarvisTextMuted
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("voice_text_fallback_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = JarvisDarkCard,
                    unfocusedContainerColor = JarvisDarkCard,
                    focusedBorderColor = JarvisCyan,
                    unfocusedBorderColor = JarvisDarkBorder,
                    focusedTextColor = JarvisTextPrimary,
                    unfocusedTextColor = JarvisTextPrimary
                ),
                maxLines = 2
            )

            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        val query = textInput
                        textInput = ""
                        viewModel.sendVoicePrompt(query)
                    }
                },
                enabled = textInput.isNotBlank(),
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (textInput.isNotBlank()) JarvisCyan else JarvisDarkCard)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = if (textInput.isNotBlank()) JarvisDarkBackground else JarvisTextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun VoiceHologramVisualizer(
    voiceState: VoiceAgentState,
    audioLevel: Float,
    personality: VoicePersonality,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "hologram")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val wavePulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wavePulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(
                    listOf(JarvisDarkSurface, JarvisDarkCard.copy(alpha = 0.8f))
                )
            )
            .border(1.5.dp, JarvisCyan.copy(alpha = 0.3f), RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Hologram Canvas Drawing
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val baseRadius = size.height * 0.28f
            val dynamicRadius = baseRadius * (if (voiceState == VoiceAgentState.LISTENING || voiceState == VoiceAgentState.SPEAKING) 1f + (audioLevel * 0.35f) else wavePulse)

            // Outer subtle glow circle
            drawCircle(
                color = when (voiceState) {
                    VoiceAgentState.LISTENING -> JarvisRuby.copy(alpha = 0.15f)
                    VoiceAgentState.THINKING -> JarvisAmber.copy(alpha = 0.15f)
                    VoiceAgentState.SPEAKING -> JarvisEmerald.copy(alpha = 0.15f)
                    else -> JarvisCyan.copy(alpha = 0.1f)
                },
                radius = dynamicRadius * 1.5f,
                center = center
            )

            // Outer dotted ring
            drawCircle(
                color = JarvisCyan.copy(alpha = 0.4f),
                radius = dynamicRadius * 1.25f,
                center = center,
                style = Stroke(width = 1.5f)
            )

            // Inner primary ring
            drawCircle(
                color = when (voiceState) {
                    VoiceAgentState.LISTENING -> JarvisRuby
                    VoiceAgentState.THINKING -> JarvisAmber
                    VoiceAgentState.SPEAKING -> JarvisEmerald
                    else -> JarvisCyan
                },
                radius = dynamicRadius,
                center = center,
                style = Stroke(width = 2.5f)
            )

            // Rotating HUD orbital nodes (8 orbital dots)
            val numNodes = 8
            for (i in 0 until numNodes) {
                val angleDeg = rotationAngle + (i * (360f / numNodes))
                val angleRad = Math.toRadians(angleDeg.toDouble())
                val orbitRadius = dynamicRadius * 1.25f
                val nodeX = center.x + (orbitRadius * cos(angleRad)).toFloat()
                val nodeY = center.y + (orbitRadius * sin(angleRad)).toFloat()

                drawCircle(
                    color = if (i % 2 == 0) JarvisCyan else JarvisElectricBlue,
                    radius = 3.5f,
                    center = Offset(nodeX, nodeY)
                )
            }

            // Audio Equalizer Waveform Bars (16 vertical bars at bottom of orb)
            val numBars = 16
            val barWidth = 4.dp.toPx()
            val spacing = 3.dp.toPx()
            val totalWidth = (numBars * barWidth) + ((numBars - 1) * spacing)
            val startX = center.x - (totalWidth / 2)
            val bottomY = size.height - 18.dp.toPx()

            for (i in 0 until numBars) {
                val barFactor = if (voiceState == VoiceAgentState.LISTENING || voiceState == VoiceAgentState.SPEAKING) {
                    val sineVal = sin((rotationAngle * 0.1f) + (i * 0.4f)).coerceAtLeast(0.1f)
                    (audioLevel * 40.dp.toPx() * sineVal).coerceIn(4.dp.toPx(), 45.dp.toPx())
                } else {
                    (8.dp.toPx() + (sin((rotationAngle * 0.05f) + i) * 4.dp.toPx())).coerceIn(4.dp.toPx(), 45.dp.toPx())
                }

                val x = startX + (i * (barWidth + spacing))
                drawLine(
                    color = when (voiceState) {
                        VoiceAgentState.LISTENING -> JarvisRuby
                        VoiceAgentState.THINKING -> JarvisAmber
                        VoiceAgentState.SPEAKING -> JarvisEmerald
                        else -> JarvisCyan
                    },
                    start = Offset(x, bottomY),
                    end = Offset(x, bottomY - barFactor),
                    strokeWidth = barWidth
                )
            }
        }

        // Center Icon inside orb
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = when (voiceState) {
                    VoiceAgentState.LISTENING -> Icons.Default.Mic
                    VoiceAgentState.THINKING -> Icons.Default.Psychology
                    VoiceAgentState.SPEAKING -> Icons.Default.RecordVoiceOver
                    else -> Icons.Default.GraphicEq
                },
                contentDescription = "Voice State Icon",
                tint = when (voiceState) {
                    VoiceAgentState.LISTENING -> JarvisRuby
                    VoiceAgentState.THINKING -> JarvisAmber
                    VoiceAgentState.SPEAKING -> JarvisEmerald
                    else -> JarvisCyan
                },
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = personality.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp
                ),
                color = JarvisTextPrimary
            )
        }
    }
}

@Composable
fun BigVoiceMicButton(
    voiceState: VoiceAgentState,
    audioLevel: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "micPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "micScale"
    )

    Box(
        modifier = modifier
            .size(80.dp)
            .scale(if (voiceState == VoiceAgentState.LISTENING) pulseScale else 1f)
            .clip(CircleShape)
            .background(
                when (voiceState) {
                    VoiceAgentState.LISTENING -> Brush.radialGradient(listOf(JarvisRuby, JarvisRuby.copy(alpha = 0.7f)))
                    VoiceAgentState.THINKING -> Brush.radialGradient(listOf(JarvisAmber, JarvisAmber.copy(alpha = 0.7f)))
                    VoiceAgentState.SPEAKING -> Brush.radialGradient(listOf(JarvisEmerald, JarvisEmerald.copy(alpha = 0.7f)))
                    else -> Brush.radialGradient(listOf(JarvisCyan, JarvisCyan.copy(alpha = 0.75f)))
                }
            )
            .border(
                3.dp,
                when (voiceState) {
                    VoiceAgentState.LISTENING -> JarvisRuby
                    VoiceAgentState.THINKING -> JarvisAmber
                    VoiceAgentState.SPEAKING -> JarvisEmerald
                    else -> JarvisCyan
                },
                CircleShape
            )
            .clickable { onClick() }
            .testTag("big_voice_mic_button"),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = when (voiceState) {
                VoiceAgentState.LISTENING -> Icons.Default.Mic
                VoiceAgentState.SPEAKING -> Icons.Default.Stop
                VoiceAgentState.THINKING -> Icons.Default.Psychology
                else -> Icons.Default.Mic
            },
            contentDescription = "Microphone Button",
            tint = JarvisDarkBackground,
            modifier = Modifier.size(38.dp)
        )
    }
}

@Composable
fun VoiceMessageBubble(
    message: VoiceMessage,
    onSpeakAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isUser = message.sender == VoiceSender.USER

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (!isUser) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(JarvisCyanGlow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "JARVIS",
                        tint = JarvisCyan,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Text(
                text = if (isUser) "YOU (VOICE)" else "JARVIS",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp
                ),
                color = if (isUser) JarvisElectricBlue else JarvisCyan
            )

            if (!isUser && message.latencyMs > 0) {
                Text(
                    text = "• ${message.latencyMs}ms",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    color = JarvisTextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .clip(
                    RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 14.dp,
                        bottomStart = if (isUser) 14.dp else 2.dp,
                        bottomEnd = if (isUser) 2.dp else 14.dp
                    )
                )
                .background(if (isUser) JarvisDarkCard else JarvisDarkSurface)
                .border(
                    1.dp,
                    if (isUser) JarvisElectricBlue.copy(alpha = 0.4f) else JarvisDarkBorder,
                    RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 14.dp,
                        bottomStart = if (isUser) 14.dp else 2.dp,
                        bottomEnd = if (isUser) 2.dp else 14.dp
                    )
                )
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 20.sp,
                        fontSize = 13.5.sp
                    ),
                    color = JarvisTextPrimary
                )

                if (!isUser) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(JarvisDarkCard)
                                .clickable { onSpeakAgain() }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Speak again",
                                    tint = JarvisCyan,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "Replay Audio",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = JarvisCyan
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
