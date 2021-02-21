package com.laplasianin.telebot.repositories;

import com.laplasianin.telebot.db.TeleChat;
import com.laplasianin.telebot.db.TelePidorGame;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface TelePidorGameRepository extends CrudRepository<TelePidorGame, Integer> {

    Optional<TelePidorGame> findByChat(TeleChat teleChat);

}
