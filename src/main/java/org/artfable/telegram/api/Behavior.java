package org.artfable.telegram.api;

import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author artfable
 *         22.01.17
 */
public interface Behavior {

    void init(String token, RestTemplate restTemplate);

    void parse(List<Update> updates);

    boolean isSubscribed();
}
