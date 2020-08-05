package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.artfable.telegram.api.Message
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.keyboard.InlineKeyboard
import org.artfable.telegram.api.TelegramResponse
import org.springframework.core.ParameterizedTypeReference

/**
 * @author aveselov
 * @since 04/08/2020
 */
data class SendMessageRequest(
        @JsonProperty("chat_id") val chatId: Long,
        @JsonProperty("text") val text: String,
        @JsonProperty("reply_to_message_id") val replayToMessageId: Long? = null,
        @JsonProperty("reply_markup") val replyMarkup: InlineKeyboard? = null
): TelegramRequest(TelegramBotMethod.SEND_MESSAGE, object: ParameterizedTypeReference<TelegramResponse<Message>>() {})