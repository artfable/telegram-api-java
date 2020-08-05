package org.artfable.telegram.api.service;

import java.util.Map;

import org.artfable.telegram.api.Message;
import org.artfable.telegram.api.TelegramBotMethod;
import org.artfable.telegram.api.request.TelegramRequest;
import org.artfable.telegram.api.TelegramResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

/**
 * @author artfable
 *         25.01.17
 */
public interface TelegramSender {
    String URL = "https://api.telegram.org/bot{token}/{method}";

    <T> T executeMethod(TelegramRequest telegramRequest);

    @Deprecated
    <T> TelegramResponse<T> send(TelegramRequest telegramRequest);

    @Deprecated
    TelegramResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams);

    @Deprecated
    TelegramResponse send(TelegramBotMethod method, HttpEntity httpEntity);

    @Deprecated
    TelegramResponse singleSend(Message forMessage, HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams);
}
