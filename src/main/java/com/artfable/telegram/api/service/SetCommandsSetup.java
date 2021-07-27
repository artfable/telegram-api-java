package com.artfable.telegram.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import javax.annotation.PostConstruct;

import com.artfable.telegram.api.BotCommand;
import com.artfable.telegram.api.request.GetMyCommandsRequest;
import com.artfable.telegram.api.request.SetMyCommandsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artfable.telegram.api.BotCommand;
import com.artfable.telegram.api.request.GetMyCommandsRequest;
import com.artfable.telegram.api.request.SetMyCommandsRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Send request to setup commands for the bot. Read config from {@link InputStream}
 *
 * @author aveselov
 * @since 25/08/2020
 */
public class SetCommandsSetup {

    private static final Logger log = LoggerFactory.getLogger(SetCommandsSetup.class);

    private InputStream resource;
    private ObjectMapper objectMapper;
    private TelegramSender telegramSender;

    public SetCommandsSetup(InputStream resource,
                            ObjectMapper objectMapper,
                            TelegramSender telegramSender) {
        this.resource = resource;
        this.objectMapper = objectMapper;
        this.telegramSender = telegramSender;
    }

    @PostConstruct
    public void start() {
        try (InputStream commandsInputStream = resource) {
            updateCommands(objectMapper.readValue(commandsInputStream, new TypeReference<List<BotCommand>>() {
            }));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void updateCommands(List<BotCommand> newCommands) {
        log.info("Checking commands for bot...");
        List<BotCommand> oldCommands = telegramSender.executeMethod(new GetMyCommandsRequest());

        if (!oldCommands.containsAll(newCommands) || !newCommands.containsAll(oldCommands)) {
            log.info("Updating commands for bot...");
            telegramSender.executeMethod(new SetMyCommandsRequest(newCommands));
        }
    }
}
