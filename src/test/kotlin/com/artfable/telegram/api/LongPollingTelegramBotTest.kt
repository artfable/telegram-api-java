package com.artfable.telegram.api

import com.artfable.telegram.api.request.GetUpdatesRequest
import com.artfable.telegram.api.service.TelegramSender
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.Executor
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * @author aveselov
 * @since 08/08/2020
 */
@ExtendWith(MockitoExtension::class)
internal class LongPollingTelegramBotTest {
    @Mock
    private lateinit var taskExecutor: Executor

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

        callSubscribeToUpdates(longPollingTelegramBot)

        verify(behaviour).parse(updates)
        verify(behaviour2).parse(updates)
        verify(taskExecutor).execute(any())
    }

    @Test
    fun subscribeToUpdates_skipFailed() {
        val longPollingTelegramBot = createBot()
        val updates = listOf(Update(1L))

        given(telegramSender.executeMethod<List<Update>>(GetUpdatesRequest(timeout = 100))).willReturn(updates)
        given(behaviour.parse(updates)).willThrow(IllegalArgumentException::class.java)

        callSubscribeToUpdates(longPollingTelegramBot)

        verify(behaviour2).parse(updates)
        verify(taskExecutor).execute(any())
    }

    @Test
    fun subscribeToUpdates_doNotSkipFailed() {
        val longPollingTelegramBot = createBot(false)
        val updates = listOf(Update(1L))

        given(telegramSender.executeMethod<List<Update>>(GetUpdatesRequest(timeout = 100))).willReturn(updates)
        given(behaviour.parse(updates)).willThrow(IllegalArgumentException::class.java)

        assertThrows<IllegalArgumentException> {
            callSubscribeToUpdates(longPollingTelegramBot)
        }

        verify(behaviour2).parse(updates)
        verify(taskExecutor, never()).execute(any())
    }

    private fun createBot(skipFailed: Boolean? = null): LongPollingTelegramBot {

        return if (skipFailed == null) {
            object : LongPollingTelegramBot(taskExecutor, telegramSender, setOf(behaviour, behaviour2), setOf()) {}
        } else {
            object : LongPollingTelegramBot(taskExecutor, telegramSender, setOf(behaviour, behaviour2), setOf(), skipFailed) {}
        }
    }

    private fun callSubscribeToUpdates(bot: LongPollingTelegramBot) {
        try {
            LongPollingTelegramBot::class.declaredMemberFunctions.asSequence()
                .filter { it.name == "subscribeToUpdates" }
                .onEach { it.isAccessible = true }
                .first()
                .call(bot, null)
        } catch (e: InvocationTargetException) {
            throw e.targetException
        }
    }
}