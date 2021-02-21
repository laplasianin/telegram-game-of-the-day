package com.laplasianin.telebot.db;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TelePidorGame {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    TeleChat chat;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tele_pidor_game_users",
            joinColumns = { @JoinColumn(name = "tele_pidor_game_id") },
            inverseJoinColumns = { @JoinColumn(name = "users_id") }
    )
    List<TeleUser> users = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TeleChat getChat() {
        return chat;
    }

    public void setChat(TeleChat chat) {
        this.chat = chat;
    }

    public List<TeleUser> getUsers() {
        return users;
    }

    public void setUsers(List<TeleUser> users) {
        this.users = users;
    }
}
