package com.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 02/09/2020
 */
data class PollAnswer(
        @JsonProperty("poll_id") val pollId: String,
        @JsonProperty("user") val user: User,
        @JsonProperty("option_ids") val options: List<Int>
)