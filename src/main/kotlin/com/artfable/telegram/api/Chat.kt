package com.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author artfable
 * 22.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Chat(
        @JsonProperty("id") val id: Long,
        @JsonProperty("type") val type: ChatType,
        @JsonProperty("title") val title: String? = null,
        @JsonProperty("username") val username: String? = null,
        @JsonProperty("first_name") val firstName: String? = null,
        @JsonProperty("last_name") val lastName: String? = null,
        @JsonProperty("all_members_are_administrators") val allMembersAreAdministrators: Boolean? = null
)


//type	String	“private”, “group”, “supergroup” or “channel”