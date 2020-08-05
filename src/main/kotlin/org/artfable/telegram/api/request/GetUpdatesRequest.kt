package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.TelegramResponse
import org.springframework.core.ParameterizedTypeReference

/**
 * @author aveselov
 * @since 05/08/2020
 */
data class GetUpdatesRequest(
        @JsonProperty("offset") val offset: Long?,
        @JsonProperty("timeout") val timeout: Int?,
        @JsonProperty("limit") val limit: Int?
): TelegramRequest(TelegramBotMethod.GET_UPDATES, object: ParameterizedTypeReference<TelegramResponse<List<Update>>>() {}) {
    init {
        timeout?.let { check(it >= 0) }
        limit?.let { check(it >= 1 || it <= 100) }
    }
}
// TODO: allowed_updates