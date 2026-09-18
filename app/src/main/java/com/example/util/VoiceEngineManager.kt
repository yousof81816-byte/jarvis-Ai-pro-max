package com.example.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.data.model.VoiceAgentState
import com.example.data.model.VoicePersonality
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class VoiceEngineManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null
    private var isTtsInitialized = false

    private val _voiceState = MutableStateFlow(VoiceAgentState.IDLE)
    val voiceState: StateFlow<VoiceAgentState> = _voiceState.asStateFlow()

    private val _micAudioLevel = MutableStateFlow(0f)
    val micAudioLevel: StateFlow<Float> = _micAudioLevel.asStateFlow()

    private val _recognizedPartialText = MutableStateFlow("")
    val recognizedPartialText: StateFlow<String> = _recognizedPartialText.asStateFlow()

    private var currentPersonality: VoicePersonality = VoicePersonality.JARVIS
    private var speechRecognizer: SpeechRecognizer? = null
    private var activeLanguageCode: String = "en-US"

    init {
        try {
            textToSpeech = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsInitialized = true
            applyPersonality(currentPersonality)
            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _voiceState.value = VoiceAgentState.SPEAKING
                }

                override fun onDone(utteranceId: String?) {
                    _voiceState.value = VoiceAgentState.IDLE
                }

                override fun onError(utteranceId: String?) {
                    _voiceState.value = VoiceAgentState.IDLE
                }
            })
        }
    }

    fun setLanguage(languageCode: String) {
        activeLanguageCode = languageCode
        if (!isTtsInitialized || textToSpeech == null) return

        val locale = when (languageCode) {
            "bn", "bn-BD", "bn_BD" -> Locale("bn", "BD")
            "en-GB", "en_GB" -> Locale.UK
            else -> Locale.US
        }

        try {
            val result = textToSpeech?.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to default US locale if Bengali voice data not locally installed
                textToSpeech?.setLanguage(Locale.US)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun applyPersonality(personality: VoicePersonality) {
        currentPersonality = personality
        if (!isTtsInitialized || textToSpeech == null) return

        try {
            textToSpeech?.setPitch(personality.pitchMultiplier)
            textToSpeech?.setSpeechRate(personality.speechRateMultiplier)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun speak(text: String, utteranceId: String = "JARVIS_REPLY") {
        if (text.isBlank()) return

        if (!isTtsInitialized || textToSpeech == null) {
            _voiceState.value = VoiceAgentState.IDLE
            return
        }

        try {
            // Auto detect if text contains Bengali characters
            val hasBengaliChars = text.any { it in '\u0980'..'\u09FF' }
            if (hasBengaliChars) {
                try {
                    textToSpeech?.setLanguage(Locale("bn", "BD"))
                } catch (e: Exception) {
                    textToSpeech?.setLanguage(Locale.getDefault())
                }
            } else {
                setLanguage(activeLanguageCode)
            }

            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            _voiceState.value = VoiceAgentState.SPEAKING
        } catch (e: Exception) {
            e.printStackTrace()
            _voiceState.value = VoiceAgentState.IDLE
        }
    }

    fun stopSpeaking() {
        try {
            textToSpeech?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (_voiceState.value == VoiceAgentState.SPEAKING) {
            _voiceState.value = VoiceAgentState.IDLE
        }
    }

    fun startSpeechRecognition(
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        stopSpeaking()

        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            onError("Speech recognition not available on this device.")
            return
        }

        try {
            speechRecognizer?.destroy()
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {
                        _voiceState.value = VoiceAgentState.LISTENING
                        _recognizedPartialText.value = ""
                    }

                    override fun onBeginningOfSpeech() {
                        _voiceState.value = VoiceAgentState.LISTENING
                    }

                    override fun onRmsChanged(rmsdB: Float) {
                        // Normalize RMS dB to 0.0 .. 1.0
                        val normalized = ((rmsdB + 2f) / 12f).coerceIn(0.1f, 1.0f)
                        _micAudioLevel.value = normalized
                    }

                    override fun onBufferReceived(buffer: ByteArray?) {}

                    override fun onEndOfSpeech() {
                        _voiceState.value = VoiceAgentState.THINKING
                    }

                    override fun onError(error: Int) {
                        _voiceState.value = VoiceAgentState.IDLE
                        _micAudioLevel.value = 0f
                        val errMsg = when (error) {
                            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                            SpeechRecognizer.ERROR_CLIENT -> "Client error"
                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Audio permission required"
                            SpeechRecognizer.ERROR_NETWORK -> "Network connection error"
                            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                            SpeechRecognizer.ERROR_NO_MATCH -> "No speech detected"
                            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Voice engine busy"
                            SpeechRecognizer.ERROR_SERVER -> "Voice server error"
                            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech detected in time"
                            else -> "Voice recognition issue ($error)"
                        }
                        onError(errMsg)
                    }

                    override fun onResults(results: Bundle?) {
                        _voiceState.value = VoiceAgentState.IDLE
                        _micAudioLevel.value = 0f
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val text = matches?.firstOrNull() ?: ""
                        _recognizedPartialText.value = text
                        if (text.isNotBlank()) {
                            onResult(text)
                        }
                    }

                    override fun onPartialResults(partialResults: Bundle?) {
                        val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val partial = matches?.firstOrNull() ?: ""
                        if (partial.isNotBlank()) {
                            _recognizedPartialText.value = partial
                        }
                    }

                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)

                val reqLang = when (activeLanguageCode) {
                    "bn", "bn-BD", "bn_BD" -> "bn-BD"
                    "en-GB" -> "en-GB"
                    else -> "en-US"
                }
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, reqLang)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, reqLang)
            }

            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            _voiceState.value = VoiceAgentState.IDLE
            onError("Unable to initialize speech recognizer: ${e.message}")
        }
    }

    fun stopSpeechRecognition() {
        try {
            speechRecognizer?.stopListening()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _voiceState.value = VoiceAgentState.IDLE
        _micAudioLevel.value = 0f
    }

    fun shutdown() {
        try {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
            speechRecognizer?.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
