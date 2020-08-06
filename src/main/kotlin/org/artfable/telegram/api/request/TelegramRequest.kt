package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.artfable.telegram.api.TelegramBotMethod
import org.artfable.telegram.api.TelegramResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import java.util.*

/**
 * Request to Telegram Bot API
 * @param responseType - represent type of expected response
 *
 * @author aveselov
 * @since 04/08/2020
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class TelegramRequest(
        @JsonIgnore val method: TelegramBotMethod,
        @JsonIgnore val responseType: ParameterizedTypeReference<out TelegramResponse<out Any?>>
) {
    @JsonIgnore
    val id: UUID = UUID.randomUUID()

    open fun asEntity(): HttpEntity<out Any?> {
        return HttpEntity(this)
    }
}
