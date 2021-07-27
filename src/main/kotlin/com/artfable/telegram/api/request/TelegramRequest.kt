package com.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.artfable.telegram.api.TelegramBotMethod
import com.artfable.telegram.api.TelegramResponse
import java.lang.reflect.Type
import java.util.*

/**
 * Request to Telegram Bot API
 * @param responseType - represent type of expected response
 *
 * @author aveselov
 * @since 04/08/2020
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class TelegramRequest<T>(
    @JsonIgnore val method: TelegramBotMethod,
    responseType: TypeReference<out TelegramResponse<T>>
) {

    @JsonIgnore
    val responseType: Type = responseType.type

    @JsonIgnore
    val id: UUID = UUID.randomUUID()

    open fun asEntity(): TelegramRequestEntity {
        return TelegramRequestEntity(this)
    }

    data class TelegramRequestEntity(val body: Any, val headers: Map<String, List<String>>? = null)
}
