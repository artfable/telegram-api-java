package org.artfable.telegram.api.service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.artfable.telegram.api.Message;
import org.artfable.telegram.api.TelegramBotMethod;
import org.artfable.telegram.api.TelegramRequestException;
import org.artfable.telegram.api.TelegramServerException;
import org.artfable.telegram.api.UrlHelper;
import org.artfable.telegram.api.request.TelegramRequest;
import org.artfable.telegram.api.TelegramResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    public <T> T executeMethod(TelegramRequest telegramRequest) {
        log.debug("Sending request " + telegramRequest);

        TelegramResponse<T> response = null;
        try {
            response = send(telegramRequest);
        } catch (Exception e) {
            throw new TelegramServerException("Can't execute request", e);
        }

        log.debug("Get response " + response);

        if (response.getOk()) {
            return response.getResult();
        } else {
            if (response.getErrorCode() == null || response.getDescription() == null) {
                throw new TelegramServerException("Invalid response format for request " + telegramRequest.toString());
            }
            throw new TelegramRequestException(response.getErrorCode(), response.getDescription());
        }
    }

    @Override
    public <T> TelegramResponse<T> send(TelegramRequest telegramRequest) {
        return (TelegramResponse<T>) restTemplate
                .exchange(UrlHelper.getUri(URL, getUrlParams(telegramRequest.getMethod())), HttpMethod.POST, telegramRequest.asEntity(), telegramRequest.getResponseType())
                .getBody();
    }

    @Override
    public TelegramResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams) {
        return send(httpMethod, getUrlParams(method), queryParams, TelegramResponse.class);
    }

    @Override
    public TelegramResponse send(TelegramBotMethod method, HttpEntity httpEntity) {
        log.debug(restTemplate.postForObject(UrlHelper.getUri(URL, getUrlParams(method)), httpEntity, String.class));
        return null;
    }

    @Override
    public TelegramResponse singleSend(Message forMessage, HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams) {
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
