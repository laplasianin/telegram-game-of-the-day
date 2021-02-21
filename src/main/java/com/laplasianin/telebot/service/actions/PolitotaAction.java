package com.laplasianin.telebot.service.actions;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.model.TelegramObject;
import com.laplasianin.telebot.service.gif.GifService;
import com.laplasianin.telebot.service.messages.MessagesServices;
import com.pengrad.telegrambot.request.SendAnimation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static com.laplasianin.telebot.utils.TextsUtils.containsAny;

@Singleton
public class PolitotaAction implements BotAction {

    @Inject GifService gifService;
    @Inject MessagesServices messagesServices;

    @Override
    public TeleResult action(Optional<TelegramObject> a) {
        final TelegramObject telegramObject = a.get();

        final TeleResult teleResult = new TeleResult();
        teleResult.setTelegramObject(telegramObject);

        teleResult.getRequests().add(
                new SendAnimation(
                        telegramObject.getChat().id(),
                        gifService.getRandomGif(new Random().nextBoolean() ?
                                messagesServices.getMessage("politota.word.1") :
                                messagesServices.getMessage("politota.word.2")))
                        .replyToMessageId(telegramObject.getMessage().messageId()));

        return teleResult;
    }

    @Override
    public boolean test(Optional<TelegramObject> telegramObject) {
        return telegramObject.filter(a -> {
            final String command = a.getCommand();
            return command != null && !command.startsWith("/")
                    && (command.split(" ").length > 1) && (command.length() > 4)
                    && containsAny(command, Arrays.asList(messagesServices.getMessage("politota.words").split(",")))
                    ;
        }).isPresent();
    }
}
