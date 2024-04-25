package data.repository

import data.models.RecognitionResponse
import domain.repository.TranscriptionRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.cio.*
import kotlinx.serialization.json.Json
import java.io.File

class DeepgramTranscription(private val token:String):TranscriptionRepository {
    
    companion object {
        private const val BASE_URL = "api.deepgram.com"
        private const val VERSION = "v1"
    }

    private val ktor by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("DEEPGRAM KTOR $message")
                    }
                }
            }
        }
    }

    override suspend fun getTranscription(audio: File): Result<String> {
        val response: RecognitionResponse = ktor.post("https://$BASE_URL/$VERSION/listen"){
            url {
                parameters.append("model","base")
                parameters.append("punctuate","true")
                parameters.append("language","it")
            }

            
            headers {
                append("Authorization","Token $token")
                append("content-type","audio/ogg")
            }
            setBody(audio.readChannel())
        }.body()


        var mapResponse = ""
        response.results.channels.firstOrNull()?.alternatives?.firstOrNull()?.let {
            mapResponse += it.transcript
            mapResponse += "\n Confidence: ${it.confidence}"
        }
        return Result.success(mapResponse)
    }
}