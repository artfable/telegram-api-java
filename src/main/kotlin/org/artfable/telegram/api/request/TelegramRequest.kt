package org.artfable.telegram.api.request

import com.fasterxml.jackson.annotation.JsonIgnore
import org.artfable.telegram.api.TelegramBotMethod

/**
 * @author aveselov
 * @since 04/08/2020
 */
abstract class TelegramRequest(
        @JsonIgnore val method: TelegramBotMethod
)