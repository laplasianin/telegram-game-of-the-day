package com.laplasianin.telebot.repositories;

import com.laplasianin.telebot.db.TeleUser;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface TeleUserRepository extends CrudRepository<TeleUser, Integer> {
}
