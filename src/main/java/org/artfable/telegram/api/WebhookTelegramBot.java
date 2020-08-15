package org.artfable.telegram.api;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.artfable.telegram.api.request.DeleteWebhookRequest;
import org.artfable.telegram.api.request.SetWebhookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Works with Telegram API through webhooks. Start and stop webhook automatically.
 * For self-signed certificates, public key {@link #cert} should be provided (.pem).
 * URL for the webhook - full URL for context-path of the bot. "/" should be at the end of the URL to avoid 302 response.
 * Always send {@link List} with a single update to {@link Behavior}s
 *
 * @author aveselov
 * @since 02/08/2020
 */
@RestController
public abstract class WebhookTelegramBot extends AbstractTelegramBot {

    private static final Logger log = LoggerFactory.getLogger(WebhookTelegramBot.class);

    private String url;
    private Resource cert;

    /**
     * @param url - full url for the webhook
     *
     * @see AbstractTelegramBot#AbstractTelegramBot(String, Set, Set)
     */
    public WebhookTelegramBot(String token, String url, Set<Behavior> behaviors, Set<CallbackBehaviour> callbackBehaviours) {
        this(token, url, null, behaviors, callbackBehaviours);
    }

    /**
     * @param url - full url for the webhook
     * @param cert - public key (.pem) for the self-signed certificate of a server with the webhook
     *
     * @see AbstractTelegramBot#AbstractTelegramBot(String, Set, Set)
     */
    public WebhookTelegramBot(String token, String url, Resource cert, Set<Behavior> behaviors, Set<CallbackBehaviour> callbackBehaviours) {
        super(token, behaviors, callbackBehaviours);
        this.url = url;
        this.cert = cert;
    }

    /**
     * @param url - full url for the webhook
     * @param cert - public key (.pem) for the self-signed certificate of a server with the webhook
     *
     * @see AbstractTelegramBot#AbstractTelegramBot(String, Set, Set, boolean)
     */
    public WebhookTelegramBot(String token, String url, Resource cert, Set<Behavior> behaviors, Set<CallbackBehaviour> callbackBehaviours, boolean skipFailed) {
        super(token, behaviors, callbackBehaviours, skipFailed);
        this.url = url;
        this.cert = cert;
    }

    @PostConstruct
    private void setWebhook() {
        telegramSender.executeMethod(new SetWebhookRequest(url, cert, null));
    }

    @PreDestroy
    private void removeWebhook() {
        telegramSender.executeMethod(new DeleteWebhookRequest());
    }

    @PostMapping
    public ResponseEntity getUpdate(@RequestBody Update update) {
        log.debug("Update received: " + update.getUpdateId());
        parse(List.of(update));

        return ResponseEntity.ok().build();
    }
}
