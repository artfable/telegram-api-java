package org.artfable.telegram.api.keyboard

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 01/08/2020
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class InlineKeyboardBtn(
        @JsonProperty("text") val text: String,
        @JsonProperty("callback_data") val callbackData: String?
)
