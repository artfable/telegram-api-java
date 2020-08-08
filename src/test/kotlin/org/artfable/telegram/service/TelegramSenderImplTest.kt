package org.artfable.telegram.service

import org.artfable.telegram.api.*
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSenderImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.eq
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.net.URI

/**
 * @author aveselov
 * @since 08/08/2020
 */
@ExtendWith(MockitoExtension::class)
internal class TelegramSenderImplTest {
    @Mock
    private lateinit var restTemplate: RestTemplate

    private lateinit var telegramSender: TelegramSenderImpl

    @BeforeEach
    fun setUp() {
        telegramSender = TelegramSenderImpl(restTemplate, "token")
    }

    @Test
    fun executeMethod() {
        val expectedMessage = Message(24L, date =  12, chat = Chat(12L, ChatType.CHANNEL))
        val request = SendMessageRequest(22L, "text")

        given<ResponseEntity<out TelegramResponse<out Any?>>>(restTemplate.exchange(eq(URI("https://api.telegram.org/bottoken/sendMessage")), eq(HttpMethod.POST), eq(request.asEntity()), eq(request.responseType)))
                .willReturn(ResponseEntity(TelegramResponse(true, null, null, expectedMessage), HttpStatus.OK))

        assertEquals(expectedMessage, telegramSender.executeMethod<Message>(request))
    }

    @Test
    fun executeMethod_okFalse() {
        val request = SendMessageRequest(22L, "text")
        val errorText = "Bad Request"

        given<ResponseEntity<out TelegramResponse<out Any?>>>(restTemplate.exchange(eq(URI("https://api.telegram.org/bottoken/sendMessage")), eq(HttpMethod.POST), eq(request.asEntity()), eq(request.responseType)))
                .willReturn(ResponseEntity(TelegramResponse(false, 400, errorText, null), HttpStatus.OK))

        val exception = assertThrows<TelegramRequestException> {
            telegramSender.executeMethod(request)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals(errorText, exception.message)
    }

    @Test
    fun singleExecuteMethod() {
        val updateId = 5L
        val expectedMessage = Message(24L, date =  12, chat = Chat(12L, ChatType.CHANNEL))
        val request = SendMessageRequest(22L, "text")

        given<ResponseEntity<out TelegramResponse<out Any?>>>(restTemplate.exchange(eq(URI("https://api.telegram.org/bottoken/sendMessage")), eq(HttpMethod.POST), eq(request.asEntity()), eq(request.responseType)))
                .willReturn(ResponseEntity(TelegramResponse(true, null, null, expectedMessage), HttpStatus.OK))

        val message = telegramSender.singleExecuteMethod<Message>(updateId, request)

        assertEquals(expectedMessage, message)
        assertNull(telegramSender.singleExecuteMethod(updateId, request))
    }
}