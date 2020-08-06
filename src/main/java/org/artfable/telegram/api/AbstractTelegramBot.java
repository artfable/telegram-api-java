package org.artfable.telegram.api;

import java.util.Set;

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

    boolean skipFailed;
    Set<Behavior> behaviors;

    String token;
    TelegramSender telegramSender;

    @Autowired
    @Qualifier("telegramBotRestTemplate")
    RestTemplate restTemplate;

    public AbstractTelegramBot(String token, Set<Behavior> behaviors) {
        this(token, behaviors, true);
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
}
