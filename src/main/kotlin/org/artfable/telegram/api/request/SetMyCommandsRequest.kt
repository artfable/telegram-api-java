package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import org.artfable.telegram.api.BotCommand
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse

/**
 * @author aveselov
 * @since 25/08/2020
 */
data class SetMyCommandsRequest(
    @JsonProperty("commands") val commands: List<BotCommand>
) : TelegramRequest<Boolean>(TelegramBotMethod.SET_MY_COMMANDS, object : TypeReference<TelegramResponse<Boolean>>() {})