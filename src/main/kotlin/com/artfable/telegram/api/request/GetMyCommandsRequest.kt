package com.artfable.telegram.api.request

import com.fasterxml.jackson.core.type.TypeReference
import com.artfable.telegram.api.BotCommand
import com.artfable.telegram.api.TelegramBotMethod
import com.artfable.telegram.api.TelegramResponse

/**
 * @author aveselov
 * @since 25/08/2020
 */
class GetMyCommandsRequest : TelegramRequest<@JvmSuppressWildcards List<BotCommand>>(
    TelegramBotMethod.GET_MY_COMMANDS,
    object : TypeReference<TelegramResponse<List<BotCommand>>>() {})