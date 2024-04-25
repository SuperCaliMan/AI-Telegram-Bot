package data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    @SerialName("transaction_key")val transactionKey: String = "deprecated", // optional with default value
    @SerialName("request_id") val requestId: String,
    val sha256: String,
    val created: String,
    val duration: Double,
    val channels: Int,
    val models: List<String>,
)

@Serializable
data class Word(
    val word: String,
    val start: Double,
    val end: Double,
    val confidence: Double,
    @SerialName("punctuated_word") val punctuatedWord: String
)

@Serializable
data class Alternative(
    val transcript: String,
    val confidence: Double,
    val words: List<Word>
)

@Serializable
data class Channel(
    val alternatives: List<Alternative>
)

@Serializable
data class Results(
    val channels: List<Channel>
)

@Serializable
data class RecognitionResponse(
    val metadata: Metadata,
    val results: Results
)