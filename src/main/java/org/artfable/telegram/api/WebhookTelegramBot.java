package org.artfable.telegram.api;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.artfable.telegram.api.request.DeleteWebhookRequest;
import org.artfable.telegram.api.request.SetWebhookRequest;
import org.artfable.telegram.api.service.TelegramSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Works with Telegram API through webhooks. Start and stop webhook automatically.
 * For self-signed certificates, public key {@link #cert} should be provided (.pem).
 * URL for the webhook - full URL for context-path of the bot. "/" should be at the end of the URL to avoid 302 response.
 * Always send {@link List} with a single update to {@link Behaviour}s
 *
 * @author aveselov
 * @since 02/08/2020
 */
public class WebhookTelegramBot extends AbstractTelegramBot {

    private static final Logger log = LoggerFactory.getLogger(WebhookTelegramBot.class);

    private String url;
    private InputStream cert;

    private TelegramSender telegramSender;

    /**
     * @param url - full url for the webhook
     * @see AbstractTelegramBot#AbstractTelegramBot(Set, Set)
     */
    public WebhookTelegramBot(TelegramSender telegramSender, String url, Set<Behaviour> behaviours, Set<CallbackBehaviour> callbackBehaviours) {
        this(telegramSender, url, null, behaviours, callbackBehaviours);
    }

    /**
     * @param url  - full url for the webhook
     * @param cert - public key (.pem) for the self-signed certificate of a server with the webhook
     * @see AbstractTelegramBot#AbstractTelegramBot(Set, Set)
     */
    public WebhookTelegramBot(TelegramSender telegramSender, String url, InputStream cert, Set<Behaviour> behaviours, Set<CallbackBehaviour> callbackBehaviours) {
        super(behaviours, callbackBehaviours);
        this.telegramSender = telegramSender;
        this.url = url;
        this.cert = cert;
    }

    /**
     * @param url  - full url for the webhook
     * @param cert - public key (.pem) for the self-signed certificate of a server with the webhook
     * @see AbstractTelegramBot#AbstractTelegramBot(Set, Set, boolean)
     */
    public WebhookTelegramBot(TelegramSender telegramSender, String url, InputStream cert, Set<Behaviour> behaviours, Set<CallbackBehaviour> callbackBehaviours, boolean skipFailed) {
        super(behaviours, callbackBehaviours, skipFailed);
        this.telegramSender = telegramSender;
        this.url = url;
        this.cert = cert;
    }

    @PostConstruct
    public void setWebhook() {
        telegramSender.executeMethod(new SetWebhookRequest(url, cert, null));
    }

    @PreDestroy
    public void removeWebhook() {
        telegramSender.executeMethod(new DeleteWebhookRequest());
    }

    public void getUpdate(Update update) {
        log.debug("Update received: " + update.getUpdateId());
        parse(List.of(update));
    }
}
