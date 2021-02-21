package com.laplasianin.telebot.utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class HumanEmulationUtils {

    private static final Logger log = LoggerFactory.getLogger(HumanEmulationUtils.class);

    public static void sendMessageLikeHuman(TelegramBot bot, Long finalChatId, String chunk) {
        log.info("sendMessageLikeHuman");

        bot.execute(new SendChatAction(finalChatId, ChatAction.typing));
        emulateHuman();
        bot.execute(new SendMessage(finalChatId, chunk));
    }

    public static void emulateHuman() {
        try {
            Thread.sleep(new Random().nextInt(2) * 1000);
        } catch (InterruptedException e) {
        }
    }
}
