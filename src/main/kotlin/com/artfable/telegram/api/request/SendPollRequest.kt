package com.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.artfable.telegram.api.*
import com.artfable.telegram.api.keyboard.InlineKeyboard

/**
 * Add native poll to the chat
 *
 * @author aveselov
 * @since 02/09/2020
 */
data class SendPollRequest(
    @JsonProperty("chat_id") val chatId: String,
    @JsonProperty("question") val question: String,
    @JsonProperty("options") val options: List<String>,
    @JsonProperty("is_anonymous") val anonymous: Boolean? = null,
    @JsonProperty("type") val type: PollType? = null,
    @JsonProperty("allows_multiple_answers") val allowsMultipleAnswers: Boolean? = null,
    @JsonProperty("correct_option_id") val correctOptionId: Int? = null,
    @JsonProperty("explanation") val explanation: String? = null,
    @JsonProperty("explanation_parse_mode") val explanationParseMode: ParseMode? = null,
    @JsonProperty("open_period") val openPeriod: Long? = null,
    @JsonProperty("close_date") val closeDate: Long? = null,
    @JsonProperty("is_closed") val closed: Boolean? = null,
    @JsonProperty("disable_notification") val disableNotification: Boolean? = null,
    @JsonProperty("reply_to_message_id") val replyToMessageId: Long? = null,
    @JsonProperty("reply_markup") val replyMarkup: InlineKeyboard? = null // TODO: others markups
) : TelegramRequest<Message>(TelegramBotMethod.SEND_POLL, object : TypeReference<TelegramResponse<Message>>() {}) {
    init {
        check(question.length in 1..255)
        check(options.size in 2..10)
        options.forEach { check(it.length in 1..100) }
        openPeriod?.let { check(it in 5..600) }
        check(closeDate == null || openPeriod == null)
    }
}