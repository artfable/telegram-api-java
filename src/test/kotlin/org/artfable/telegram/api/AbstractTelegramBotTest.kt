package org.artfable.telegram.api

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

/**
 * @author aveselov
 * @since 15/08/2020
 */
@ExtendWith(MockitoExtension::class)
internal class AbstractTelegramBotTest {

    @Mock
    private lateinit var behaviour: Behaviour

    @Mock
    private lateinit var callbackBehaviour: CallbackBehaviour

    @Test
    fun parse_inCallback() {
        val bot = createBot(true)
        val update = Update(1L, callbackQuery = CallbackQuery(2L))

        given(callbackBehaviour.parse(update)).willReturn(true)

        bot.parse(mutableListOf(update))

        verify(behaviour, never()).parse(anyList())
    }

    @Test
    fun parse_notInCallback() {
        val bot = createBot(true)
        val update = Update(1L, callbackQuery = CallbackQuery(2L))

        given(callbackBehaviour.parse(update)).willReturn(false)

        bot.parse(mutableListOf(update))

        verify(behaviour).parse(anyList())
    }

    @Test
    fun parse_skipFailed() {
        val bot = createBot(true)
        val update = Update(1L)

        given(callbackBehaviour.parse(eq(update))).willThrow(RuntimeException::class.java)

        bot.parse(mutableListOf(update))
    }

    @Test
    fun getUpdate_doNotSkipFailed() {
        val bot = createBot(false)
        val update = Update(1L)

        given(callbackBehaviour.parse(eq(update))).willThrow(RuntimeException::class.java)

        assertThrows<IllegalArgumentException> {
            bot.parse(mutableListOf(update))
        }
    }

    private fun createBot(skipFailed: Boolean): AbstractTelegramBot {
        return if (skipFailed) {
            object : AbstractTelegramBot(setOf(behaviour), setOf(callbackBehaviour)) {
                public override fun parse(updates: MutableList<Update>?) {
                    super.parse(updates)
                }
            }
        } else {
            object : AbstractTelegramBot(setOf(behaviour), setOf(callbackBehaviour), skipFailed) {
                public override fun parse(updates: MutableList<Update>?) {
                    super.parse(updates)
                }
            }
        }

    }
}