package org.artfable.telegram.api

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

/**
 * @author aveselov
 * @since 15/08/2020
 */
@ExtendWith(MockitoExtension::class)
internal class AbstractTelegramBotTest {

    @Mock
    private lateinit var behavior: Behavior

    @Mock
    private lateinit var callbackBehaviour: CallbackBehaviour

    @Mock
    private var skipFailAction: Runnable? = null

    @Test
    fun parse_inCallback() {
        val bot = createBot(true)
        val update = Update(1L, callbackQuery = CallbackQuery(2L))

        given(callbackBehaviour.parse(update)).willReturn(true)

        bot.parse(mutableListOf(update), skipFailAction)

        verify(behavior, never()).isSubscribed
        verify(behavior, never()).parse(anyList())
    }

    @Test
    fun parse_notInCallback() {
        val bot = createBot(true)
        val update = Update(1L, callbackQuery = CallbackQuery(2L))

        given(behavior.isSubscribed).willReturn(true)
        given(callbackBehaviour.parse(update)).willReturn(false)

        bot.parse(mutableListOf(update), skipFailAction)

        verify(behavior).parse(anyList())
    }

    @Test
    fun parse_skipFailed() {
        val bot = createBot(true)
        val update = Update(1L)

        given(callbackBehaviour.parse(eq(update))).willThrow(RuntimeException::class.java)

        bot.parse(mutableListOf(update), skipFailAction)

        verify(skipFailAction)?.run()
    }

    @Test
    fun getUpdate_doNotSkipFailed() {
        val bot = createBot(false)
        val update = Update(1L)

        given(callbackBehaviour.parse(eq(update))).willThrow(RuntimeException::class.java)

        assertThrows<IllegalArgumentException> {
            bot.parse(mutableListOf(update), skipFailAction)
        }

        verify(skipFailAction, never())?.run()
    }

    private fun createBot(skipFailed: Boolean): AbstractTelegramBot {
        return if (skipFailed) {
            object : AbstractTelegramBot("token", setOf(behavior), setOf(callbackBehaviour)) {
                public override fun parse(updates: MutableList<Update>?, skipFailAction: Runnable?) {
                    super.parse(updates, skipFailAction)
                }
            }
        } else {
            object : AbstractTelegramBot("token", setOf(behavior), setOf(callbackBehaviour), skipFailed) {
                public override fun parse(updates: MutableList<Update>?, skipFailAction: Runnable?) {
                    super.parse(updates, skipFailAction)
                }
            }
        }

    }
}