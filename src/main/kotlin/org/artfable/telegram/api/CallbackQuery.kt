package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 18/07/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class CallbackQuery(
    @JsonProperty("id") val id: Long,
    @JsonProperty("from") val from: User?,
    @JsonProperty("data") val data: String?,
    @JsonProperty("message") val message: Message?
)