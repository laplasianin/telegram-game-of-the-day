package com.laplasianin.telebot.service.game;

import com.laplasianin.telebot.db.TelePidorEvent;
import com.laplasianin.telebot.db.TelePidorGame;
import com.laplasianin.telebot.db.TelePidorGameVote;
import com.laplasianin.telebot.db.TeleUser;
import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.repositories.TelePidorGameVoteRepository;
import com.laplasianin.telebot.service.messages.MessagesServices;
import com.laplasianin.telebot.utils.RandomUtils;
import com.laplasianin.telebot.utils.TextsUtils;
import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class VoteService {

    public static final String PIDOR = "/pidor/";
    public static final String PIDOR_END_LINE = "/end/";
    public static final String PIDOR_END_PIDOR_POSTS = "END_OF_LINE";

    @Inject TelePidorGameVoteRepository telePidorGameVoteRepository;
    @Inject GameEventService gameEventService;
    @Inject StatService statService;
    @Inject MessageSource messageSource;
    @Inject MessagesServices messagesServices;
    @Inject RandomUtils randomUtils;

    @Value("${pidor.rules.cooldown.minutes}")
    public String voteCoolDown;

    @Value("${pidor.rules.min.people}")
    public String treshold;

    private static final Logger log = LoggerFactory.getLogger(VoteService.class);

    private List<String> getTexts() {
        log.info("getTexts");

        final String s = TextsUtils.getTexts();

        return Arrays.asList(s.split(VoteService.PIDOR_END_PIDOR_POSTS));
    }

    public TeleResult vote(TelePidorGame game) {
        log.info("vote");
        TeleResult result = new TeleResult();

        int votes = 1;
        TelePidorEvent event;

        final Optional<TelePidorEvent> todaysEvent = gameEventService.findTodaysEvent();
        if (todaysEvent.isPresent()) {
            event = todaysEvent.get();
            votes = event.getVotes();
        }

        final List<TeleUser> users = game.getUsers();
        if (users.size() < Integer.parseInt(treshold)) {
            result.getMessages().add(messagesServices.getMessage("pidor.validation.few.people", treshold));
            result.setCanContinue(false);
            return result;
        }

        final Optional<TelePidorGameVote> prevGame = telePidorGameVoteRepository.findFirstByTelePidorGameOrderByVoteDateDesc(game);
        if (prevGame.isPresent()) {

            if (voteCoolDown.equals("d")) {

                if (!LocalDate.now().isAfter(LocalDate.from(prevGame.get().getVoteDate()))) {

                    result.getMessages().add(messagesServices.getMessage("pidor.validation.cooldown", prevGame.get().getTeleUser().getFullName()));
                    result.setCanContinue(false);
                    return result;
                }

            } else {

                final int munites = Integer.parseInt(voteCoolDown);
                final LocalDateTime next = prevGame.get().getVoteDate().plusMinutes(munites);
                final long until = LocalDateTime.now().until(next, ChronoUnit.MINUTES);
                if (munites > 0 && until > 0) {
                    result.getMessages().add(messagesServices.getMessage("pidor.validation.cooldown", prevGame.get().getTeleUser().getFullName()));
                    result.setCanContinue(false);
                    return result;
                }

            }
        }

        final TeleUser pidor = getRandom(users, result);

        for (int i = 0; i < votes; i++) {
            final TelePidorGameVote gameVote = new TelePidorGameVote();
            gameVote.setTeleUser(pidor);
            gameVote.setTelePidorGame(game);
            gameVote.setVoteDate(LocalDateTime.now());

            telePidorGameVoteRepository.save(gameVote);
            statService.updateStat(game, pidor);
        }

        final String randomPidorText = getRandom(getTexts(), result);

        result.getMessages().add(randomPidorText.replaceAll(PIDOR, pidor.getFullNameAndTag()));

        return result;
    }

    private <T> T getRandom(List<T> users, TeleResult result) {
        log.info("getRandom");
        return users.get(randomUtils.getRandomValue(Math.max(users.size() - 1, 0), result));
    }

}
