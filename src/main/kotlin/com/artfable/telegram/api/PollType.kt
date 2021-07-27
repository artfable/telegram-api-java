package com.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author aveselov
 * @since 02/09/2020
 */
enum class PollType(@JsonValue val typeName: String) {
    REGULAR("regular"),
    QUIZ("quiz")
}