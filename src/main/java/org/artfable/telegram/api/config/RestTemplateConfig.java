package org.artfable.telegram.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * Auto configuration that provide {@link RestTemplate} for a bot.
 * Will be enable by EnableAutoConfiguration with Spring Boot
 *
 * @author aveselov
 * @since 06/08/2020
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate telegramBotRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(telegramResponseErrorHandler());
        return restTemplate;
    }

    public ResponseErrorHandler telegramResponseErrorHandler() {
        return new DefaultResponseErrorHandler() {
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                return !statusCode.is2xxSuccessful() && !statusCode.is4xxClientError() && super.hasError(statusCode);
            }
        };
    }
}
