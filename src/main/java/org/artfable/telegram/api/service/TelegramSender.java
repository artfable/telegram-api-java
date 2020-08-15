package org.artfable.telegram.api.service;

import org.artfable.telegram.api.request.TelegramRequest;

/**
 * Allow to send requests to Telegram API.
 *
 * @see TelegramRequest
 *
 * @author artfable
 *         25.01.17
 */
public interface TelegramSender {
    String URL = "https://api.telegram.org/bot{token}/{method}";

    /**
     *
     * @param telegramRequest - request to API
     * @param <T> - response type that corresponding to #telegramRequest
     * @return - response in case of success
     *
     * @throws org.artfable.telegram.api.TelegramServerException - Telegram API error
     * @throws org.artfable.telegram.api.TelegramRequestException - exception due to problem with the request
     */
    <T> T executeMethod(TelegramRequest telegramRequest);

    /**
     * Execute request only if no request were executed before for thr provided #forUpdate
     *
     * @param forUpdate - id of update for which the bot is responding
     * @param telegramRequest - request to API
     * @param <T> - response type that corresponding to #telegramRequest
     * @return - response in case of success or null if request for the provided #forUpdate was already sent
     */
    <T> T singleExecuteMethod(Long forUpdate, TelegramRequest telegramRequest);
}
