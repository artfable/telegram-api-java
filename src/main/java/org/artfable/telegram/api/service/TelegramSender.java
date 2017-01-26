package org.artfable.telegram.api.service;

import org.artfable.telegram.api.Message;
import org.artfable.telegram.api.TelegramBotMethod;
import org.artfable.telegram.api.TelegramSendResponse;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * @author artfable
 *         25.01.17
 */
public interface TelegramSender {
    TelegramSendResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams);

    TelegramSendResponse singleSend(Message forMessage, HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams);
}
