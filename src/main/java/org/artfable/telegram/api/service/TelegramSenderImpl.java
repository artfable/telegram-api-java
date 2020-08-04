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
    public TelegramSendResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams) {
        Map<String, String> urlParams = new HashMap<>(2);
        urlParams.put("token", token);
        urlParams.put("method", method.getValue());

        if (method.getManager()) {
            log.debug(restTemplate.postForObject(UrlHelper.getUri(URL, urlParams), queryParams, String.class));
            return null;
        }

        switch (httpMethod) {
            case GET:
                return restTemplate.getForObject(UrlHelper.getUri(URL, urlParams, queryParams), TelegramSendResponse.class);
            case POST:
                return restTemplate.postForObject(UrlHelper.getUri(URL, urlParams), queryParams, TelegramSendResponse.class);
            default:
                throw new UnsupportedOperationException("Telegram Api works only with GET and POST methods");
        }
    }

    @Override
    public TelegramSendResponse send(HttpMethod httpMethod, TelegramBotMethod method, HttpEntity httpEntity) {
        Map<String, String> urlParams = new HashMap<>(2);
        urlParams.put("token", token);
        urlParams.put("method", method.getValue());

        log.debug(restTemplate.postForObject(UrlHelper.getUri(URL, urlParams), httpEntity, String.class));
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
}
