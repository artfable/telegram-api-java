package org.artfable.telegram.api;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.artfable.telegram.api.request.GetUpdatesRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Works with Telegram API through long polling. This implementation should be used also for bots that don't need to receive updates.
 * To start receiving updates call {@link #subscribeToUpdates(Long)}.
 *
 * @author aveselov
 * @since 03/08/2020
 */
@Service
public abstract class LongPollingTelegramBot extends AbstractTelegramBot {

    private static final Logger log = LoggerFactory.getLogger(LongPollingTelegramBot.class);

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * @see AbstractTelegramBot#AbstractTelegramBot(String, Set, Set)
     */
    public LongPollingTelegramBot(String token, Set<Behavior> behaviors, Set<CallbackBehaviour> callbackBehaviours) {
        super(token, behaviors, callbackBehaviours);
    }

    /**
     * @see AbstractTelegramBot#AbstractTelegramBot(String, Set, Set, boolean)
     */
    public LongPollingTelegramBot(String token, Set<Behavior> behaviors, Set<CallbackBehaviour> callbackBehaviours, boolean skipFailed) {
        super(token, behaviors, callbackBehaviours, skipFailed);
    }

    @PostConstruct
    private void init() {
        taskExecutor.execute(() -> subscribeToUpdates(null));
    }

    /**
     * This method starts subscription.
     *
     * @param lastId - id of the last received update, null for start
     */
    private void subscribeToUpdates(Long lastId) {
        List<Update> result = telegramSender.executeMethod(new GetUpdatesRequest(lastId != null ? lastId + 1 : null, 100, null, null));
        Long updateId = result.isEmpty() ? null : result.get(result.size() - 1).getUpdateId();

        parse(result, () -> taskExecutor.execute(() -> this.subscribeToUpdates(updateId)));

        taskExecutor.execute(() -> this.subscribeToUpdates(updateId));
    }
}
