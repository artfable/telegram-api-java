package org.artfable.telegram.api;

import org.artfable.telegram.api.service.TelegramSender;

/**
 * Main class for behaviour that is used by {@link AbstractTelegramBot}
 *
 * @author artfable
 *         22.01.17
 */
public abstract class AbstractBehavior implements Behavior {

    private boolean subscribed;
    private TelegramSender telegramSender;

    protected AbstractBehavior(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public boolean isSubscribed() {
        return subscribed;
    }

    @Override
    public void init(TelegramSender telegramSender) {
        this.telegramSender = telegramSender;
        start();
    }


    protected TelegramSender getTelegramSender() {
        return telegramSender;
    }

}
