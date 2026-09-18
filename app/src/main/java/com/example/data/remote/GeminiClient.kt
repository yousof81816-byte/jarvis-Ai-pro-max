package com.example.data.remote

import com.example.BuildConfig
import com.example.data.model.ApiKeyTestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiClient {
    private const val API_BASE_HOST = "https://generativelanguage.googleapis.com/v1beta/models"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    fun resolveEffectiveApiKey(customApiKey: String?): String {
        if (!customApiKey.isNullOrBlank()) {
            return customApiKey.trim()
        }
        return try {
            val buildConfigKey = BuildConfig.GEMINI_API_KEY
            if (buildConfigKey.isNotBlank() && buildConfigKey != "MY_GEMINI_API_KEY") {
                buildConfigKey
            } else {
                ""
            }
        } catch (e: Throwable) {
            ""
        }
    }

    suspend fun testConnection(
        apiKey: String,
        modelName: String = "gemini-3.5-flash"
    ): ApiKeyTestResult = withContext(Dispatchers.IO) {
        val effectiveKey = resolveEffectiveApiKey(apiKey)
        if (effectiveKey.isBlank()) {
            return@withContext ApiKeyTestResult(
                isSuccess = false,
                message = "API Key is empty. Please enter your Google Gemini API Key from AI Studio.",
                modelTested = modelName
            )
        }

        val startTime = System.currentTimeMillis()
        val targetModel = if (modelName.isNotBlank()) modelName.trim() else "gemini-3.5-flash"
        val endpoint = "$API_BASE_HOST/$targetModel:generateContent?key=$effectiveKey"

        try {
            val rootJson = JSONObject().apply {
                val contentsArray = JSONArray()
                val contentObj = JSONObject().apply {
                    val partsArray = JSONArray()
                    partsArray.put(JSONObject().put("text", "JARVIS System Ping: Confirm API operational status in 1 sentence."))
                    put("parts", partsArray)
                }
                contentsArray.put(contentObj)
                put("contents", contentsArray)

                val genConfig = JSONObject().apply {
                    put("temperature", 0.2)
                    put("maxOutputTokens", 60)
                }
                put("generationConfig", genConfig)
            }

            val requestBody = rootJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .build()

            val response = okHttpClient.newCall(request).execute()
            val latency = System.currentTimeMillis() - startTime
            val bodyString = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                val errorMsg = try {
                    val errJson = JSONObject(bodyString)
                    errJson.optJSONObject("error")?.optString("message") ?: "HTTP ${response.code}: $bodyString"
                } catch (e: Exception) {
                    "HTTP ${response.code}: $bodyString"
                }
                return@withContext ApiKeyTestResult(
                    isSuccess = false,
                    message = "Connection failed: $errorMsg",
                    latencyMs = latency,
                    modelTested = targetModel
                )
            }

            val responseJson = JSONObject(bodyString)
            val candidates = responseJson.optJSONArray("candidates")
            val candidateText = candidates?.optJSONObject(0)
                ?.optJSONObject("content")
                ?.optJSONArray("parts")
                ?.optJSONObject(0)
                ?.optString("text")

            val reply = candidateText?.trim() ?: "Connection verified (200 OK)"
            ApiKeyTestResult(
                isSuccess = true,
                message = "Authenticated successfully! Latency: ${latency}ms.\nResponse: $reply",
                latencyMs = latency,
                modelTested = targetModel
            )
        } catch (e: Exception) {
            val latency = System.currentTimeMillis() - startTime
            ApiKeyTestResult(
                isSuccess = false,
                message = "Network error: ${e.localizedMessage ?: e.message}",
                latencyMs = latency,
                modelTested = targetModel
            )
        }
    }

    suspend fun generateContent(
        prompt: String,
        modelName: String = "gemini-3.5-flash",
        customApiKey: String? = null,
        systemInstruction: String? = null,
        temperature: Float = 0.7f,
        topP: Float = 0.95f,
        topK: Int = 40
    ): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = resolveEffectiveApiKey(customApiKey)

        if (apiKey.isBlank()) {
            return@withContext Result.failure(IllegalStateException("API_KEY_UNSET"))
        }

        val targetModel = if (modelName.isNotBlank()) modelName.trim() else "gemini-3.5-flash"
        val endpoint = "$API_BASE_HOST/$targetModel:generateContent?key=$apiKey"

        try {
            val rootJson = JSONObject().apply {
                val contentsArray = JSONArray()
                val contentObj = JSONObject().apply {
                    val partsArray = JSONArray()
                    partsArray.put(JSONObject().put("text", prompt))
                    put("parts", partsArray)
                }
                contentsArray.put(contentObj)
                put("contents", contentsArray)

                if (!systemInstruction.isNullOrBlank()) {
                    val sysContent = JSONObject().apply {
                        val sysParts = JSONArray()
                        sysParts.put(JSONObject().put("text", systemInstruction))
                        put("parts", sysParts)
                    }
                    put("systemInstruction", sysContent)
                }

                val genConfig = JSONObject().apply {
                    put("temperature", temperature)
                    put("topP", topP)
                    put("topK", topK)
                }
                put("generationConfig", genConfig)
            }

            val requestBody = rootJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .build()

            val response = okHttpClient.newCall(request).execute()
            val bodyString = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                val errorMsg = try {
                    val errJson = JSONObject(bodyString)
                    errJson.optJSONObject("error")?.optString("message") ?: "HTTP ${response.code}: $bodyString"
                } catch (e: Exception) {
                    "HTTP ${response.code}: $bodyString"
                }
                return@withContext Result.failure(Exception(errorMsg))
            }

            val responseJson = JSONObject(bodyString)
            val candidates = responseJson.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text")

            if (text != null && text.isNotBlank()) {
                Result.success(text)
            } else {
                Result.failure(Exception("Empty response received from $targetModel"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
