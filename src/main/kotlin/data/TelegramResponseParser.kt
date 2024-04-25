package data
import domain.models.IncomingMessage
import kotlinx.serialization.json.Json


class TelegramResponseParser {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }


    fun parseIncomingMessage(input: Map<String,Any>):IncomingMessage {
        val body = input.entries.first { it.key == "body" }.value as String
        val message = json.decodeFromString<IncomingMessage>(body)
        return message
    }
}

