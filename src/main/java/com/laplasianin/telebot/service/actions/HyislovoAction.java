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
import java.util.Random;

import static com.laplasianin.telebot.utils.TextsUtils.containsAny;

@Singleton
public class HyislovoAction implements BotAction {

    @Inject MessagesServices messagesServices;
    @Inject RandomUtils randomUtils;

    @Override
    public TeleResult action(Optional<TelegramObject> a) {
        final TelegramObject telegramObject = a.get();

        final TeleResult teleResult = new TeleResult();
        teleResult.setTelegramObject(telegramObject);

        String answer = null;

        if (new Random().nextBoolean()) {
            answer = randomUtils.getHui(telegramObject.getCommand().trim().substring(2, 3));
            if (answer != null) {
                answer += telegramObject.getCommand().trim().substring(2);
            }
        } else {
            List<String> strings = Arrays.asList(messagesServices.getMessage("hui.answers").split(","));
            answer = telegramObject.getCommand().trim() + " " + RandomUtils.getJavaRandom(strings);
        }

        if (answer != null) {
            teleResult.getMessages().add(answer);
        }

        return teleResult;
    }

    @Override
    public boolean test(Optional<TelegramObject> telegramObject) {
        return telegramObject.filter(a -> {
            final String command = a.getCommand();
            return (RandomUtils.getJavaRandom(9) < 3)
                    && command != null && !command.startsWith("/")
                    && (command.split(" ").length == 1) && (command.length() > 4)
                    && !containsAny(command, Arrays.asList(messagesServices.getMessage("lol.words").split(",")))
                    ;
        }).isPresent();
    }
}
