package org.artfable.telegram.api;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author artfable
 *         22.01.17
 */
public abstract class AbstractBehavior implements Behavior {

    private String token;
    private boolean subscribed;
    private RestTemplate restTemplate;

    protected AbstractBehavior(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public boolean isSubscribed() {
        return subscribed;
    }

    @Override
    public void init(String token, RestTemplate restTemplate) {
        this.token = token;
        this.restTemplate = restTemplate;
    }

    protected TelegramSendResponse send(HttpMethod httpMethod, TelegramBotMethod method, Map<String, Object> queryParams) {
        Map<String, String> urlParams = new HashMap<>(4);
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
}
