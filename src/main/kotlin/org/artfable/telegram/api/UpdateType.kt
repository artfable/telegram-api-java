package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author aveselov
 * @since 06/08/2020
 */
enum class UpdateType(@JsonValue val typeName: String) {
    MESSAGE("message"),
    EDITED_MESSAGE("edited_message"),
    CHANNEL_POST("channel_post"),
    EDITED_CHANNEL_POST("edited_channel_post"),
    CALLBACK_QUERY("callback_query")
}

// TODO: add all update's types