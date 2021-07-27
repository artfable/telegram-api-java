package com.artfable.telegram.api

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
            return valueOf(value.uppercase())
        }
    }

    @JsonValue
    fun toValue(): String {
        return name.lowercase()
    }

}