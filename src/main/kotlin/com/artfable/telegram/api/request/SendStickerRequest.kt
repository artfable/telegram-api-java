package com.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.artfable.telegram.api.Message
import com.artfable.telegram.api.TelegramBotMethod
import com.artfable.telegram.api.TelegramResponse
import com.artfable.telegram.api.keyboard.InlineKeyboard

/**
 * @author aveselov
 * @since 06/08/2020
 */
data class SendStickerRequest(
    @JsonProperty("chat_id") val chatId: Long,
    @JsonProperty("sticker") val sticker: String,
    @JsonProperty("disable_notification") val disableNotification: Boolean? = null,
    @JsonProperty("reply_to_message_id") val replayToMessageId: Long? = null,
    @JsonProperty("reply_markup") val replyMarkup: InlineKeyboard? = null
) : TelegramRequest<Message>(TelegramBotMethod.SEND_STICKER, object : TypeReference<TelegramResponse<Message>>() {})

// TODO: support files