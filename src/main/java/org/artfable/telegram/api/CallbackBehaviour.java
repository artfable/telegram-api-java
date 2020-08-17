package org.artfable.telegram.api;

import org.artfable.telegram.api.keyboard.InlineKeyboardBtn;

/**
 * Represents actions that are done in response to a click on an {@link InlineKeyboardBtn}
 *
 * @see AbstractCallbackBehaviour
 *
 * @author aveselov
 * @since 13/08/2020
 */
public interface CallbackBehaviour {

    /**
     * Parse only {@link Update}s that are correspond to this {@link CallbackBehaviour}
     *
     * @param update - any {@link Update}
     * @return true if {@link Update} correspond to the {@link CallbackBehaviour}, false otherwise
     */
    boolean parse(Update update);

    /**
     * Return an {@link InlineKeyboardBtn} for the {@link CallbackBehaviour}
     *
     * @param text - text for the {@link InlineKeyboardBtn}
     * @param value - can be null, addition information that not influence routing
     * @return an {@link InlineKeyboardBtn} for the {@link CallbackBehaviour}
     */
    InlineKeyboardBtn createBtn(String text, String value);
}
