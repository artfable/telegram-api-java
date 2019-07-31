package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author artfable
 * 22.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Message(
        @JsonProperty("message_id") val messageId: Long,
        @JsonProperty("from") val from: User?,
        @JsonProperty("forward_from") val forwardFrom: User?,
        @JsonProperty("date") val date: Int,
        @JsonProperty("chat") val chat: Chat,
        @JsonProperty("reply_to_message") val replyToMessage: Message?,
        @JsonProperty("edit_date") val editDate: Int?,
        @JsonProperty("text") val text: String?,
        @JsonProperty("sticker") val sticker: Sticker?,
        @JsonProperty("caption") val caption: String?,
        @JsonProperty("new_chat_title") val newChartTitle: String?,
        @JsonProperty("delete_chat_photo") val deleteChatPhoto: Boolean?,
        @JsonProperty("group_chat_created") val groupChatCreated: Boolean?,
        @JsonProperty("supergroup_chat_created") val supergroupChatCreated: Boolean?,
        @JsonProperty("channel_chat_created") val channelChatCreated: Boolean?,
        @JsonProperty("migrate_to_chat_id") val migrateToChatId: Long?,
        @JsonProperty("migrate_from_chat_id") val migrateFromChatId: Long?,
        @JsonProperty("pinned_message") val pinnedMessage: Message?
)

//forward_from_chat	Chat
//forward_from_message_id	Integer
//forward_date	Integer
//entities	Array of MessageEntity
//audio	Audio
//document	Document
//game	Game
//photo	Array of PhotoSize
//sticker	Sticker
//video	Video
//voice	Voice
//contact	Contact
//location	Location
//venue	Venue
//new_chat_member	User
//left_chat_member	User
//new_chat_photo Array of PhotoSize
