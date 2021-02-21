package com.laplasianin.telebot.service.game;

import com.laplasianin.telebot.db.TelePidorEvent;
import com.laplasianin.telebot.repositories.TelePidorEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Singleton
public class GameEventService {

    @Inject TelePidorEventsRepository telePidorEventsRepository;

    private static final Logger log = LoggerFactory.getLogger(GameEventService.class);

    public Optional<TelePidorEvent> findTodaysEvent() {
        log.info("findTodaysEvent");

        return StreamSupport.stream(telePidorEventsRepository.findAll().spliterator(), false).filter(event -> {
            return (LocalDate.now().isAfter(event.getStartDate()) || LocalDate.now().isEqual(event.getStartDate())) &&
                    (LocalDate.now().isBefore(event.getEndDate()) || LocalDate.now().isEqual(event.getEndDate()));
        }).findFirst();
    }

}
