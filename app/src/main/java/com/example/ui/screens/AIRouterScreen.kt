package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AltRoute
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.NetworkPing
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AIRouteRule
import com.example.data.model.GeminiModelCatalog
import com.example.data.model.GeminiModelOption
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
import com.example.ui.theme.RouterColor
import com.example.ui.viewmodel.JarvisViewModel

@Composable
fun AIRouterScreen(
    viewModel: JarvisViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val routeRules by viewModel.routeRules.collectAsState()
    val customApiKey by viewModel.customGeminiApiKey.collectAsState()
    val selectedModelId by viewModel.selectedGeminiModelId.collectAsState()
    val customModelName by viewModel.customModelInput.collectAsState()
    val isTestingKey by viewModel.isTestingApiKey.collectAsState()
    val testResult by viewModel.apiKeyTestResult.collectAsState()

    val temp by viewModel.geminiTemperature.collectAsState()
    val topP by viewModel.geminiTopP.collectAsState()
    val topK by viewModel.geminiTopK.collectAsState()
    val sysInstruction by viewModel.geminiSystemInstruction.collectAsState()

    var apiKeyInput by remember(customApiKey) { mutableStateOf(customApiKey) }
    var isApiKeyVisible by remember { mutableStateOf(false) }
    var isSavedNotificationVisible by remember { mutableStateOf(false) }
    var customModelDraft by remember(customModelName) { mutableStateOf(customModelName) }

    var localTemp by remember(temp) { mutableStateOf(temp) }
    var localTopP by remember(topP) { mutableStateOf(topP) }
    var localTopK by remember(topK) { mutableStateOf(topK) }
    var localSysInstruction by remember(sysInstruction) { mutableStateOf(sysInstruction) }

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
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = JarvisTextPrimary
                )
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(RouterColor.copy(alpha = 0.2f))
                    .border(1.dp, RouterColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.AltRoute,
                    contentDescription = "AI Router Subsystem",
                    tint = RouterColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = "AI ROUTER & MODEL VAULT",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = JarvisTextPrimary
                )
                Text(
                    text = "Gemini API Keys • All Model Versions • Routing Policies",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = RouterColor
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: GEMINI API KEY MANAGEMENT VAULT
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(JarvisDarkSurface)
                        .border(1.5.dp, JarvisCyan.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = "API Key Vault",
                                tint = JarvisCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "GEMINI API KEY VAULT",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = JarvisCyan
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (customApiKey.isNotBlank()) JarvisEmerald.copy(alpha = 0.2f) else JarvisAmber.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (customApiKey.isNotBlank()) "CUSTOM KEY ACTIVE" else "DEFAULT CREDENTIALS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.5.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = if (customApiKey.isNotBlank()) JarvisEmerald else JarvisAmber
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Enter your Google Gemini API Key from Google AI Studio. Your key will be securely saved locally in encrypted application storage.",
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
                        color = JarvisTextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // API Key Text Field with Eye toggle
                    OutlinedTextField(
                        value = apiKeyInput,
                        onValueChange = {
                            apiKeyInput = it
                            isSavedNotificationVisible = false
                        },
                        placeholder = {
                            Text(
                                "AIzaSy...",
                                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                                color = JarvisTextMuted
                            )
                        },
                        label = { Text("Google Gemini API Key", style = MaterialTheme.typography.labelSmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("gemini_api_key_input"),
                        shape = RoundedCornerShape(10.dp),
                        visualTransformation = if (isApiKeyVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isApiKeyVisible = !isApiKeyVisible }) {
                                Icon(
                                    imageVector = if (isApiKeyVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle API Key Visibility",
                                    tint = JarvisCyan
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = JarvisDarkCard,
                            unfocusedContainerColor = JarvisDarkCard,
                            focusedBorderColor = JarvisCyan,
                            unfocusedBorderColor = JarvisDarkBorder,
                            focusedTextColor = JarvisTextPrimary,
                            unfocusedTextColor = JarvisTextPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Action Buttons: Save, Test, Clear
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                viewModel.saveGeminiApiKey(apiKeyInput)
                                isSavedNotificationVisible = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("save_api_key_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = JarvisCyan),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save",
                                tint = JarvisDarkBackground,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Save Key", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = JarvisDarkBackground)
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.testGeminiApiKey(apiKeyInput, selectedModelId)
                            },
                            enabled = !isTestingKey,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("test_api_key_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = JarvisElectricBlue
                            )
                        ) {
                            if (isTestingKey) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = JarvisCyan,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.NetworkPing,
                                    contentDescription = "Ping",
                                    tint = JarvisElectricBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Test Ping", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                            }
                        }

                        if (customApiKey.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    apiKeyInput = ""
                                    viewModel.clearGeminiApiKey()
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(JarvisDarkCard)
                                    .border(1.dp, JarvisRuby.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Clear Key",
                                    tint = JarvisRuby,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    // Save success banner
                    AnimatedVisibility(visible = isSavedNotificationVisible) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(JarvisEmerald.copy(alpha = 0.15f))
                                    .border(1.dp, JarvisEmerald, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Saved",
                                        tint = JarvisEmerald,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "API Key saved successfully! Ready for autonomous agent operations.",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = JarvisEmerald
                                    )
                                }
                            }
                        }
                    }

                    // Test result banner
                    if (testResult != null) {
                        val res = testResult!!
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (res.isSuccess) JarvisEmerald.copy(alpha = 0.12f) else JarvisRuby.copy(alpha = 0.12f))
                                .border(1.dp, if (res.isSuccess) JarvisEmerald else JarvisRuby, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Icon(
                                            imageVector = if (res.isSuccess) Icons.Default.CheckCircle else Icons.Default.Warning,
                                            contentDescription = "Status",
                                            tint = if (res.isSuccess) JarvisEmerald else JarvisRuby,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = if (res.isSuccess) "CONNECTION VERIFIED" else "CONNECTION FAILED",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            ),
                                            color = if (res.isSuccess) JarvisEmerald else JarvisRuby
                                        )
                                    }

                                    if (res.latencyMs > 0) {
                                        Text(
                                            text = "${res.latencyMs}ms latency",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 9.sp
                                            ),
                                            color = if (res.isSuccess) JarvisEmerald else JarvisRuby
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = res.message,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = JarvisTextPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Section 2: GEMINI MODEL VERSION SELECTOR
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "GEMINI MODEL VERSIONS SELECTOR",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = JarvisCyan
                        )

                        Text(
                            text = "ACTIVE: ${selectedModelId.uppercase()}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            ),
                            color = JarvisEmerald
                        )
                    }

                    Text(
                        text = "Select from all available Google Gemini model versions to route missions, voice conversations, and code generation.",
                        style = MaterialTheme.typography.bodySmall,
                        color = JarvisTextSecondary
                    )
                }
            }

            // Gemini Model Catalog Cards
            items(GeminiModelCatalog.ALL_MODELS) { model ->
                val isSelected = selectedModelId == model.id
                GeminiModelSelectionCard(
                    model = model,
                    isSelected = isSelected,
                    onSelect = { viewModel.setGeminiModel(model.id) }
                )
            }

            // Custom Model Option
            item {
                val isCustomSelected = selectedModelId == "custom"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(JarvisDarkSurface)
                        .border(
                            1.5.dp,
                            if (isCustomSelected) JarvisCyan else JarvisDarkBorder,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Custom Gemini Model ID",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (isCustomSelected) JarvisCyan else JarvisTextPrimary
                                )
                                Text(
                                    text = "Enter any experimental or fine-tuned model identifier",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = JarvisTextSecondary
                                )
                            }

                            Button(
                                onClick = {
                                    viewModel.setGeminiModel("custom")
                                    if (customModelDraft.isNotBlank()) {
                                        viewModel.setCustomModelInput(customModelDraft)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isCustomSelected) JarvisCyan else JarvisDarkCard
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (isCustomSelected) "SELECTED" else "SELECT",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    color = if (isCustomSelected) JarvisDarkBackground else JarvisTextPrimary
                                )
                            }
                        }

                        if (isCustomSelected) {
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = customModelDraft,
                                onValueChange = {
                                    customModelDraft = it
                                    viewModel.setCustomModelInput(it)
                                },
                                placeholder = { Text("e.g. gemini-1.5-pro-latest or custom-endpoint", style = MaterialTheme.typography.bodySmall) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = JarvisDarkCard,
                                    unfocusedContainerColor = JarvisDarkCard,
                                    focusedBorderColor = JarvisCyan,
                                    unfocusedBorderColor = JarvisDarkBorder,
                                    focusedTextColor = JarvisTextPrimary,
                                    unfocusedTextColor = JarvisTextPrimary
                                ),
                                singleLine = true
                            )
                        }
                    }
                }
            }

            // Section 3: MODEL HYPERPARAMETERS & SYSTEM INSTRUCTIONS
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(JarvisDarkSurface)
                        .border(1.dp, JarvisDarkBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Parameters",
                                tint = JarvisCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "MODEL PARAMETERS & PROMPTING",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = JarvisCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Temperature Slider
                    Text(
                        text = "Temperature (Creativity): ${String.format("%.2f", localTemp)}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )
                    Slider(
                        value = localTemp,
                        onValueChange = {
                            localTemp = it
                            viewModel.updateGeminiParams(it, localTopP, localTopK, localSysInstruction)
                        },
                        valueRange = 0.0f..1.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = JarvisCyan,
                            activeTrackColor = JarvisCyan,
                            inactiveTrackColor = JarvisDarkCard
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Top-P Slider
                    Text(
                        text = "Top-P (Nucleus Sampling): ${String.format("%.2f", localTopP)}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )
                    Slider(
                        value = localTopP,
                        onValueChange = {
                            localTopP = it
                            viewModel.updateGeminiParams(localTemp, it, localTopK, localSysInstruction)
                        },
                        valueRange = 0.0f..1.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = JarvisElectricBlue,
                            activeTrackColor = JarvisElectricBlue,
                            inactiveTrackColor = JarvisDarkCard
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Top-K Slider
                    Text(
                        text = "Top-K: $localTopK",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )
                    Slider(
                        value = localTopK.toFloat(),
                        onValueChange = {
                            localTopK = it.toInt()
                            viewModel.updateGeminiParams(localTemp, localTopP, localTopK, localSysInstruction)
                        },
                        valueRange = 1f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = JarvisAmber,
                            activeTrackColor = JarvisAmber,
                            inactiveTrackColor = JarvisDarkCard
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // System Instruction Text Area
                    Text(
                        text = "Autonomous Agent System Instruction",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = JarvisTextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = localSysInstruction,
                        onValueChange = {
                            localSysInstruction = it
                            viewModel.updateGeminiParams(localTemp, localTopP, localTopK, it)
                        },
                        placeholder = { Text("Set system prompt for autonomous agent...", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = JarvisDarkCard,
                            unfocusedContainerColor = JarvisDarkCard,
                            focusedBorderColor = JarvisCyan,
                            unfocusedBorderColor = JarvisDarkBorder,
                            focusedTextColor = JarvisTextPrimary,
                            unfocusedTextColor = JarvisTextPrimary
                        ),
                        minLines = 3,
                        maxLines = 5
                    )
                }
            }

            // Section 4: Multi-Model Architecture Overview
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(JarvisDarkSurface)
                        .border(1.dp, RouterColor.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "MULTI-PROVIDER DISPATCH MATRIX",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = RouterColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "JARVIS automatically routes mission steps between Gemini, OpenAI, Anthropic Claude, and Local Edge SLMs based on latency SLAs, multimodal vision requirements, and task reasoning depth.",
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
                        color = JarvisTextSecondary
                    )
                }
            }

            items(routeRules) { rule ->
                AIRouteRuleItemCard(
                    rule = rule,
                    onSelectPrimary = { viewModel.setPrimaryRouteModel(rule.modelName) }
                )
            }
        }
    }
}

