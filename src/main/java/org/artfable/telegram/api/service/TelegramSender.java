package org.artfable.telegram.api.service;

import org.artfable.telegram.api.Message;
import org.artfable.telegram.api.TelegramBotMethod;
import org.artfable.telegram.api.TelegramSendResponse;
import org.artfable.telegram.api.request.TelegramRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * @author artfable
 *         25.01.17
 */
public interface TelegramSender {
    String URL = "https://api.telegram.org/bot{token}/{method}";

    TelegramSendResponse send(TelegramRequest telegramRequest);

    TelegramSendResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams);

    TelegramSendResponse send(TelegramBotMethod method, HttpEntity httpEntity);

    TelegramSendResponse singleSend(Message forMessage, HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams);
}
