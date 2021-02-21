package com.laplasianin.telebot.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class TeleUser {

    @Id
    private Integer id;

    @Column
    private boolean isBot;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    public String getFullNameAndTag() {
        String fullName = firstName  + " " + (lastName != null ? lastName : "");
        return (username != null ? ("@" + username + " ") : "" ) + fullName;
    }

    public String getFullName() {
        return firstName  + " " + (lastName != null ? lastName : "");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeleUser teleUser = (TeleUser) o;
        return Objects.equals(id, teleUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
