package data.repository

import com.aallam.openai.api.audio.TranscriptionRequest
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.RetryStrategy
import domain.repository.TranscriptionRepository
import okio.source
import java.io.File
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class OpenAiTranscription(private val openAiToken:String): TranscriptionRepository {

    private val openAI by lazy {
        OpenAI(
            token = openAiToken,
            logging = LoggingConfig(LogLevel.All),
            retry = RetryStrategy(maxRetries = 1),
            timeout = Timeout(request = TIMEOUT.toDuration(DurationUnit.MILLISECONDS))
        )
    }


    override suspend fun getTranscription(audio: File): Result<String> = runCatching {
        val transcription = openAI.transcription(
            request = TranscriptionRequest(
                audio = FileSource(
                    audio.name,
                    audio.source()
                ),
                model = ModelId("whisper-1"),
                prompt = "You are an useful ai assistant and translate audio voice message in test, RESPECT AUDIO LANGUAGE",
            )
        )
        transcription.text
    }

    private companion object {
        const val TIMEOUT = 5000L
    }
}