package com.laplasianin.telebot.utils;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.service.game.VoteService;
import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.laplasianin.telebot.utils.HumanEmulationUtils.sendMessageLikeHuman;

public class BotMessagingUtils {

    private static final Logger log = LoggerFactory.getLogger(BotMessagingUtils.class);

    public static void sendMessages(TelegramBot bot, TeleResult result, Long finalChatId) {
        log.info("sendMessages");

        result.getMessages().forEach(msg -> {

            final List<String> splittedMessages = Arrays.asList(msg.split(VoteService.PIDOR_END_LINE));

            splittedMessages.forEach(chunk -> sendMessageLikeHuman(bot, finalChatId, chunk));

        });
    }

}
