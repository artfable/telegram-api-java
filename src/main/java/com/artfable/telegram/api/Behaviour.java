package com.artfable.telegram.api;

import java.util.List;

/**
 * Represent any function of the bot that reacts to incoming updates.
 *
 * @author artfable
 *         22.01.17
 */
public interface Behaviour {

    /**
     * React to {@link Update}
     *
     * @param updates - List of updates from Telegram API
     */
    default void parse(List<Update> updates) {
        updates.parallelStream().forEach(this::parse);
    }

    void parse(Update update);
}
