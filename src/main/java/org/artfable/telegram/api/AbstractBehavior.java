package org.artfable.telegram.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author artfable
 *         22.01.17
 */
public abstract class AbstractBehavior implements Behavior {
    @Autowired
    protected RestTemplate restTemplate;

    private boolean subscribed;

    protected AbstractBehavior(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public boolean isSubscribed() {
        return subscribed;
    }
}
