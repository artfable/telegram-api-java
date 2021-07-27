package com.artfable.telegram.api

import com.artfable.telegram.api.keyboard.InlineKeyboardBtn
import com.artfable.telegram.api.service.TelegramSender
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * @author aveselov
 * @since 15/08/2020
 */
internal class AbstractCallbackBehaviourTest {

    @Test
    fun parse_matchedUpdate() {
        val update = Update(1L, callbackQuery = CallbackQuery(2L, data = "key-value"))
        val callbackBehaviour = createBehaviour { value, callbackQuery ->
            assertEquals("value", value)
            assertEquals(update.callbackQuery, callbackQuery)
        }

        assertTrue(callbackBehaviour.parse(update))
    }

    @Test
    fun parse_matchedUpdateEmpty() {
        val update = Update(1L, callbackQuery = CallbackQuery(2L, data = "key"))
        val callbackBehaviour = createBehaviour { value, callbackQuery ->
            assertEquals("", value)
            assertEquals(update.callbackQuery, callbackQuery)
        }

        assertTrue(callbackBehaviour.parse(update))
    }

    @Test
    fun parse_otherKey() {
        val update = Update(1L, callbackQuery = CallbackQuery(2L, data = "key2-value"))
        val callbackBehaviour = createBehaviour { _, _ -> throw IllegalArgumentException() }

        assertFalse(callbackBehaviour.parse(update))
    }

    @Test
    fun createBtn() {
        val callbackBehaviour = createBehaviour()

        assertEquals(InlineKeyboardBtn("text", "key-value"), callbackBehaviour.createBtn("text", "value"))
    }

    private fun createBehaviour(parse: (value: String, callbackQuery: CallbackQuery?) -> Unit = { _, _ -> }): AbstractCallbackBehaviour {
        return object : AbstractCallbackBehaviour("key") {
            override fun parse(value: String, callbackQuery: CallbackQuery?) {
                parse.invoke(value, callbackQuery)
            }
        }
    }
}
