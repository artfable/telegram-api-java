package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Any response from Telegram Bot API. In case of success #errorCode and #description are null,
 * otherwise #result is null
 *
 * @author aveselov
 * @since 05/08/2020
 */
data class TelegramResponse<T>(
        @JsonProperty("ok") val ok: Boolean,
        @JsonProperty("error_code") val errorCode: Int?,
        @JsonProperty("description") val description: String?,
        @JsonProperty("result") val result: T?
)