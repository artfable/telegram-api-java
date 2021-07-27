package com.artfable.telegram.api.service;

import com.artfable.telegram.api.request.TelegramRequest;

import com.artfable.telegram.api.TelegramRequestException;
import com.artfable.telegram.api.TelegramServerException;
import com.artfable.telegram.api.request.TelegramRequest;

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
     * @throws TelegramServerException - Telegram API error
     * @throws TelegramRequestException - exception due to problem with the request
     */
    <T> T executeMethod(TelegramRequest<T> telegramRequest);

    /**
     * Execute request only if no request were executed before for thr provided #forUpdate
     *
     * @param forUpdate - id of update for which the bot is responding
     * @param telegramRequest - request to API
     * @param <T> - response type that corresponding to #telegramRequest
     * @return - response in case of success or null if request for the provided #forUpdate was already sent
     */
    <T> T singleExecuteMethod(Long forUpdate, TelegramRequest<T> telegramRequest);
}
