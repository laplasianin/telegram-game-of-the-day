package com.laplasianin.telebot.service.actions;

import com.laplasianin.telebot.db.TeleChat;
import com.laplasianin.telebot.db.TeleUser;
import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.model.TelegramObject;
import com.laplasianin.telebot.service.CommandRouter;
import com.laplasianin.telebot.service.data.CommonTeleChatService;
import com.laplasianin.telebot.service.data.CommonTeleUserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class PidorDnyaAction implements BotAction {

    @Inject CommonTeleUserService commonTeleUserService;
    @Inject CommonTeleChatService commonTeleChatService;
    @Inject CommandRouter commandRouter;

    @Override
    public TeleResult action(Optional<TelegramObject> a) {
        final TelegramObject telegramObject = a.get();

        TeleUser teleUser = commonTeleUserService.createOrUpdateUser(telegramObject.getFrom());
        TeleChat teleChat = commonTeleChatService.createOrUpdateChat(telegramObject.getChat());

        TeleResult teleResult = commandRouter.route(teleUser, teleChat, telegramObject.getCommand());
        teleResult.setTelegramObject(telegramObject);

        return teleResult;
    }

    @Override
    public boolean test(Optional<TelegramObject> telegramObject) {
        return telegramObject.filter(a -> a.getCommand() != null && a.getCommand().startsWith("/")).isPresent();
    }
}
