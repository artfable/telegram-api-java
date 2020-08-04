package org.artfable.telegram.api;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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

    public WebhookTelegramBot(String token, String url, Set<Behavior> behaviors) {
        this(token, url, null, behaviors);
    }

    public WebhookTelegramBot(String token, String url, Resource cert, Set<Behavior> behaviors) {
        super(token, behaviors);
        this.url = url;
        this.cert = cert;
    }

    public WebhookTelegramBot(String token, String url, Resource cert, Set<Behavior> behaviors, boolean skipFailed) {
        super(token, behaviors, skipFailed);
        this.url = url;
        this.cert = cert;
    }

    @PostConstruct
    private void setWebhook() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("url", url);

        if (cert != null) {
            body.add("certificate", cert);
        }

        telegramSender.send(HttpMethod.POST, TelegramBotMethod.SET_WEBHOOK, new HttpEntity<MultiValueMap<String, Object>>(body, headers));
    }

    @PreDestroy
    private void removeWebhook() {
        telegramSender.send(HttpMethod.POST, TelegramBotMethod.DELETE_WEBHOOK, Collections.emptyMap());
    }

    @PostMapping
    public ResponseEntity getUpdate(@RequestBody Update update) {
        log.debug("Update received: " + update.getUpdateId());
        try {
            behaviors.parallelStream().filter(Behavior::isSubscribed).forEach(behavior -> behavior.parse(List.of(update)));
        } catch (HttpClientErrorException e) {
            if (skipFailed) {
                log.error("Can't parse updates", e);

                return ResponseEntity.ok().build();
            }

            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
