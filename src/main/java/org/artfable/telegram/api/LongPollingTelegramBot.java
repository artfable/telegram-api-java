package org.artfable.telegram.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.HttpClientErrorException;

import static org.artfable.telegram.api.service.TelegramSender.URL;

/**
 * Works with Telegram API through long polling. This implementation should be used also for bots that don't need to receive updates.
 * To start receiving updates call {@link #subscribeToUpdates(Long)}.
 *
 * @author aveselov
 * @since 03/08/2020
 */
public abstract class LongPollingTelegramBot extends AbstractTelegramBot {

    private static final Logger log = LoggerFactory.getLogger(LongPollingTelegramBot.class);

    @Autowired
    private TaskExecutor taskExecutor;

    public LongPollingTelegramBot(String token, Set<Behavior> behaviors) {
        super(token, behaviors);
    }

    public LongPollingTelegramBot(String token, Set<Behavior> behaviors, boolean skipFailed) {
        super(token, behaviors, skipFailed);
    }

    /**
     * This method starts subscription. Always in a separate thread.
     *
     * @param lastId - id of the last received update, null for start
     * @see org.springframework.scheduling.annotation.Async
     */
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
