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
        @JsonProperty("message") val message: Message?,
        @JsonProperty("edited_message") val editedMessage: Message?,
        @JsonProperty("channel_post") val channelPost: Message?,
        @JsonProperty("edited_channel_post") val editedChannelPost: Message?
)
