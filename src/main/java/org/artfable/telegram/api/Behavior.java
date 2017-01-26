package org.artfable.telegram.api;

import org.artfable.telegram.api.service.TelegramSender;

import java.util.List;

/**
 * @author artfable
 *         22.01.17
 */
public interface Behavior {

    void init(TelegramSender telegramSender);

    void parse(List<Update> updates);

    boolean isSubscribed();
}
