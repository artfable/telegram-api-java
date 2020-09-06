package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 02/09/2020
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Poll(
        @JsonProperty("id") val id: String,
        @JsonProperty("question") val question: String,
        @JsonProperty("options") val options: List<PollOption>,
        @JsonProperty("total_voter_count") val totalVoterCount: Int,
        @JsonProperty("is_closed") val closed: Boolean,
        @JsonProperty("is_anonymous") val anonymous: Boolean,
        @JsonProperty("type") val type: PollType,
        @JsonProperty("allows_multiple_answers")	val allowsMultipleAnswers: Boolean,
        @JsonProperty("correct_option_id") val correctOptionId: Int? = null,
        @JsonProperty("explanation") val explanation: String? = null,
// TODO: explanation_entities	Array of MessageEntity	Optional.
        @JsonProperty("open_period") val openPeriod: Long? = null,
        @JsonProperty("close_date") val closeDate: Long? = null
)