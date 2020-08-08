package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.artfable.telegram.api.keyboard.InlineKeyboard

/**
 * @author artfable
 * 22.01.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Message(
        @JsonProperty("message_id") val messageId: Long,
        @JsonProperty("from") val from: User? = null,
        @JsonProperty("forward_from") val forwardFrom: User? = null,
        @JsonProperty("date") val date: Int,
        @JsonProperty("chat") val chat: Chat,
        @JsonProperty("reply_to_message") val replyToMessage: Message? = null,
        @JsonProperty("edit_date") val editDate: Int? = null,
        @JsonProperty("text") val text: String? = null,
        @JsonProperty("sticker") val sticker: Sticker? = null,
        @JsonProperty("caption") val caption: String? = null,
        @JsonProperty("new_chat_title") val newChartTitle: String? = null,
        @JsonProperty("delete_chat_photo") val deleteChatPhoto: Boolean? = null,
        @JsonProperty("group_chat_created") val groupChatCreated: Boolean? = null,
        @JsonProperty("supergroup_chat_created") val supergroupChatCreated: Boolean? = null,
        @JsonProperty("channel_chat_created") val channelChatCreated: Boolean? = null,
        @JsonProperty("migrate_to_chat_id") val migrateToChatId: Long? = null,
        @JsonProperty("migrate_from_chat_id") val migrateFromChatId: Long? = null,
        @JsonProperty("pinned_message") val pinnedMessage: Message? = null,
        @JsonProperty("reply_markup") val keyboard: InlineKeyboard? = null
)

//forward_from_chat	Chat
//forward_from_message_id	Integer
//forward_date	Integer
//entities	Array of MessageEntity
//audio	Audio
//document	Document
//game	Game
//photo	Array of PhotoSize
//video	Video
//voice	Voice
//contact	Contact
//location	Location
//venue	Venue
//new_chat_member	User
//left_chat_member	User
//new_chat_photo Array of PhotoSize
