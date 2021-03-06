package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse

/**
 * @author aveselov
 * @since 06/08/2020
 */
data class DeleteMessageRequest(
    @JsonProperty("chat_id") val chatId: Long,
    @JsonProperty("message_id") val messageId: Long
) : TelegramRequest<Boolean>(TelegramBotMethod.DELETE_MESSAGE, object : TypeReference<TelegramResponse<Boolean>>() {})