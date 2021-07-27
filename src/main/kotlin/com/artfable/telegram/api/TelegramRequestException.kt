package com.artfable.telegram.api

/**
 * @author aveselov
 * @since 05/08/2020
 */
class TelegramRequestException(errorCode: Int, description: String) : RuntimeException(description) {
    val statusCode: Int = errorCode
}