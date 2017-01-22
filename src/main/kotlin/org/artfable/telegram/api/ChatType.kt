package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author artfable
 * 22.01.17
 */
enum class ChatType {
    PRIVATE,
    GROUP,
    SUPERGROUP,
    CHANNEL;

    companion object Factory {
        @JsonCreator
        fun getValue(value: String): ChatType {
            return ChatType.valueOf(value.toUpperCase())
        }
    }

    @JsonValue
    fun toValue(): String {
        return name.toLowerCase()
    }

}