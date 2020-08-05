package org.artfable.telegram.api

/**
 * @author aveselov
 * @since 05/08/2020
 */
class TelegramServerException: RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}