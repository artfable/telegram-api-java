package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * @author aveselov
 * @since 06/08/2020
 */
data class SetWebhookRequest(
        @JsonProperty("url") val url: String,
        @JsonProperty("certificate") val certificate: Resource?
): TelegramRequest(TelegramBotMethod.SET_WEBHOOK, object: ParameterizedTypeReference<TelegramResponse<Boolean>>() {}) {
    override fun asEntity(): HttpEntity<out Any?> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
        body.add("url", url)

        certificate?.let { body.add("certificate", it) }

        return HttpEntity(body, headers);
    }
}
// TODO: fields, result, responseType