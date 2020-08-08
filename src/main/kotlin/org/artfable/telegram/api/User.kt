package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author artfable
 * 30.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class User (
        @JsonProperty("id") val id: Long,
        @JsonProperty("first_name") val firstName: String,
        @JsonProperty("last_name") val lastName: String? = null,
        @JsonProperty("username") val username: String? = null
)
