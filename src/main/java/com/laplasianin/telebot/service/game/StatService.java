package com.laplasianin.telebot.service.game;

import com.laplasianin.telebot.db.TelePidorEvent;
import com.laplasianin.telebot.db.TelePidorGame;
import com.laplasianin.telebot.db.TelePidorGameStatistic;
import com.laplasianin.telebot.db.TeleUser;
import com.laplasianin.telebot.repositories.TelePidorGameStatisticRepository;
import com.laplasianin.telebot.service.messages.MessagesServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Singleton
public class StatService {

    @Inject TelePidorGameStatisticRepository telePidorGameStatisticRepository;
    @Inject MessagesServices messagesServices;
    @Inject GameEventService gameEventService;

    private static final Logger log = LoggerFactory.getLogger(StatService.class);

    public String getStat(TelePidorGame game, Integer year) {
        log.info("getStat");

        if (year == null) {
            year = LocalDate.now().getYear();
        }

        final List<TelePidorGameStatistic> votes = telePidorGameStatisticRepository
                .findAllByTelePidorGameAndYearOrderByPidorCounterDesc(game, year);

        final String[] message = {messagesServices.getMessage("pidor.stats.for.year", year.toString())};

        for (int i = 0, votesSize = votes.size(); i < votesSize; i++) {
            TelePidorGameStatistic res = votes.get(i);
            message[0] = message[0] + (i + 1) + ". " + res.getTeleUser().getFullName() + " - " + res.getPidorCounter() + " раз(а) " + "\n";
        }

        if (votes.size() == 0) {
            message[0] = message[0] + messagesServices.getMessage("pidor.stats.no.games");
        }

        final Optional<TelePidorEvent> todaysEvent = gameEventService.findTodaysEvent();

        todaysEvent.ifPresent(
                telePidorEvent -> message[0] = message[0] + "\n\n" + telePidorEvent.getExplain()
        );

        return message[0];
    }

    public String getStatAll(TelePidorGame game) {
        log.info("getStatAll");

        final List<TelePidorGameStatistic> votes = telePidorGameStatisticRepository.findAllByTelePidorGameOrderByPidorCounterDesc(game);

        final HashMap<TeleUser, Integer> collect = new HashMap<>(votes.stream()
                .collect(groupingBy(
                        TelePidorGameStatistic::getTeleUser,
                        Collectors.summingInt(TelePidorGameStatistic::getPidorCounter))));

        final String[] message = {""};

        final int[] i = {1};
        List<Map<TeleUser, Integer>> l = new ArrayList<>();

        collect.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(x -> {
                    Map<TeleUser, Integer> tu = new HashMap<>();
                    tu.put(x.getKey(), x.getValue());
                    l.add(tu);
                });


        Collections.reverse(l);
        l.forEach(ll -> ll.forEach((a, v) ->
                {
                    message[0] = message[0] + (i[0]) + ". " + a.getFullName() + " - " + v + " раз(а) " + "\n";
                    i[0]++;
                }

        ));

        message[0] = messagesServices.getMessage("pidor.stats.total") + message[0];

        if (collect.size() == 0) {
            message[0] = message[0] + "\n" + messagesServices.getMessage("pidor.stats.no.games");
        }

        final Optional<TelePidorEvent> todaysEvent = gameEventService.findTodaysEvent();
        todaysEvent.ifPresent(telePidorEvent -> message[0] = message[0] + "\n\n" + telePidorEvent.getExplain());

        return message[0];
    }

    public TelePidorGameStatistic updateStat(TelePidorGame game, TeleUser pidor) {
        log.info("updateStat");

        final int year = LocalDate.now().getYear();

        final Optional<TelePidorGameStatistic> vote = telePidorGameStatisticRepository
                .findByTelePidorGameAndTeleUserAndYear(game, pidor, year);

        TelePidorGameStatistic stat;

        if (vote.isPresent()) {

            stat = vote.get();
            vote.get().setPidorCounter(vote.get().getPidorCounter() + 1);

        } else {

            stat = new TelePidorGameStatistic();

            stat.setPidorCounter(1);
            stat.setTelePidorGame(game);
            stat.setYear(year);
            stat.setTeleUser(pidor);

        }

        return telePidorGameStatisticRepository.save(stat);

    }

}
