package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncomingMessage(
    @SerialName("update_id") val updateId: Long,
    @SerialName("message") val message: Message
)

@Serializable
data class Message(
    @SerialName("message_id") val messageId: Long,
    val from: User,
    val chat: Chat,
    val voice: Voice? = null,
    val date: Long,
    val text: String? = null
)

@Serializable
data class User(
    val id: Long,
    @SerialName("is_bot") val isBot: Boolean,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("username") val username: String,
    @SerialName("language_code") val languageCode: String
)

@Serializable
data class Chat(
    @SerialName("id") val id: Long,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("username") val username: String,
    @SerialName("type") val type: String
)

@Serializable
data class Voice(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    @SerialName("duration") val duration: Int,
    @SerialName("performer") val performer: String?= null,
    @SerialName("title") val title: String?= null,
    @SerialName("file_name") val filename: String?= null,
    @SerialName("mime_type") val mimeType: String? = null,
    @SerialName("file_size") val fileSize: Int? = null
)