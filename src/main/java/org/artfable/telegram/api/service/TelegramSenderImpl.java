package org.artfable.telegram.api.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.artfable.telegram.api.Message;
import org.artfable.telegram.api.TelegramBotMethod;
import org.artfable.telegram.api.TelegramSendResponse;
import org.artfable.telegram.api.UrlHelper;
import org.artfable.telegram.api.request.TelegramRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.client.RestTemplate;

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
    public TelegramSendResponse send(TelegramRequest telegramRequest) {
        if (telegramRequest.getMethod().getManager()) {
            log.debug(restTemplate.patchForObject(UrlHelper.getUri(URL, getUrlParams(telegramRequest.getMethod())), telegramRequest, String.class));
            return null;
        }

        return restTemplate.postForObject(UrlHelper.getUri(URL, getUrlParams(telegramRequest.getMethod())), telegramRequest, TelegramSendResponse.class);
    }

    @Override
    public TelegramSendResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams) {
        if (method.getManager()) {
            log.debug(send(httpMethod, getUrlParams(method), queryParams, String.class));
            return null;
        }

        return send(httpMethod, getUrlParams(method), queryParams, TelegramSendResponse.class);
    }

    @Override
    public TelegramSendResponse send(TelegramBotMethod method, HttpEntity httpEntity) {
        log.debug(restTemplate.postForObject(UrlHelper.getUri(URL, getUrlParams(method)), httpEntity, String.class));
        return null;
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

    private <T> T send(HttpMethod httpMethod, Map<String, String> urlParams, Map<String, Object> queryParams, Class<T> responseClass) {
        switch (httpMethod) {
            case GET:
                return restTemplate.getForObject(UrlHelper.getUri(URL, urlParams, queryParams), responseClass);
            case POST:
                return restTemplate.postForObject(UrlHelper.getUri(URL, urlParams), queryParams, responseClass);
            default:
                throw new UnsupportedOperationException("Telegram Api works only with GET and POST methods");
        }
    }

    private Map<String, String> getUrlParams(TelegramBotMethod method) {
        return Map.of(
                "token", token,
                "method", method.getValue()
        );
    }
}
