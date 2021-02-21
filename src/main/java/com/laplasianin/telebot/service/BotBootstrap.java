package com.laplasianin.telebot.service;

import com.laplasianin.telebot.utils.LogToOwnerService;
import com.pengrad.telegrambot.TelegramBot;
import io.micronaut.context.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.MessageFormat;

@Singleton
public class BotBootstrap {

    private static final Logger log = LoggerFactory.getLogger(BotBootstrap.class);

    @Inject BotListener botListener;
    @Inject LogToOwnerService logToOwnerService;

    @Value("${bot.token}")
    public String botToken;

    public void init() {

        TelegramBot bot = new TelegramBot(botToken);
        logToOwnerService.logInit(bot, "Started");
        setListeners(bot);
    }

    private void setListeners(TelegramBot bot) {
        bot.setUpdatesListener(
                botListener.updateListenerHandler(bot),
                message -> {
                    logToOwnerService.logInit(bot, message.getMessage());
                    log.error(message.getLocalizedMessage(), message);
                    bot.removeGetUpdatesListener();
                    setListeners(bot);
                }
        );
    }

    @PostConstruct
    public void postConstruct() {
        log.info(MessageFormat.format("Initializing APP from listener with bot token: {0}", botToken));
    }

}
