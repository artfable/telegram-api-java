package org.artfable.telegram.api.service;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import org.artfable.telegram.api.TelegramRequestException;
import org.artfable.telegram.api.TelegramResponse;
import org.artfable.telegram.api.TelegramServerException;
import org.artfable.telegram.api.request.TelegramRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author artfable
 * 25.01.17
 */
public abstract class AbstractTelegramSender implements TelegramSender {

    private static final Logger log = LoggerFactory.getLogger(AbstractTelegramSender.class);

    private final Set<Long> updateIds = Collections.newSetFromMap(new WeakHashMap<>());

    @Override
    public <T> T executeMethod(TelegramRequest<T> telegramRequest) {
        log.debug("Sending request " + telegramRequest.getId() + " " + telegramRequest);

        TelegramResponse<T> response = send(telegramRequest);

        log.debug("Get response (request " + telegramRequest.getId() + ") " + response);

        if (response.getOk()) {
            return response.getResult();
        } else {
            if (response.getErrorCode() == null || response.getDescription() == null) {
                throw new TelegramServerException("Invalid response format for request " + telegramRequest.toString());
            }
            throw new TelegramRequestException(response.getErrorCode(), response.getDescription());
        }
    }

    @Override
    public <T> T singleExecuteMethod(Long forUpdate, TelegramRequest<T> telegramRequest) {
        if (!updateIds.contains(forUpdate)) {
            synchronized (updateIds) {
                if (updateIds.add(forUpdate)) {
                    log.trace("Used update ids in memory: " + updateIds.size());
                    return executeMethod(telegramRequest);
                }
            }
        }
        return null;
    }

    /**
     * Implementation for actual sending
     *
     * @param telegramRequest - request that should be send
     * @param <T> - return type
     * @return - {@link TelegramResponse} that specified by {@link TelegramRequest}
     */
    protected abstract <T> TelegramResponse<T> send(TelegramRequest<T> telegramRequest);
}
