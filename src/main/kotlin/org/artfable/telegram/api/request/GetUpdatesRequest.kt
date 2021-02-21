package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.UpdateType

/**
 * @author aveselov
 * @since 05/08/2020
 */
data class GetUpdatesRequest(
    @JsonProperty("offset") val offset: Long? = null,
    @JsonProperty("timeout") val timeout: Int? = null,
    @JsonProperty("limit") val limit: Int? = null,
    @JsonProperty("allowed_updates") val allowedUpdates: Array<UpdateType>? = null
) : TelegramRequest<@JvmSuppressWildcards List<Update>>(
    TelegramBotMethod.GET_UPDATES,
    object : TypeReference<TelegramResponse<List<Update>>>() {}) {
    init {
        timeout?.let { check(it >= 0) }
        limit?.let { check(it in 1..100) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetUpdatesRequest

        if (offset != other.offset) return false
        if (timeout != other.timeout) return false
        if (limit != other.limit) return false
        if (allowedUpdates != null) {
            if (other.allowedUpdates == null) return false
            if (!allowedUpdates.contentEquals(other.allowedUpdates)) return false
        } else if (other.allowedUpdates != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = offset?.hashCode() ?: 0
        result = 31 * result + (timeout ?: 0)
        result = 31 * result + (limit ?: 0)
        result = 31 * result + (allowedUpdates?.contentHashCode() ?: 0)
        return result
    }
}
