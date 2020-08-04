package org.artfable.telegram.api

/**
 * TODO: add type
 *
 * @author artfable
 * 22.01.17
 */
enum class TelegramBotMethod(val value: String, val manager: Boolean = false) {
    GET_UPDATES("getUpdates"),
    SEND_MESSAGE("sendMessage"),
    DELETE_MESSAGE("deleteMessage", true),
    SEND_STICKER("sendSticker"),

//    Webhook
    SET_WEBHOOK("setWebhook", true),
    DELETE_WEBHOOK("deleteWebhook", true)
}