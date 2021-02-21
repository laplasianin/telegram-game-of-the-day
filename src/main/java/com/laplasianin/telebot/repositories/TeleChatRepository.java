package com.laplasianin.telebot.repositories;

import com.laplasianin.telebot.db.TeleChat;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface TeleChatRepository extends CrudRepository<TeleChat, Long> {
}
