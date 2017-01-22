package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Telegram answer for send's methods.
 *
 * @author artfable
 * 23.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TelegramSendResponse(@JsonProperty("ok") val ok: Boolean, @JsonProperty("result") val result: Message)