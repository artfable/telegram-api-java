package com.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.artfable.telegram.api.TelegramBotMethod
import com.artfable.telegram.api.TelegramResponse
import com.artfable.telegram.api.UpdateType
import java.io.InputStream

/**
 * @author aveselov
 * @since 06/08/2020
 */
data class SetWebhookRequest(
    @JsonProperty("url") val url: String,
    @JsonProperty("certificate") val certificate: InputStream? = null,
    @JsonProperty("allowed_updates") val allowedUpdates: Array<UpdateType>? = null
) : TelegramRequest<Boolean>(TelegramBotMethod.SET_WEBHOOK, object : TypeReference<TelegramResponse<Boolean>>() {}) {
    override fun asEntity(): TelegramRequestEntity {
        certificate ?: return TelegramRequestEntity(this)

        val headers = mapOf(Pair("Content-Type", listOf("multipart/form-data")))

        val body: MutableMap<String, List<Any>> = mutableMapOf(Pair("url", listOf(url)))
        allowedUpdates?.let { body.put("allowed_updates", listOf(allowedUpdates)) }

        body["certificate"] = listOf(certificate)

        return TelegramRequestEntity(body, headers)
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