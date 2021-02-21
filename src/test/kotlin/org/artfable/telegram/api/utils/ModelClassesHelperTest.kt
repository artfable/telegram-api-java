package org.artfable.telegram.api.utils

import org.artfable.telegram.api.Chat
import org.artfable.telegram.api.ChatType
import org.artfable.telegram.api.Message
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author aveselov
 * @since 11/02/2021
 */
internal class ModelClassesHelperTest {

    @Test
    fun notNullFieldsToString() {
        val message = Message(42L, date = 42, chat = Chat(1L, ChatType.CHANNEL, title = "test"), text = "Test")

        assertEquals(
            "Message(chat=Chat(id=1, title=test, type=CHANNEL), date=42, messageId=42, text=Test)",
            message.notNullFieldsToString()
        )
    }
}