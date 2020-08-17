package org.artfable.telegram.api.service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.artfable.telegram.api.TelegramBotMethod;
import org.artfable.telegram.api.TelegramRequestException;
import org.artfable.telegram.api.TelegramServerException;
import org.artfable.telegram.api.UrlHelper;
import org.artfable.telegram.api.request.TelegramRequest;
import org.artfable.telegram.api.TelegramResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author artfable
 * 25.01.17
 */
public class TelegramSenderImpl implements TelegramSender {

    private static final Logger log = LoggerFactory.getLogger(TelegramSenderImpl.class);

    private RestTemplate restTemplate;
    private String token;

    private final Set<Long> updateIds = Collections.newSetFromMap(new WeakHashMap<>());

    public TelegramSenderImpl(RestTemplate restTemplate, String token) {
        this.restTemplate = restTemplate;
        this.token = token;
    }

    @Override
    public <T> T executeMethod(TelegramRequest<T> telegramRequest) {
        log.debug("Sending request " + telegramRequest.getId() + " " + telegramRequest);

        TelegramResponse<T> response = send(telegramRequest);

        log.debug("Get response (request " + telegramRequest.getId() + ") " + response);

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
    public <T> T singleExecuteMethod(Long forUpdate, TelegramRequest<T> telegramRequest) {
        if (!updateIds.contains(forUpdate)) {
            synchronized (updateIds) {
                if (updateIds.add(forUpdate)) {
                    log.trace("Used update ids in memory: " + updateIds.size());
                    return executeMethod(telegramRequest);
                }
            }
        }
        return null;
    }

    private <T> TelegramResponse<T> send(TelegramRequest<T> telegramRequest) {
        try {
            return restTemplate
                    .exchange(UrlHelper.getUri(URL, getUrlParams(telegramRequest.getMethod())), HttpMethod.POST, telegramRequest.asEntity(), telegramRequest.getResponseType())
                    .getBody();
        } catch (Exception e) {
            throw new TelegramServerException("Can't execute request", e);
        }
    }

    private Map<String, String> getUrlParams(TelegramBotMethod method) {
        return Map.of(
                "token", token,
                "method", method.getValue()
        );
    }
}
