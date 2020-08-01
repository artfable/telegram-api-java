package org.artfable.telegram.api.keyboard

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 01/08/2020
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class InlineKeyboard(
        @JsonProperty("inline_keyboard") val lines: Array<Array<out InlineKeyboardBtn>>
) {
    constructor(vararg lines: InlineKeyboardBtn): this(arrayOf(lines))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InlineKeyboard

        if (!lines.contentDeepEquals(other.lines)) return false

        return true
    }

    override fun hashCode(): Int {
        return lines.contentDeepHashCode()
    }

    override fun toString(): String {
        return "InlineKeyboard(lines=${lines.contentDeepToString()})"
    }


}