package com.artfable.telegram.api

/**
 * TODO: add type
 *
 * @author artfable
 * 22.01.17
 */
enum class TelegramBotMethod(val value: String) {
    GET_UPDATES("getUpdates"),
    SEND_MESSAGE("sendMessage"),
    SEND_POLL("sendPoll"),
    DELETE_MESSAGE("deleteMessage"),
    SEND_STICKER("sendSticker"),

//    Webhook
    SET_WEBHOOK("setWebhook"),
    DELETE_WEBHOOK("deleteWebhook"),

//    Bot settings
    GET_MY_COMMANDS("getMyCommands"),
    SET_MY_COMMANDS("setMyCommands")
}