package com.laplasianin.telebot.service;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.service.actions.BotAction;
import com.laplasianin.telebot.service.actions.BotActionsFactory;
import com.laplasianin.telebot.utils.LogToOwnerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.laplasianin.telebot.utils.TeleObjectUtils.toTeleObject;
import static com.laplasianin.telebot.utils.TeleResultUtils.sendToBot;

@Singleton
public class BotListener {


    @Inject BotActionsFactory botActionsFactory;
    @Inject LogToOwnerService logToOwnerService;

    private static final Logger log = LoggerFactory.getLogger(BotListener.class);

    public UpdatesListener updateListenerHandler(TelegramBot bot) {
        return updates -> {
            try {

                botActionsFactory
                        .provideAvailableBotActions()
                        .parallelStream()
                        .map(a -> botAction(updates, a))
                        .filter(TeleResult::hasAnythingToProcess)
                        .forEach(r -> sendToBot(bot, r));

                log.info("CONFIRMED_UPDATES_ALL");
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
                logToOwnerService.logInit(bot, Arrays.toString(e.getStackTrace()));
            } finally {
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        };
    }

    public TeleResult botAction(List<Update> updates, BotAction action) {
        return updates.stream()
                .map(toTeleObject())
                .filter(Optional::isPresent)
                .filter(action::test)
                .map(action::action)
                .findFirst().orElseGet(TeleResult::new);
    }

}
