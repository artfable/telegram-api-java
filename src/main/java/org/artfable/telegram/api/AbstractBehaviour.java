package org.artfable.telegram.api;

import java.util.List;

import org.artfable.telegram.api.service.TelegramSender;

/**
 * Main class for behaviour that is used by {@link AbstractTelegramBot}
 *
 * @author artfable
 *         22.01.17
 */
public abstract class AbstractBehaviour implements Behaviour {

    private boolean subscribed;

    protected AbstractBehaviour(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public boolean isSubscribed() {
        return subscribed;
    }

    @Override
    public void parse(List<Update> updates) {
        updates.parallelStream().forEach(this::parse);
    }

    protected abstract void parse(Update update);

}
