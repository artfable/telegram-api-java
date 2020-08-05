package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 05/08/2020
 */
data class TelegramResponse<T>(
        @JsonProperty("ok") val ok: Boolean,
        @JsonProperty("error_code") val errorCode: Int?,
        @JsonProperty("description") val description: String?,
        @JsonProperty("result") val result: T?
)