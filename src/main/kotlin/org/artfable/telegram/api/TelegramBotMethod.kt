package org.artfable.telegram.api

/**
 * @author artfable
 * 22.01.17
 */
enum class TelegramBotMethod(val value: String) {
    GET_UPDATES("getUpdates"),
    SEND_MESSAGE("sendMessage")
}