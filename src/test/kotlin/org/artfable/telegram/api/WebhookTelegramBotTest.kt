package org.artfable.telegram.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
    private lateinit var behavior: Behavior

    @Mock
    private lateinit var behavior2: Behavior

    @Test
    fun getUpdate() {
        val webhookTelegramBot = createBot()
        val update = Update(1L)

        given(behavior.isSubscribed).willReturn(true)
        given(behavior2.isSubscribed).willReturn(true)

        val responseEntity: ResponseEntity<out Any?>? = ReflectionTestUtils.invokeMethod(webhookTelegramBot, "getUpdate", update)

        verify(behavior).parse(eq(listOf(update)))
        verify(behavior2).parse(eq(listOf(update)))

        assertEquals(ResponseEntity.ok().build<Any?>(), responseEntity)
    }

    @Test
    fun getUpdate_notSubscribed() {
        val webhookTelegramBot = createBot()
        val update = Update(1L)

        given(behavior.isSubscribed).willReturn(false)
        given(behavior2.isSubscribed).willReturn(true)

        val responseEntity: ResponseEntity<out Any?>? = ReflectionTestUtils.invokeMethod(webhookTelegramBot, "getUpdate", update)

        verify(behavior, never()).parse(eq(listOf(update)))
        verify(behavior2).parse(eq(listOf(update)))

        assertEquals(ResponseEntity.ok().build<Any?>(), responseEntity)
    }

    @Test
    fun getUpdate_skipFailed() {
        val webhookTelegramBot = createBot()
        val update = Update(1L)

        given(behavior.isSubscribed).willReturn(true)
        given(behavior2.isSubscribed).willReturn(true)
        given(behavior.parse(eq(listOf(update)))).willThrow(IllegalArgumentException::class.java)

        val responseEntity: ResponseEntity<out Any?>? = ReflectionTestUtils.invokeMethod(webhookTelegramBot, "getUpdate", update)

        verify(behavior2).parse(eq(listOf(update)))

        assertEquals(ResponseEntity.ok().build<Any?>(), responseEntity)
    }

    @Test
    fun getUpdate_doNotSkipFailed() {
        val webhookTelegramBot = createBot(false)
        val update = Update(1L)

        given(behavior.isSubscribed).willReturn(true)
        given(behavior2.isSubscribed).willReturn(true)
        given(behavior.parse(eq(listOf(update)))).willThrow(IllegalArgumentException::class.java)

        val responseEntity: ResponseEntity<out Any?>? = ReflectionTestUtils.invokeMethod(webhookTelegramBot, "getUpdate", update)

        verify(behavior2).parse(eq(listOf(update)))

        assertEquals(ResponseEntity.badRequest().build<Any?>(), responseEntity)
    }

    private fun createBot(skipFailed: Boolean? = null): WebhookTelegramBot {
        return if (skipFailed == null) {
            object : WebhookTelegramBot("token", "url", setOf(behavior, behavior2)) {}
        } else {
            object : WebhookTelegramBot("token", "url", null, setOf(behavior, behavior2), skipFailed) {}
        }
    }
}