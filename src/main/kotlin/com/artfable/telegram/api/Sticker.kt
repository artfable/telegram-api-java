package com.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 18/07/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Sticker(
        @JsonProperty("emoji") val emoji: String? = null,
        @JsonProperty("file_id") val stickerId: String? = null,
        @JsonProperty("set_name") val collectionName: String? = null,
        @JsonProperty("thumb") val thumb: Thumb? = null
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Thumb(
            @JsonProperty("file_id") val thumbId: String
    )
}