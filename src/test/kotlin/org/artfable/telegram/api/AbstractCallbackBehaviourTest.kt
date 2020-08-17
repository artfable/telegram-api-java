package org.artfable.telegram.api

import org.artfable.telegram.api.keyboard.InlineKeyboardBtn
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify

/**
 * @author aveselov
 * @since 15/08/2020
 */
internal class AbstractCallbackBehaviourTest {

    @Test
    fun parse_matchedUpdate() {
        val update = Update(1L, callbackQuery = CallbackQuery(2L, data = "key-value"))
        val callbackBehaviour = object : AbstractCallbackBehaviour("key") {
            override fun parse(value: String, callbackQuery: CallbackQuery?) {
                assertEquals("value", value)
                assertEquals(update.callbackQuery, callbackQuery)
            }
        }

        assertTrue(callbackBehaviour.parse(update))
    }

    @Test
    fun parse_matchedUpdateEmpty() {
        val update = Update(1L, callbackQuery = CallbackQuery(2L, data = "key"))
        val callbackBehaviour = object : AbstractCallbackBehaviour("key") {
            override fun parse(value: String, callbackQuery: CallbackQuery?) {
                assertEquals("", value)
                assertEquals(update.callbackQuery, callbackQuery)
            }
        }

        assertTrue(callbackBehaviour.parse(update))
    }

    @Test
    fun parse_otherKey() {
        val update = Update(1L, callbackQuery = CallbackQuery(2L, data = "key2-value"))
        val callbackBehaviour = object : AbstractCallbackBehaviour("key") {
            override fun parse(value: String, callbackQuery: CallbackQuery?) {
                throw IllegalArgumentException()
            }
        }

        assertFalse(callbackBehaviour.parse(update))
    }

    @Test
    fun createBtn() {
        val callbackBehaviour = object : AbstractCallbackBehaviour("key") {
            override fun parse(value: String, callbackQuery: CallbackQuery?) {
            }
        }

        assertEquals(InlineKeyboardBtn("text", "key-value"), callbackBehaviour.createBtn("text", "value"))
    }
}