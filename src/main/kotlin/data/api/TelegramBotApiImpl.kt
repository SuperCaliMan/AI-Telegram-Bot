package data.api

import domain.api.TelegramBotApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

class TelegramBotApiImpl(private val token: String) : TelegramBotApi {

    private val baseUrl = "https://api.telegram.org/bot${token}"
    private val fileBaseUrl = "https://api.telegram.org/file/bot${token}"

    private val ktor = HttpClient {
        defaultRequest {
            contentType(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("\uD83D\uDE80 KTOR $message")
                }
            }
        }
    }

    override suspend fun sendMessage(chatId: Long, message: String): Boolean {
        val res = ktor.post("${baseUrl}/sendMessage") {
            setBody(SendMessage(chatId, message))
        }.bodyAsText()
        return res.isNotEmpty()
    }

    override suspend fun getAudioFile(fileId: String):File {
        val tempFile = File("/tmp/","$fileId.oga")
        getFileInfo(fileId).result.path?.let {
            val out = ktor.get("${fileBaseUrl}/$it").bodyAsChannel()
            out.copyAndClose(tempFile.writeChannel())
            return tempFile
        } ?: throw IllegalArgumentException("path not found")
    }

    private suspend fun getFileInfo(fileId: String): ResponseResult {
        return ktor.get("${baseUrl}/getFile"){
            parameter("file_id", fileId)
        }.body()
    }

    @Serializable
    data class ResponseResult(
        val ok:Boolean,
        val result: FileInfo
    )

    @Serializable
    data class FileInfo(
        @SerialName("file_id") val fileId: String,
        @SerialName("file_unique_id") val uniqueId: String,
        @SerialName("file_size") val fileSize: Int? = null,
        @SerialName("file_path") val path: String? = null
    )

    @Serializable
    data class SendMessage(val chat_id: Long, val text: String)

}