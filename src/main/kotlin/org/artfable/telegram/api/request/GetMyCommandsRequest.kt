package org.artfable.telegram.api.request

import org.artfable.telegram.api.BotCommand
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.springframework.core.ParameterizedTypeReference

/**
 * @author aveselov
 * @since 25/08/2020
 */
class GetMyCommandsRequest: TelegramRequest<@JvmSuppressWildcards List<BotCommand>>(TelegramBotMethod.GET_MY_COMMANDS, object: ParameterizedTypeReference<TelegramResponse<List<BotCommand>>>() {})