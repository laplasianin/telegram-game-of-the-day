package com.laplasianin.telebot.service.actions;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.model.TelegramObject;
import com.laplasianin.telebot.service.messages.MessagesServices;
import com.laplasianin.telebot.utils.RandomUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.laplasianin.telebot.utils.TextsUtils.containsAny;

@Singleton
public class GoosiAction implements BotAction {

    @Inject MessagesServices messagesServices;

    @Override
    public TeleResult action(Optional<TelegramObject> a) {
        final TelegramObject telegramObject = a.get();

        final TeleResult teleResult = new TeleResult();
        teleResult.setTelegramObject(telegramObject);

        teleResult.getMessages().add(messagesServices.getMessage("goose.answer.1"));
        teleResult.getMessages().add(messagesServices.getMessage("goose.answer.2"));
        teleResult.getMessages().add(messagesServices.getMessage("goose.answer.3"));
        teleResult.getMessages().add(messagesServices.getMessage("goose.answer.4"));

        final List<String> randomAnswer = Arrays.asList(messagesServices.getMessage("goose.answers").split("_"));

        teleResult.getMessages().add("Output: " + RandomUtils.getJavaRandom(randomAnswer) + "");

        return teleResult;
    }

    @Override
    public boolean test(Optional<TelegramObject> telegramObject) {
        return telegramObject.filter(a -> {
            final String command = a.getCommand();
            return command != null && !command.startsWith("/")
                    && (command.split(" ").length > 1) && (command.length() > 4)
                    && containsAny(command, Arrays.asList(messagesServices.getMessage("goose.lost").split(",")))
                    ;
        }).isPresent();
    }
}
