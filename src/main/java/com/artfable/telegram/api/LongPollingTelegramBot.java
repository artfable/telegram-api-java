package com.artfable.telegram.api;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import com.artfable.telegram.api.request.GetUpdatesRequest;
import com.artfable.telegram.api.service.TelegramSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artfable.telegram.api.request.GetUpdatesRequest;

/**
 * Works with Telegram API through long polling. This implementation should be used also for bots that don't need to receive updates.
 * To start receiving updates call {@link #subscribeToUpdates(Long)}.
 *
 * @author aveselov
 * @since 03/08/2020
 */
public class LongPollingTelegramBot extends AbstractTelegramBot {

    private static final Logger log = LoggerFactory.getLogger(LongPollingTelegramBot.class);

    private Executor taskExecutor;

    private TelegramSender telegramSender;

    /**
     * @see AbstractTelegramBot#AbstractTelegramBot(Set, Set)
     */
    public LongPollingTelegramBot(Executor taskExecutor, TelegramSender telegramSender, Set<Behaviour> behaviours, Set<CallbackBehaviour> callbackBehaviours) {
        super(behaviours, callbackBehaviours);
        this.taskExecutor = taskExecutor;
        this.telegramSender = telegramSender;
    }

    /**
     * @see AbstractTelegramBot#AbstractTelegramBot(Set, Set, boolean)
     */
    public LongPollingTelegramBot(Executor taskExecutor, TelegramSender telegramSender, Set<Behaviour> behaviours, Set<CallbackBehaviour> callbackBehaviours, boolean skipFailed) {
        super(behaviours, callbackBehaviours, skipFailed);
        this.taskExecutor = taskExecutor;
        this.telegramSender = telegramSender;
    }

    @PostConstruct
    public void init() {
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

        parse(result);

        taskExecutor.execute(() -> this.subscribeToUpdates(updateId));
    }
}
