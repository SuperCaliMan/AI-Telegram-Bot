package domain.repository

import domain.models.IncomingMessage

interface TelegramBotRepository {
    fun processIncomingMessage(incomingMessage: IncomingMessage): Boolean
}