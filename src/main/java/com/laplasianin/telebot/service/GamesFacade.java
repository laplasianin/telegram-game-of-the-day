package com.laplasianin.telebot.service;

import com.laplasianin.telebot.db.TeleChat;
import com.laplasianin.telebot.db.TelePidorEvent;
import com.laplasianin.telebot.db.TelePidorGame;
import com.laplasianin.telebot.db.TeleUser;
import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.service.game.GameEventService;
import com.laplasianin.telebot.service.game.GameService;
import com.laplasianin.telebot.service.game.StatService;
import com.laplasianin.telebot.service.game.VoteService;
import com.laplasianin.telebot.service.messages.MessagesServices;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class GamesFacade {

    @Inject GameService gameService;
    @Inject StatService statService;
    @Inject VoteService voteService;
    @Inject GameEventService gameEventService;

    @Inject MessagesServices messagesServices;

    private static final Logger log = LoggerFactory.getLogger(GamesFacade.class);

    @Transactional
    public void sendPidorStatQuiz(TeleResult result, TeleChat teleChat) {
        log.info("sendPidorStatQuiz");

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buildStatByYearButtons(buttons);
        buttons.add(new InlineKeyboardButton("All time").callbackData("/pidor_stat_all"));
        buttons.add(new InlineKeyboardButton("Share Bot").switchInlineQuery(messagesServices.getMessage("pidor.helper")));

        final Optional<TelePidorEvent> todaysEvent = gameEventService.findTodaysEvent();

        todaysEvent.ifPresent(telePidorEvent ->
                buttons.add(new InlineKeyboardButton(telePidorEvent.getTitle()).callbackData("/pidor_event")));

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(buttons.toArray(new InlineKeyboardButton[0]));

        result.getRequests().add(
                new SendMessage(
                        teleChat.getId(),
                        messagesServices.getMessage("pidor.vote.question"))
                        .replyMarkup(inlineKeyboard));
    }

    private void buildStatByYearButtons(List<InlineKeyboardButton> buttons) {
        int nowYear = LocalDate.now().getYear();
        int startYear = 2019;
        while (startYear <= nowYear) {
            buttons.add(new InlineKeyboardButton(String.valueOf(startYear)).callbackData("/pidor_stat_" + startYear));
            startYear = startYear + 1;
        }
    }

    @Transactional
    public TeleResult statGame(TeleResult result, TeleChat teleChat, String cmd) {
        log.info("statGame");
        final TelePidorGame game = gameService.createOrUpdateGame(teleChat);

        final String[] words = cmd.trim().split("_");

        final String yearString = words[words.length - 1];

        String stats;
        int year;

        if (yearString.equals("all")) {
            stats = statService.getStatAll(game);
        } else {
            try {
                year = Integer.parseInt(yearString);
            } catch (NumberFormatException e) {
                year = LocalDate.now().getYear();
            }

            stats = statService.getStat(game, year);
        }

        result.getMessages().add(stats);

        return result;
    }

    @Transactional
    public void handlePidorEvent(TeleResult result) {
        log.info("handlePidorEvent");
        final Optional<TelePidorEvent> todaysEvent = gameEventService.findTodaysEvent();

        if (todaysEvent.isPresent()) {
            result.getMessages().add(todaysEvent.get().getExplain());
        } else {
            result.getMessages().add("Нет событий, ждите");
        }
    }

    @Transactional
    public TeleResult playGame(TeleChat chat, TeleUser from) {
        log.info("playGame");
        final TeleResult result = new TeleResult();

        final TelePidorGame game = gameService.createOrUpdateGame(chat);

        TeleResult joinResult = join(chat, from, false);
        TeleResult voteResult = voteService.vote(game);

        result.getMessages().addAll(joinResult.getMessages());
        result.getMessages().addAll(voteResult.getMessages());

        Optional<TelePidorEvent> todaysEvent = gameEventService.findTodaysEvent();
        todaysEvent
                .ifPresent(telePidorEvent -> result.getMessages().add(telePidorEvent.getExplain()));

        return result;
    }

    @Transactional
    public TeleResult join(TeleChat chat, TeleUser from, boolean fromJoinCommand) {
        log.info("join");
        final TelePidorGame game = gameService.createOrUpdateGame(chat);
        boolean joined = gameService.join(game, from);

        final TeleResult result = new TeleResult();

        if (joined) {
            result.getMessages().add(messagesServices.getMessage("pidor.joined", from.getFullNameAndTag()));
        } else {
            if (fromJoinCommand) {
                result.getMessages().add(messagesServices.getMessage("pidor.already.joined", from.getFullNameAndTag()));
            }
        }

        return result;
    }

}
