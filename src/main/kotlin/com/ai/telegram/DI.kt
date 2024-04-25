package com.ai.telegram

import data.TelegramResponseParser
import data.api.TelegramBotApiImpl
import data.repository.DeepgramTranscription
import data.repository.OpenAiTranscription
import data.repository.TelegramBotRepositoryImpl
import domain.repository.TelegramBotRepository
import domain.repository.TranscriptionRepository

object DI {

    private val telegramBotToken by lazy {  System.getenv("TELEGRAM_TOKEN") }
    private val openAiToken by lazy { System.getenv("OPEN_AI_TOKEN") }
    private val deepgramToken by lazy { System.getenv("DEEPGRAM_TOKEN") }

    private val telegramBotApi by lazy { TelegramBotApiImpl(telegramBotToken) }

    private val transcriptionRepository: TranscriptionRepository by lazy { DeepgramTranscription(deepgramToken) }

    val telegramResponseParser by lazy { TelegramResponseParser() }

    val telegramBotRepository:TelegramBotRepository by lazy { TelegramBotRepositoryImpl(telegramBotApi, transcriptionRepository) }

}