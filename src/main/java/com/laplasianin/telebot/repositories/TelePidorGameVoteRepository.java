package com.laplasianin.telebot.repositories;

import com.laplasianin.telebot.db.TelePidorGame;
import com.laplasianin.telebot.db.TelePidorGameVote;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelePidorGameVoteRepository extends CrudRepository<TelePidorGameVote, Integer> {

    Optional<TelePidorGameVote> findFirstByTelePidorGameOrderByVoteDateDesc(TelePidorGame telePidorGame);

    List<TelePidorGameVote> findAllByTelePidorGame(TelePidorGame telePidorGame);

}
