package org.artfable.telegram.api

import org.artfable.telegram.api.request.GetUpdatesRequest
import org.artfable.telegram.api.service.TelegramSender
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.task.TaskExecutor
import org.springframework.test.util.ReflectionTestUtils

/**
 * @author aveselov
 * @since 08/08/2020
 */
@ExtendWith(MockitoExtension::class)
internal class LongPollingTelegramBotTest {
    @Mock
    private lateinit var taskExecutor: TaskExecutor

    @Mock
    private lateinit var telegramSender: TelegramSender

    @Mock
    private lateinit var behaviour: Behaviour

    @Mock
    private lateinit var behaviour2: Behaviour

    @Test
    fun subscribeToUpdates() {
        val longPollingTelegramBot = createBot()
        val updates = listOf(Update(1L))

        given(telegramSender.executeMethod<List<Update>>(GetUpdatesRequest(timeout = 100))).willReturn(updates)
        given(behaviour.isSubscribed).willReturn(true)
        given(behaviour2.isSubscribed).willReturn(true)

        ReflectionTestUtils.invokeMethod<Void>(longPollingTelegramBot, "subscribeToUpdates", null)

        verify(behaviour).parse(updates)
        verify(behaviour2).parse(updates)
        verify(taskExecutor).execute(any())
    }

    @Test
    fun subscribeToUpdates_notSubscribe() {
        val longPollingTelegramBot = createBot()
        val updates = listOf(Update(1L))

        given(telegramSender.executeMethod<List<Update>>(GetUpdatesRequest(timeout = 100))).willReturn(updates)
        given(behaviour.isSubscribed).willReturn(false)
        given(behaviour2.isSubscribed).willReturn(true)

        ReflectionTestUtils.invokeMethod<Void>(longPollingTelegramBot, "subscribeToUpdates", null)

        verify(behaviour, never()).parse(updates)
        verify(behaviour2).parse(updates)
        verify(taskExecutor).execute(any())
    }

    @Test
    fun subscribeToUpdates_skipFailed() {
        val longPollingTelegramBot = createBot()
        val updates = listOf(Update(1L))

        given(telegramSender.executeMethod<List<Update>>(GetUpdatesRequest(timeout = 100))).willReturn(updates)
        given(behaviour.parse(updates)).willThrow(IllegalArgumentException::class.java)
        given(behaviour.isSubscribed).willReturn(true)
        given(behaviour2.isSubscribed).willReturn(true)

        ReflectionTestUtils.invokeMethod<Void>(longPollingTelegramBot, "subscribeToUpdates", null)

        verify(behaviour2).parse(updates)
        verify(taskExecutor).execute(any())
    }

    @Test
    fun subscribeToUpdates_doNotSkipFailed() {
        val longPollingTelegramBot = createBot(false)
        val updates = listOf(Update(1L))

        given(telegramSender.executeMethod<List<Update>>(GetUpdatesRequest(timeout = 100))).willReturn(updates)
        given(behaviour.parse(updates)).willThrow(IllegalArgumentException::class.java)
        given(behaviour.isSubscribed).willReturn(true)
        given(behaviour2.isSubscribed).willReturn(true)

        assertThrows<IllegalArgumentException> {
            ReflectionTestUtils.invokeMethod<Void>(longPollingTelegramBot, "subscribeToUpdates", null)
        }

        verify(behaviour2).parse(updates)
        verify(taskExecutor, never()).execute(any())
    }

    private fun createBot(skipFailed: Boolean? = null): LongPollingTelegramBot {
        val longPollingTelegramBot = if (skipFailed == null) {
            object : LongPollingTelegramBot("token", setOf(behaviour, behaviour2), setOf()) {}
        } else {
            object : LongPollingTelegramBot("token", setOf(behaviour, behaviour2), setOf(), skipFailed) {}
        }
        ReflectionTestUtils.setField(longPollingTelegramBot, "taskExecutor", taskExecutor)
        ReflectionTestUtils.setField(longPollingTelegramBot, "telegramSender", telegramSender)

        return longPollingTelegramBot
    }
}