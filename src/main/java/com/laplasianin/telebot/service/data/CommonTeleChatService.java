package com.laplasianin.telebot.service.data;

import com.laplasianin.telebot.db.TeleChat;

import com.pengrad.telegrambot.model.Chat;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CommonTeleChatService {

    @Inject com.laplasianin.telebot.repositories.TeleChatRepository teleChatRepository;

    public TeleChat createOrUpdateChat(Chat from) {
        final Optional<TeleChat> userDB = teleChatRepository.findById(from.id());

        if (userDB.isPresent()) {
            return userDB.get();
        }

        final TeleChat teleChat = new TeleChat();
        teleChat.setId(from.id());
        teleChat.setType(from.type());
        teleChat.setTitle(from.title());
        teleChat.setFirstName(from.firstName());
        teleChat.setLastName(from.lastName());

        return teleChatRepository.save(teleChat);
    }

}
