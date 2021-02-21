package com.laplasianin.telebot.service.messages;

import io.micronaut.context.MessageSource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Singleton
public class MessagesServices {

    @Inject MessageSource messageSource;

    public String getMessage(String code, String... args) {

        Map<String, Object> variables = new HashMap<>();
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                variables.put(String.valueOf(i), args[i]);
            }
        }
        return messageSource.getMessage(code, MessageSource.MessageContext.of(getLocale(), variables), code);
    }

    public Locale getLocale() {
        return new Locale("ru", "RU");
    }

}
