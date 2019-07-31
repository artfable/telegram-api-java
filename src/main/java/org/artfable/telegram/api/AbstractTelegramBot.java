package org.artfable.telegram.api;

import org.artfable.telegram.api.service.TelegramSender;
import org.artfable.telegram.api.service.TelegramSenderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
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
    private boolean skipFailed;
    private TelegramSender telegramSender;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskExecutor taskExecutor;

    public AbstractTelegramBot(String token, Set<Behavior> behaviors) {
        this(token, behaviors, false);
    }

    public AbstractTelegramBot(String token, Set<Behavior> behaviors, boolean skipFailed) {
        this.token = token;
        this.behaviors = behaviors;
        this.skipFailed = skipFailed;
    }

    @PostConstruct
    private void init() {
        this.telegramSender = new TelegramSenderImpl(restTemplate, token);
        this.behaviors.forEach(behavior -> behavior.init(telegramSender));
    }

    @Override
    @Async
    public void subscribeToUpdates(Long lastId) {
        Map<String, String> urlParams = new HashMap<>(4);
        urlParams.put("token", token);
        urlParams.put("method", TelegramBotMethod.GET_UPDATES.getValue());

        Map<String, Object> queryParams = new HashMap<>(4);
        queryParams.put("timeout", "100");
        if (lastId != null) {
            queryParams.put("offset", lastId + 1);
        }

        try {
            TelegramUpdateResponse response = restTemplate.getForObject(UrlHelper.getUri(URL, urlParams, queryParams), TelegramUpdateResponse.class);
            log.debug("Correct response: " + response.getOk());

            List<Update> result = response.getResult();
            Long updateId = result.isEmpty() ? null : result.get(result.size() - 1).getUpdateId();

            try {
                behaviors.parallelStream().filter(Behavior::isSubscribed).forEach(behavior -> behavior.parse(result));
            } catch (HttpClientErrorException e) {
                if (skipFailed) {
                    log.error("Can't parse updates", e);
                    taskExecutor.execute(() -> this.subscribeToUpdates(updateId));
                } else {
                    throw e;
                }
            }

            taskExecutor.execute(() -> this.subscribeToUpdates(updateId));
        } catch (HttpClientErrorException e) {
            log.error("Can't parse updates", e);
        }
    }
}
