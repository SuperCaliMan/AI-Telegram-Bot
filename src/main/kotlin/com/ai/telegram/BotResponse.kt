package com.ai.telegram

import kotlinx.serialization.Serializable

@Serializable
open class BotResponse

data class Success(val message: String, val input:String) : BotResponse()

data class ErrorResponse(val message: String, val exception:Exception? = null): BotResponse()

