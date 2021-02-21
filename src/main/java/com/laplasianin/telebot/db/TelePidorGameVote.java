package com.laplasianin.telebot.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class TelePidorGameVote {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    TelePidorGame telePidorGame;

    @OneToOne
    TeleUser teleUser;

    LocalDateTime voteDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TelePidorGame getTelePidorGame() {
        return telePidorGame;
    }

    public void setTelePidorGame(TelePidorGame telePidorGame) {
        this.telePidorGame = telePidorGame;
    }

    public TeleUser getTeleUser() {
        return teleUser;
    }

    public void setTeleUser(TeleUser teleUser) {
        this.teleUser = teleUser;
    }

    public LocalDateTime getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDateTime voteDate) {
        this.voteDate = voteDate;
    }
}
