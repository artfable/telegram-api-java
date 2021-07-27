package com.artfable.telegram.api;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author artfable
 * 22.01.17
 */
abstract class AbstractTelegramBot {

    private static final Logger log = LoggerFactory.getLogger(AbstractTelegramBot.class);

    private boolean skipFailed;
    private Set<Behaviour> behaviours;
    private Set<CallbackBehaviour> callbackBehaviours;

    /**
     * {@link #skipFailed} true by default
     */
    public AbstractTelegramBot(Set<Behaviour> behaviours, Set<CallbackBehaviour> callbackBehaviours) {
        this(behaviours, callbackBehaviours, true);
    }

    /**
     * @param behaviours
     * @param skipFailed - if true, will continue execution even if some of {@link #behaviours} trows an exception
     */
    public AbstractTelegramBot(Set<Behaviour> behaviours, Set<CallbackBehaviour> callbackBehaviours, boolean skipFailed) {
        this.behaviours = behaviours;
        this.callbackBehaviours = callbackBehaviours;
        this.skipFailed = skipFailed;
    }

    protected void parse(List<Update> updates) {
        try {
            List<Update> nonProcessedUpdates = updates.parallelStream()
                    .filter(update -> callbackBehaviours.parallelStream().noneMatch(callbackBehaviour -> callbackBehaviour.parse(update)))
                    .collect(Collectors.toList());

            if (!nonProcessedUpdates.isEmpty()) {
                behaviours.parallelStream().forEach(behavior -> behavior.parse(nonProcessedUpdates));
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
