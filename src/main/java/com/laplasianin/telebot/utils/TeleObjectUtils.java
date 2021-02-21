package com.laplasianin.telebot.utils;

import com.laplasianin.telebot.model.TelegramObject;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.util.Optional;
import java.util.function.Function;

public class TeleObjectUtils {

    public static Function<Update, Optional<TelegramObject>> toTeleObject() {
        return update -> {
            final Optional<Message> message = Optional.ofNullable(update.message());

            Optional<TelegramObject> telegramObject = buildTeleObject(message);
            if (!telegramObject.isPresent()) {
                telegramObject = buildTeleObjectFromCallback(Optional.ofNullable(update.callbackQuery()));
            }

            return telegramObject.map(prepareCommandValue());
        };
    }

    private static Function<TelegramObject, TelegramObject> prepareCommandValue() {
        return to -> {
            if (to.getCommand() != null) {
                to.setCommand(to.getCommand().trim().toLowerCase().split("@")[0].trim());
            }
            return to;
        };
    }

    private static Optional<TelegramObject> buildTeleObject(Optional<Message> message) {
        return message.map(a -> {
            final TelegramObject telegramObject = new TelegramObject();
            telegramObject.setChat(a.chat());
            telegramObject.setFrom(a.from());
            telegramObject.setMessage(a);
            telegramObject.setCommand(a.text());
            return telegramObject;
        });
    }

    private static Optional<TelegramObject> buildTeleObjectFromCallback(Optional<CallbackQuery> message) {
        return message.map(a -> {
            final TelegramObject telegramObject = new TelegramObject();
            telegramObject.setChat(a.message().chat());
            telegramObject.setFrom(a.from());
            telegramObject.setMessage(a.message());
            telegramObject.setCommand(a.data());
            return telegramObject;
        });
    }

}
