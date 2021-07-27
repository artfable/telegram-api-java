package com.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonValue

/**
 * Define the format for message text.
 * https://core.telegram.org/bots/api#formatting-options
 *
 * @author aveselov
 * @since 06/08/2020
 */
enum class ParseMode(@JsonValue val modeName: String) {
    MARKDOWN_V2("MarkdownV2"),
    HTML("HTML"),
    MARKDOWN("Markdown")
}