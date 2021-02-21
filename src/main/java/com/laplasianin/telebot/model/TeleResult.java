package com.laplasianin.telebot.model;

import com.pengrad.telegrambot.request.AbstractSendRequest;

import java.util.ArrayList;
import java.util.List;

public class TeleResult {

    private List<String> messages = new ArrayList<>();
    private Boolean canContinue = true;
    private TelegramObject telegramObject;
    private List<AbstractSendRequest> requests = new ArrayList<>();

    public TelegramObject getTelegramObject() {
        return telegramObject;
    }

    public boolean hasAnythingToProcess() {
        return telegramObject != null && (!requests.isEmpty() || !messages.isEmpty());
    }

    public void setTelegramObject(TelegramObject telegramObject) {
        this.telegramObject = telegramObject;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Boolean getCanContinue() {
        return canContinue;
    }

    public void setCanContinue(Boolean canContinue) {
        this.canContinue = canContinue;
    }

    public List<AbstractSendRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<AbstractSendRequest> requests) {
        this.requests = requests;
    }
}
