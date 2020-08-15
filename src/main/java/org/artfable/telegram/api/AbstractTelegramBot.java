package org.artfable.telegram.api;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.artfable.telegram.api.service.TelegramSender;
import org.artfable.telegram.api.service.TelegramSenderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

/**
 * @author artfable
 *         22.01.17
 */
abstract class AbstractTelegramBot {

    private static final Logger log = LoggerFactory.getLogger(AbstractTelegramBot.class);

    private boolean skipFailed;
    private Set<Behavior> behaviors;
    private Set<CallbackBehaviour> callbackBehaviours;

    private String token;

    @Autowired
    @Qualifier("telegramBotRestTemplate")
    private RestTemplate restTemplate;

    TelegramSender telegramSender;

    /**
     * {@link #skipFailed} true by default
     */
    public AbstractTelegramBot(String token, Set<Behavior> behaviors, Set<CallbackBehaviour> callbackBehaviours) {
        this(token, behaviors, callbackBehaviours, true);
    }

    /**
     *
     * @param token
     * @param behaviors
     * @param skipFailed - if true, will continue execution even if some of {@link #behaviors} trows an exception
     */
    public AbstractTelegramBot(String token, Set<Behavior> behaviors, Set<CallbackBehaviour> callbackBehaviours, boolean skipFailed) {
        this.token = token;
        this.behaviors = behaviors;
        this.callbackBehaviours = callbackBehaviours;
        this.skipFailed = skipFailed;
    }

    @PostConstruct
    private void init() {
        this.telegramSender = new TelegramSenderImpl(restTemplate, token);
        this.behaviors.forEach(behavior -> behavior.init(telegramSender));
    }

    protected void parse(List<Update> updates) {
        try {
            List<Update> nonProcessedUpdates = updates.parallelStream()
                    .filter(update -> callbackBehaviours.parallelStream().noneMatch(callbackBehaviour -> callbackBehaviour.parse(update)))
                    .collect(Collectors.toList());

            if (!nonProcessedUpdates.isEmpty()) {
                behaviors.parallelStream().filter(Behavior::isSubscribed).forEach(behavior -> behavior.parse(nonProcessedUpdates));
            }
        } catch (Exception e) {
            if (skipFailed) {
                log.error("Can't parse updates", e);
            } else {
                throw new IllegalArgumentException("Can't parse updates", e);
            }
        }
    }
}
