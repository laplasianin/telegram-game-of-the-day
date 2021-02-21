package com.laplasianin.telebot.service;

import com.laplasianin.telebot.db.TeleChat;
import com.laplasianin.telebot.db.TeleUser;
import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.service.messages.MessagesServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandRouter {

    public static final String GAME_CMD = "/pidor";
    public static final String JOIN_GAME_CMD = "/pidor_join";
    public static final String START_CMD = "/start";
    public static final String EVENT_CMD = "/pidor_event";
    public static final String HELP_CMD = "/help";
    public static final String STAT_CMD = "/pidor_stat";

    @Inject GamesFacade gamesFacade;
    @Inject MessagesServices messagesServices;

    private static final Logger log = LoggerFactory.getLogger(CommandRouter.class);

    public TeleResult route(TeleUser teleUser, TeleChat teleChat, String command) {
        String text = command.toLowerCase().split("@")[0].trim();

        TeleResult result = new TeleResult();

        log.info("Route: " + text);

        switch (text) {
            case GAME_CMD:

                result = gamesFacade.playGame(teleChat, teleUser);

                break;
            case JOIN_GAME_CMD:

                result = gamesFacade.join(teleChat, teleUser, true);

                break;
            case START_CMD:

                result.getMessages().add(messagesServices.getMessage("start"));

                break;
            case EVENT_CMD:

                gamesFacade.handlePidorEvent(result);

                break;
            case HELP_CMD:

                result.getMessages().add(messagesServices.getMessage("help"));

                break;
            case STAT_CMD:

                gamesFacade.sendPidorStatQuiz(result, teleChat);

                break;
            default:
                if (text.startsWith(STAT_CMD)) {
                    gamesFacade.statGame(result, teleChat, text);
                }
        }

        return result;
    }

}