@Composable
fun GeminiModelSelectionCard(
    model: GeminiModelOption,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) JarvisDarkCard else JarvisDarkSurface)
            .border(
                1.5.dp,
                if (isSelected) JarvisCyan else JarvisDarkBorder,
                RoundedCornerShape(12.dp)
            )
            .clickable { onSelect() }
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = model.displayName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.5.sp
                            ),
                            color = if (isSelected) JarvisCyan else JarvisTextPrimary
                        )

                        if (model.isRecommended) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(JarvisCyanGlow)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "RECOMMENDED",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = JarvisCyan
                                    )
                                )
                            }
                        }
                    }

                    Text(
                        text = "ID: ${model.id} • ${model.category}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.5.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = JarvisTextSecondary
                    )
                }

                // Select Radio / Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) JarvisCyan else JarvisDarkCard)
                        .border(1.dp, if (isSelected) JarvisCyan else JarvisDarkBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = if (isSelected) "ACTIVE" else "SELECT",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.5.sp
                        ),
                        color = if (isSelected) JarvisDarkBackground else JarvisTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = model.description,
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp, fontSize = 11.5.sp),
                color = JarvisTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Capabilities Pill Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ModelPillTag(label = "Context: ${model.contextWindow}", color = JarvisEmerald)
                if (model.isVisionSupported) {
                    ModelPillTag(label = "Vision Multimodal", color = JarvisCyan)
                }
                if (model.isAudioSupported) {
                    ModelPillTag(label = "Voice / Audio", color = JarvisAmber)
                }
            }
        }
    }
}

