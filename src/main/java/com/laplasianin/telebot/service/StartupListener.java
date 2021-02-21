package com.laplasianin.telebot.service;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StartupListener implements ApplicationEventListener<ServerStartupEvent> {

    @Inject BotBootstrap botBootstrap;

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        botBootstrap.init();
    }
}