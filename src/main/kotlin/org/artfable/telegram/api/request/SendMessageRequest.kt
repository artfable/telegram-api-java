package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import org.artfable.telegram.api.Message
import org.artfable.telegram.api.ParseMode
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.artfable.telegram.api.keyboard.InlineKeyboard

/**
 * @author aveselov
 * @since 04/08/2020
 */
data class SendMessageRequest(
    @JsonProperty("chat_id") val chatId: String, // can be @username
    @JsonProperty("text") val text: String,
    @JsonProperty("parse_mode") val parseMode: ParseMode? = null,
    @JsonProperty("disable_web_page_preview") val disableWebPagePreview: Boolean? = null,
    @JsonProperty("disable_notification") val disableNotification: Boolean? = null,
    @JsonProperty("reply_to_message_id") val replayToMessageId: Long? = null,
    @JsonProperty("allow_sending_without_reply") val allowSendWithoutReply: Boolean? = null,
    @JsonProperty("reply_markup") val replyMarkup: InlineKeyboard? = null
) : TelegramRequest<Message>(TelegramBotMethod.SEND_MESSAGE, object : TypeReference<TelegramResponse<Message>>() {})