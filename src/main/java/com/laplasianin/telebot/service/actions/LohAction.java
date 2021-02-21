package com.laplasianin.telebot.service.actions;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.model.TelegramObject;
import com.laplasianin.telebot.service.messages.MessagesServices;
import com.laplasianin.telebot.utils.RandomUtils;
import com.pengrad.telegrambot.request.SendMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Optional;

import static com.laplasianin.telebot.utils.TextsUtils.containsAny;

@Singleton
public class LohAction implements BotAction {

    @Inject MessagesServices messagesServices;
    @Inject RandomUtils randomUtils;

    @Override
    public TeleResult action(Optional<TelegramObject> a) {
        final TelegramObject telegramObject = a.get();

        final TeleResult teleResult = new TeleResult();
        teleResult.setTelegramObject(telegramObject);

        final String loh = randomUtils.getLoh(telegramObject.getCommand());
        if (loh != null) {
            teleResult.getRequests().add(new SendMessage(telegramObject.getChat().id(), loh).replyToMessageId(telegramObject.getMessage().messageId()));
        }
        return teleResult;
    }

    @Override
    public boolean test(Optional<TelegramObject> telegramObject) {
        return telegramObject.filter(a -> {
            final String command = a.getCommand();
            return command != null && !command.startsWith("/") &&
                    (command.split(" ").length > 1) && (command.length() > 4)
                    && containsAny(command, Arrays.asList(messagesServices.getMessage("loh.word").split(",")));
        }).isPresent();
    }
}
