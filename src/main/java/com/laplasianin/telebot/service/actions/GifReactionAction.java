package com.laplasianin.telebot.service.actions;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.model.TelegramObject;
import com.laplasianin.telebot.service.gif.GifService;
import com.laplasianin.telebot.service.messages.MessagesServices;
import com.pengrad.telegrambot.request.SendAnimation;
import com.pengrad.telegrambot.request.SendMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class GifReactionAction implements BotAction {

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
                        gifService.getRandomGif(messagesServices.getMessage("gifs.gooses")))
                        .replyToMessageId(telegramObject.getMessage().messageId()));

        teleResult.getRequests().add(
                new SendMessage(
                        telegramObject.getChat().id(),
                        messagesServices.getMessage("gifs.forbidden")));

        return teleResult;
    }

    @Override
    public boolean test(Optional<TelegramObject> telegramObject) {
        return telegramObject.filter(a -> a.getMessage() != null && a.getMessage().animation() != null).isPresent();
    }
}
