package org.artfable.telegram.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.artfable.telegram.api.BotCommand;
import org.artfable.telegram.api.request.GetMyCommandsRequest;
import org.artfable.telegram.api.request.SetMyCommandsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author aveselov
 * @since 25/08/2020
 */
@Service
@ConditionalOnProperty("telegram.bot.commands")
public class SetCommandsSetup {

    private static final Logger log = LoggerFactory.getLogger(SetCommandsSetup.class);

    private Resource resource;
    private ObjectMapper objectMapper;
    private TelegramSender telegramSender;

    @Autowired
    public SetCommandsSetup(@Value("${telegram.bot.commands}") Resource resource,
                            ObjectMapper objectMapper,
                            TelegramSender telegramSender) {
        this.resource = resource;
        this.objectMapper = objectMapper;
        this.telegramSender = telegramSender;
    }

    @PostConstruct
    public void start() {
        try (InputStream commandsInputStream = resource.getInputStream()) {
            updateCommands(objectMapper.readValue(commandsInputStream, new TypeReference<List<BotCommand>>() {}));
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
