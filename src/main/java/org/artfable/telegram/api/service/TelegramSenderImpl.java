package org.artfable.telegram.api.service;

import org.artfable.telegram.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author artfable
 *         25.01.17
 */
public class TelegramSenderImpl implements TelegramSender {

    private static final Logger log = LoggerFactory.getLogger(TelegramSenderImpl.class);

    private RestTemplate restTemplate;
    private String token;

    private final Set<Message> messages = Collections.newSetFromMap(new WeakHashMap<>());

    public TelegramSenderImpl(RestTemplate restTemplate, String token) {
        this.restTemplate = restTemplate;
        this.token = token;
    }

    @Override
    public TelegramSendResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams) {
        Map<String, String> urlParams = new HashMap<>(2);
        urlParams.put("token", token);
        urlParams.put("method", method.getValue());

        switch (httpMethod) {
            case GET:
                return restTemplate.getForObject(UrlHelper.getUri(TelegramBot.URL, urlParams, queryParams), TelegramSendResponse.class);
            case POST:
                return restTemplate.postForObject(UrlHelper.getUri(TelegramBot.URL, urlParams), queryParams, TelegramSendResponse.class);
            default:
                throw new UnsupportedOperationException("Telegram Api works only with GET and POST methods");
        }
    }

    @Override
    public TelegramSendResponse singleSend(Message forMessage, HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams) {
        if (!messages.contains(forMessage)) {
            synchronized (messages) {
                if (messages.add(forMessage)) {
                    log.trace("Sender messages in memory: " + messages.size());
                    return send(httpMethod, method, queryParams);
                }
            }
        }
        return null;
    }
}
