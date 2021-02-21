package com.laplasianin.telebot.repositories;

import com.laplasianin.telebot.db.TelePidorGame;
import com.laplasianin.telebot.db.TelePidorGameStatistic;
import com.laplasianin.telebot.db.TeleUser;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelePidorGameStatisticRepository extends CrudRepository<TelePidorGameStatistic, Integer> {

    List<TelePidorGameStatistic> findAllByTelePidorGameOrderByPidorCounterDesc(TelePidorGame telePidorGame);

    List<TelePidorGameStatistic> findAllByTelePidorGameAndYearOrderByPidorCounterDesc(TelePidorGame telePidorGame, Integer year);

    Optional<TelePidorGameStatistic> findByTelePidorGameAndTeleUserAndYear(TelePidorGame game, TeleUser user, Integer year);

}
