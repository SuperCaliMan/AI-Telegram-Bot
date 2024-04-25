package com.ai.telegram

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class BotHandler : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        return try {
            val incomingMessage = DI.telegramResponseParser.parseIncomingMessage(input)
            val res = DI.telegramBotRepository.processIncomingMessage(incomingMessage)
            ApiGatewayResponse.build {
                statusCode = if (res) 200 else 204
                objectBody = Success("Send message with result $res", "")
                headers = mapOf("X-Powered-By" to "Aws lambda")
            }
        } catch (e: Exception) {
            ApiGatewayResponse.build {
                statusCode = 204
                objectBody = ErrorResponse("Fail lambda", e)
                headers = mapOf("X-Powered-By" to "AWS Lambda & serverless")
            }
        }
    }
}
