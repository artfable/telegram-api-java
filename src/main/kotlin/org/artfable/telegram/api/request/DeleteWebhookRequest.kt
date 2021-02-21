package org.artfable.telegram.api.request

import com.fasterxml.jackson.core.type.TypeReference
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse

/**
 * @author aveselov
 * @since 06/08/2020
 */
class DeleteWebhookRequest() :
    TelegramRequest<Boolean>(TelegramBotMethod.DELETE_WEBHOOK, object : TypeReference<TelegramResponse<Boolean>>() {}) {

    override fun toString(): String {
        return "DeleteWebhookRequest"
    }
}
