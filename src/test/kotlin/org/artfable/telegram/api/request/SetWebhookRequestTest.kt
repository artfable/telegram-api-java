package org.artfable.telegram.api.request

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.io.InputStream

/**
 * @author aveselov
 * @since 08/08/2020
 */
internal class SetWebhookRequestTest {

    @Test
    fun asEntity_asJson() {
        val setWebhookRequest = SetWebhookRequest("test")

        assertEquals(TelegramRequest.TelegramRequestEntity(setWebhookRequest), setWebhookRequest.asEntity())
    }

    @Test
    fun asEntity_asForm() {
        val setWebhookRequest = SetWebhookRequest("test", mock(InputStream::class.java))
        val headers = mapOf(Pair("Content-Type", listOf("multipart/form-data")))

        val body = mutableMapOf(
            Pair("url", listOf(setWebhookRequest.url)),
            Pair("certificate", listOf(setWebhookRequest.certificate))
        )

        assertEquals(TelegramRequest.TelegramRequestEntity(body, headers), setWebhookRequest.asEntity())
    }
}