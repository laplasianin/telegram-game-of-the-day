package com.laplasianin.telebot.repositories;

import com.laplasianin.telebot.db.TelePidorEvent;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface TelePidorEventsRepository extends CrudRepository<TelePidorEvent, Long> {
}
