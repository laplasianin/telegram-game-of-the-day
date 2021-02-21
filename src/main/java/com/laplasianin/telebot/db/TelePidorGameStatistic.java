package com.laplasianin.telebot.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TelePidorGameStatistic {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    TelePidorGame telePidorGame;

    @OneToOne
    TeleUser teleUser;

    Integer year;

    Integer pidorCounter = 0;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPidorCounter() {
        return pidorCounter;
    }

    public void setPidorCounter(Integer pidorCounter) {
        this.pidorCounter = pidorCounter;
    }
}
