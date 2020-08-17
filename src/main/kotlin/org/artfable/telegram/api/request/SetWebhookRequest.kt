package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.artfable.telegram.api.UpdateType
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
        @JsonProperty("certificate") val certificate: Resource? = null,
        @JsonProperty("allowed_updates") val allowedUpdates: Array<UpdateType>? = null
): TelegramRequest<Boolean>(TelegramBotMethod.SET_WEBHOOK, object: ParameterizedTypeReference<TelegramResponse<Boolean>>() {}) {
    override fun asEntity(): HttpEntity<out Any?> {
        certificate ?: return HttpEntity(this)

        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
        body.add("url", url)
        allowedUpdates?.let { body.add("allowed_updates", allowedUpdates) }

        body.add("certificate", certificate)

        return HttpEntity(body, headers)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SetWebhookRequest

        if (url != other.url) return false
        if (certificate != other.certificate) return false
        if (allowedUpdates != null) {
            if (other.allowedUpdates == null) return false
            if (!allowedUpdates.contentEquals(other.allowedUpdates)) return false
        } else if (other.allowedUpdates != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + (certificate?.hashCode() ?: 0)
        result = 31 * result + (allowedUpdates?.contentHashCode() ?: 0)
        return result
    }
}
// TODO: fields