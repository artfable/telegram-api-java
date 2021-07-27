package com.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Contain any action happened in the chat. Only 1 type of action will be represent in 1 {@link Update}.
 * Updates with {@link PollAnswer} will be only in case when a poll was created by the bot, in other cases
 * the bot will get {@link Update}s with {@link Poll} only when a poll was created or closed.
 *
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
        @JsonProperty("callback_query") val callbackQuery: CallbackQuery? = null,
        @JsonProperty("poll") val poll: Poll? = null,
        @JsonProperty("poll_answer") val pollAnswer: PollAnswer? = null
) {
    fun extractMessage(): Message? {
        return message
                ?: editedMessage
                ?: channelPost
                ?: editedChannelPost
                ?: callbackQuery?.message
    }
}
