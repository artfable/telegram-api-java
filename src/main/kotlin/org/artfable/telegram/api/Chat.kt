package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author artfable
 * 22.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Chat(
        @JsonProperty("id") val id: Long, // In the Telegram Api - Integer, but it's lie!
        @JsonProperty("type") val type: ChatType,
        @JsonProperty("title") val title: String?,
        @JsonProperty("username") val username: String?,
        @JsonProperty("first_name") val firstName: String?,
        @JsonProperty("last_name") val lastName: String?,
        @JsonProperty("all_members_are_administrators") val allMembersAreAdministrators: Boolean?
)


//type	String	“private”, “group”, “supergroup” or “channel”