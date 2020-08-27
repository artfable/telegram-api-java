package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 25/08/2020
 */
data class BotCommand(
        @JsonProperty("command") val command: String,
        @JsonProperty("description") val description: String
) {
    init {
        check(command.length in 1..32)
        check(description.length in 3..256)
    }
}