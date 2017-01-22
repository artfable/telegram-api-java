package org.artfable.telegram.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author artfable
 *         22.01.17
 */
public abstract class AbstractTelegramBot implements TelegramBot {

    private static final Logger log = LoggerFactory.getLogger(AbstractTelegramBot.class);

    private String token;
    private Set<Behavior> behaviors;

    @Autowired
    private RestTemplate restTemplate;

    public AbstractTelegramBot(String token, Set<Behavior> behaviors) {
        this.token = token;
        this.behaviors = behaviors;
    }

    @Override
    @Async
    public void subscribeToUpdates(Integer lastId) {
        Map<String, String> urlParams = new HashMap<>(4);
        urlParams.put("token", token);
        urlParams.put("method", "getUpdates");

        Map<String, Object> queryParams = new HashMap<>(4);
        queryParams.put("timeout", "100");
        if (lastId != null) {
            queryParams.put("offset", lastId + 1);
        }

        try {
            TelegramResponse response = restTemplate.getForObject(getUri(URL, urlParams, queryParams), TelegramResponse.class);
            log.info("Correct response: " + response.getOk());

            List<Update> result = response.getResult();
            Integer updateId = result.isEmpty() ? null : result.get(result.size() - 1).getUpdateId();
            behaviors.parallelStream().filter(Behavior::isSubscribed).forEach(behavior -> behavior.parse(result));

            subscribeToUpdates(updateId);
        } catch (HttpClientErrorException e) {
            log.error("Can't get updates", e);
        }
    }

    private URI getUri(String url, Map<String, String> urlParams, Map<String, Object> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }

        return builder.buildAndExpand(urlParams).encode().toUri();
    }

    private URI getUri(String url, Map<String, String> urlParams) {
        return getUri(url, urlParams, null);
    }
}
