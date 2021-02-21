package com.laplasianin.telebot.utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
public class LogToOwnerService {

    @Value("${bot.self.chat.id}")
    public String selfChatId;

    public void logInit(TelegramBot bot, String message) {
        bot.execute(new SendMessage(selfChatId, message));
    }

}
