package org.artfable.telegram.api.request

import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.springframework.core.ParameterizedTypeReference

/**
 * @author aveselov
 * @since 06/08/2020
 */
class DeleteWebhookRequest():
        TelegramRequest<Boolean>(TelegramBotMethod.DELETE_WEBHOOK, object: ParameterizedTypeReference<TelegramResponse<Boolean>>() {}) {

    override fun toString(): String {
        return "DeleteWebhookRequest"
    }
}
