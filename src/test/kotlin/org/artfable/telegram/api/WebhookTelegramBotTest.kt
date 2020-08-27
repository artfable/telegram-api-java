package org.artfable.telegram.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity
import org.springframework.test.util.ReflectionTestUtils

/**
 * @author aveselov
 * @since 08/08/2020
 */
@ExtendWith(MockitoExtension::class)
class WebhookTelegramBotTest {
    @Mock
    private lateinit var behaviour: Behaviour

    @Mock
    private lateinit var behaviour2: Behaviour

    @Test
    fun getUpdate() {
        val webhookTelegramBot = createBot()
        val update = Update(1L)

        val responseEntity: ResponseEntity<out Any?>? = ReflectionTestUtils.invokeMethod(webhookTelegramBot, "getUpdate", update)

        verify(behaviour).parse(eq(listOf(update)))
        verify(behaviour2).parse(eq(listOf(update)))

        assertEquals(ResponseEntity.ok().build<Any?>(), responseEntity)
    }

    @Test
    fun getUpdate_skipFailed() {
        val webhookTelegramBot = createBot()
        val update = Update(1L)

        given(behaviour.parse(eq(listOf(update)))).willThrow(IllegalArgumentException::class.java)

        val responseEntity: ResponseEntity<out Any?>? = ReflectionTestUtils.invokeMethod(webhookTelegramBot, "getUpdate", update)

        verify(behaviour2).parse(eq(listOf(update)))

        assertEquals(ResponseEntity.ok().build<Any?>(), responseEntity)
    }

    @Test
    fun getUpdate_doNotSkipFailed() {
        val webhookTelegramBot = createBot(false)
        val update = Update(1L)

        given(behaviour.parse(eq(listOf(update)))).willThrow(RuntimeException::class.java)

        assertThrows<IllegalArgumentException> {
            ReflectionTestUtils.invokeMethod(webhookTelegramBot, "getUpdate", update)
        }

        verify(behaviour2).parse(eq(listOf(update)))
    }

    private fun createBot(skipFailed: Boolean? = null): WebhookTelegramBot {
        return if (skipFailed == null) {
            object : WebhookTelegramBot("url", setOf(behaviour, behaviour2), setOf()) {}
        } else {
            object : WebhookTelegramBot( "url", null, setOf(behaviour, behaviour2), setOf(), skipFailed) {}
        }
    }
}