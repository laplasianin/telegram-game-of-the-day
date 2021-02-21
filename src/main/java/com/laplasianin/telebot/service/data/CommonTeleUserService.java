package com.laplasianin.telebot.service.data;

import com.laplasianin.telebot.db.TeleUser;
import com.laplasianin.telebot.repositories.TeleUserRepository;
import com.pengrad.telegrambot.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CommonTeleUserService {

    @Inject TeleUserRepository teleUserRepository;

    public TeleUser createOrUpdateUser(User from) {
        final Optional<TeleUser> userDB = teleUserRepository.findById(from.id());

        if (userDB.isPresent()) {
            return userDB.get();
        }

        final TeleUser user = new TeleUser();

        user.setId(from.id());
        user.setFirstName(from.firstName());
        user.setLastName(from.lastName());
        user.setUsername(from.username());
        user.setBot(from.isBot());

        return teleUserRepository.save(user);
    }

}
