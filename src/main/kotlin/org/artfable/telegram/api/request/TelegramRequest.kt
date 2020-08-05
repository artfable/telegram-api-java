package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonIgnore
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity

/**
 * @author aveselov
 * @since 04/08/2020
 */
abstract class TelegramRequest(
        @JsonIgnore val method: TelegramBotMethod,
        @JsonIgnore val responseType: ParameterizedTypeReference<out TelegramResponse<out Any?>>
) {
    open fun asEntity(): HttpEntity<out Any?> {
        return HttpEntity(this)
    }
}
// TODO: id for logs