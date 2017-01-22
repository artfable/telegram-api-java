package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Telegram answer for {@link TelegramBotMethod#GET_UPDATES}.
 *
 * @author artfable
 * 22.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TelegramUpdateResponse(@JsonProperty("ok") val ok: Boolean, @JsonProperty("result") val result: List<Update>)