package org.artfable.telegram.api;

import org.artfable.telegram.api.service.TelegramSender;

import java.util.List;

/**
 * Represent any function of the bot. Can receive updates if {@link #isSubscribed()} true.
 *
 * @author artfable
 *         22.01.17
 */
public interface Behaviour {

    /**
     * Action on the start of the bot with this {@link Behaviour}
     */
    default void start() {}

    /**
     * React to {@link Update} if {@link #isSubscribed()} true, will not be called if false.
     *
     * @param updates - List of updates from Telegram API
     */
    void parse(List<Update> updates);

    /**
     *
     * @return true - if {@link Behaviour} should react to updates, otherwise false
     */
    boolean isSubscribed();
}
