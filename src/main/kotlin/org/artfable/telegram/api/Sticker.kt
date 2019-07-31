package org.artfable.telegram.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author aveselov
 * @since 18/07/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Sticker(
        @JsonProperty("emoji") val emoji: String?,
        @JsonProperty("file_id") val stickerId: String?,
        @JsonProperty("set_name") val collectionName: String?,
        @JsonProperty("thumb") val thumb: Thumb?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Thumb(
            @JsonProperty("file_id") val thumbId: String
    )
}