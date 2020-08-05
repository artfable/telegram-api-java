package org.artfable.telegram.api

import org.springframework.http.HttpStatus

/**
 * @author aveselov
 * @since 05/08/2020
 */
class TelegramRequestException(errorCode: Int, description: String): RuntimeException(description) {
    init {
        val statusCode: HttpStatus? = HttpStatus.resolve(errorCode)
    }
}