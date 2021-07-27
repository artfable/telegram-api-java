package com.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 02/09/2020
 */
data class PollOption(
        @JsonProperty("text") val text: String,
        @JsonProperty("voter_count") val voterCount: Int
)