package com.laplasianin.telebot.service.actions;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.model.TelegramObject;
import com.laplasianin.telebot.service.messages.MessagesServices;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.laplasianin.telebot.utils.RandomUtils.getJavaRandom;

@Singleton
public class MeolishAction implements BotAction {

    @Inject MessagesServices messagesServices;

    @Override
    public TeleResult action(Optional<TelegramObject> a) {
        final TelegramObject telegramObject = a.get();

        final TeleResult teleResult = new TeleResult();
        teleResult.setTelegramObject(telegramObject);

        List<String> answers = Arrays.asList(messagesServices.getMessage("meolish.answers").split(","));

        String text = getJavaRandom(answers);
        teleResult.getMessages().add(text);
        return teleResult;
    }

    @Override
    public boolean test(Optional<TelegramObject> telegramObject) {
        return telegramObject.filter(a -> {
            final String command = a.getCommand();
            return (command != null && !command.startsWith("/")
                    && command.contains(messagesServices.getMessage("meolish.word")))
                    ;
        }).isPresent();
    }
}
