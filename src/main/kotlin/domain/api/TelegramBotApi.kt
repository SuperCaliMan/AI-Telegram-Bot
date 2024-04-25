package domain.api

import java.io.File

interface TelegramBotApi {
    suspend fun sendMessage(chatId: Long, message: String): Boolean

    suspend fun getAudioFile(fileId: String): File
}