@Composable
fun ModelPillTag(
    label: String,
    color: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 8.5.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        )
    }
}

@Composable
fun AIRouteRuleItemCard(
    rule: AIRouteRule,
    onSelectPrimary: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(JarvisDarkSurface)
            .border(
                1.5.dp,
                if (rule.isPrimary) RouterColor else JarvisDarkBorder,
                RoundedCornerShape(12.dp)
            )
            .clickable { onSelectPrimary() }
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = rule.modelName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.5.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = if (rule.isPrimary) RouterColor else JarvisTextPrimary
                    )
                    Text(
                        text = rule.provider,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.5.sp,
                            color = JarvisCyan
                        )
                    )
                }

                if (rule.isPrimary) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .background(RouterColor.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                            .border(1.dp, RouterColor, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Primary",
                            tint = RouterColor,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "PRIMARY ROUTE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            ),
                            color = RouterColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Target Workload: " + rule.targetWorkload,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = JarvisTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Telemetry stats row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(JarvisDarkCard, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "LATENCY", style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp), color = JarvisTextMuted)
                    Text(text = "${rule.avgLatencyMs}ms", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = JarvisCyan, fontSize = 10.sp))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "COST/1K", style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp), color = JarvisTextMuted)
                    Text(text = "$${rule.costPer1kTokens}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = JarvisEmerald, fontSize = 10.sp))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "CONTEXT", style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp), color = JarvisTextMuted)
                    Text(text = rule.contextWindow, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = JarvisTextPrimary, fontSize = 10.sp))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "REASONING", style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp), color = JarvisTextMuted)
                    Text(text = "${rule.reasoningScore}/100", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = RouterColor, fontSize = 10.sp))
                }
            }
        }
    }
}
