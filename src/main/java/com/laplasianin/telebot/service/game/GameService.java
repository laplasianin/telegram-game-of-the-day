package com.laplasianin.telebot.service.game;

import com.laplasianin.telebot.db.TeleChat;
import com.laplasianin.telebot.db.TelePidorGame;
import com.laplasianin.telebot.db.TeleUser;
import com.laplasianin.telebot.repositories.TelePidorGameRepository;
import com.laplasianin.telebot.service.messages.MessagesServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Optional;

@Singleton
public class GameService {

    @Inject TelePidorGameRepository telePidorGameRepository;
    @Inject MessagesServices messagesServices;

    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    public TelePidorGame createOrUpdateGame(TeleChat chat) {
        log.info("createOrUpdateGame");

        final Optional<TelePidorGame> telePidorGame = telePidorGameRepository.findByChat(chat);

        if (telePidorGame.isPresent()) {
            return telePidorGame.get();
        }

        final TelePidorGame game = new TelePidorGame();
                game.setChat(chat);

        return telePidorGameRepository.save(game);
    }

    public boolean join(TelePidorGame game, TeleUser user) {
        log.info("join");

        if (game.getUsers() == null) {
            game.setUsers(new ArrayList<>());
        }

        if (!game.getUsers().contains(user)) {
            game.getUsers().add(user);
            telePidorGameRepository.save(game);
            return true;
        }

        return false;
    }

}
