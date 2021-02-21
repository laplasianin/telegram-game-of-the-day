package com.laplasianin.telebot.service.actions;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.model.TelegramObject;

import java.util.Optional;

public interface BotAction {
    TeleResult action(Optional<TelegramObject> a);

    boolean test(Optional<TelegramObject> telegramObject);
}
