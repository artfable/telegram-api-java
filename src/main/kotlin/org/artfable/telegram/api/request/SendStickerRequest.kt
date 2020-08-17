package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.artfable.telegram.api.Message
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.artfable.telegram.api.keyboard.InlineKeyboard
import org.springframework.core.ParameterizedTypeReference

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
): TelegramRequest<Message>(TelegramBotMethod.SEND_STICKER, object: ParameterizedTypeReference<TelegramResponse<Message>>() {})

// TODO: support files