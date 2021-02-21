package com.laplasianin.telebot.utils;

import com.laplasianin.telebot.model.TeleResult;
import com.pengrad.telegrambot.TelegramBot;

import static com.laplasianin.telebot.utils.BotMessagingUtils.sendMessages;

public class TeleResultUtils {

    public static void sendToBot(TelegramBot bot, TeleResult r) {
        sendMessages(bot, r, r.getTelegramObject().getChat().id());
        r.getRequests().forEach(bot::execute);
    }
}
