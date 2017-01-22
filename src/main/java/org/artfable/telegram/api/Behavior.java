package org.artfable.telegram.api;

import java.util.List;

/**
 * @author artfable
 *         22.01.17
 */
public interface Behavior {
    void parse(List<Update> updates);

    boolean isSubscribed();
}
