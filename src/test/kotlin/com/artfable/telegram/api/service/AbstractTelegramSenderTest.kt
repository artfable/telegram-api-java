package com.artfable.telegram.api.service

import com.artfable.telegram.api.*
import com.artfable.telegram.api.request.SendMessageRequest
import com.artfable.telegram.api.request.TelegramRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

/**
 * @author aveselov
 * @since 08/08/2020
 */
@ExtendWith(MockitoExtension::class)
internal class AbstractTelegramSenderTest {

    @Test
    fun executeMethod() {
        val expectedMessage = Message(24L, date = 12, chat = Chat(12L, ChatType.CHANNEL))
        val request = SendMessageRequest("22", "text")
        val telegramSender = createTelegramSender({ TelegramResponse(true, null, null, expectedMessage) }, request)

        assertEquals(expectedMessage, telegramSender.executeMethod(request))
    }

    @Test
    fun executeMethod_okFalse() {
        val request = SendMessageRequest("22", "text")
        val errorText = "Bad Request"
        val telegramSender = createTelegramSender({ TelegramResponse(false, 400, errorText, null) }, request)

        val exception = assertThrows<TelegramRequestException> {
            telegramSender.executeMethod(request)
        }

        assertEquals(400, exception.statusCode)
        assertEquals(errorText, exception.message)
    }

    @Test
    fun singleExecuteMethod() {
        val updateId = 5L
        val expectedMessage = Message(24L, date = 12, chat = Chat(12L, ChatType.CHANNEL))
        val request = SendMessageRequest("22", "text")
        val telegramSender = createTelegramSender({ TelegramResponse(true, null, null, expectedMessage) }, request)

        val message = telegramSender.singleExecuteMethod<Message>(updateId, request)

        assertEquals(expectedMessage, message)
        assertNull(telegramSender.singleExecuteMethod(updateId, request))
    }

    private fun createTelegramSender(
        responseFunction: () -> TelegramResponse<out Any?>,
        expectedRequest: TelegramRequest<*>
    ): TelegramSender {
        return object : AbstractTelegramSender() {
            override fun <T> send(telegramRequest: TelegramRequest<T>?): TelegramResponse<T> {
                assertEquals(expectedRequest, telegramRequest)
                return responseFunction.invoke() as TelegramResponse<T>
            }
        }
    }
}