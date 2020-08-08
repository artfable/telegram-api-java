package org.artfable.telegram.api.request

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.core.io.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * @author aveselov
 * @since 08/08/2020
 */
internal class SetWebhookRequestTest {

    @Test
    fun asEntity_asJson() {
        val setWebhookRequest = SetWebhookRequest("test")

        assertEquals(HttpEntity(setWebhookRequest), setWebhookRequest.asEntity())
    }

    @Test
    fun asEntity_asForm() {
        val setWebhookRequest = SetWebhookRequest("test", mock(Resource::class.java))
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
        body.add("url", setWebhookRequest.url)
        body.add("certificate", setWebhookRequest.certificate)

        assertEquals(HttpEntity(body, headers), setWebhookRequest.asEntity())
    }
}