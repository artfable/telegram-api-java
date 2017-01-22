package org.artfable.telegram.api;

/**
 * @author artfable
 *         22.01.17
 */
public interface TelegramBot {
    String URL = "https://api.telegram.org/bot{token}/{method}";

    /**
     * This method starts subscription. Always in a separate thread.
     *
     * @see org.springframework.scheduling.annotation.Async
     */
    void subscribeToUpdates(Integer lastId);
}
