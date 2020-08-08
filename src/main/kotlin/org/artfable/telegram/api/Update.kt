package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author artfable
 * 22.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Update(
        @JsonProperty("update_id") val updateId: Long,
        @JsonProperty("message") val message: Message? = null,
        @JsonProperty("edited_message") val editedMessage: Message? = null,
        @JsonProperty("channel_post") val channelPost: Message? = null,
        @JsonProperty("edited_channel_post") val editedChannelPost: Message? = null,
        @JsonProperty("callback_query") val callbackQuery: CallbackQuery? = null
) {
    fun extractMessage(): Message? {
        return message
                ?: editedMessage
                ?: channelPost
                ?: editedChannelPost
                ?: callbackQuery?.message
    }
}
