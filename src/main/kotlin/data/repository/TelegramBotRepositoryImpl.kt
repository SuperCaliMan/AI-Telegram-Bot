package data.repository

import data.api.TelegramBotApiImpl
import domain.models.IncomingMessage
import domain.repository.TelegramBotRepository
import domain.repository.TranscriptionRepository
import kotlinx.coroutines.*



class TelegramBotRepositoryImpl(private val telegramBotApiImpl: TelegramBotApiImpl, private val transcriptionRepository: TranscriptionRepository) : TelegramBotRepository {


    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun processIncomingMessage(incomingMessage: IncomingMessage): Boolean = runBlocking {
        return@runBlocking withTimeout(TIMEOUT) {
            scope.async {
                if (!incomingMessage.message.from.isBot) {
                    if (incomingMessage.message.text != null) {
                        with(incomingMessage.message.text) {
                            telegramBotApiImpl.sendMessage(
                                incomingMessage.message.chat.id,
                                "Bot say: $this"
                            )
                        }
                    } else if (incomingMessage.message.voice != null) {
                        val audioFile = telegramBotApiImpl.getAudioFile(incomingMessage.message.voice.fileId)
                        val transcription = transcriptionRepository.getTranscription(audioFile).getOrThrow()
                        telegramBotApiImpl.sendMessage(
                            incomingMessage.message.chat.id,
                            transcription
                        )
                    } else {
                        false
                    }
                } else false
            }.await()
        }
    }

    private companion object {
        const val TIMEOUT = 5000L
    }
}

