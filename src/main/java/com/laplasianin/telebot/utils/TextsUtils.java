package com.laplasianin.telebot.utils;

import java.util.List;

public class TextsUtils {

    public static boolean containsAny(String command, List<String> words) {
        return words.stream().map(String::toLowerCase).map(String::trim).anyMatch(command::contains);
    }

    public static boolean startsWithAny(String command, List<String> words) {
        return words.stream().map(String::toLowerCase).map(String::trim).anyMatch(command::startsWith);
    }

    public static String getTexts() {
        return "END_OF_LINE\n" +
                "Такс-такс-такс, что это тут у нас? /end/\n" +
                "Пидор дня! /end/\n" +
                "И это /pidor/\n" +
                "END_OF_LINE\n" +
                "Пидор дня, обыкновенный, одна штука: /pidor/\n" +
                "END_OF_LINE\n" +
                "Пи! Пара-па-па-па-па-пам /end/\n" +
                "Дор! Пара-па-па-па-па-пам /end/\n" +
                "Дня! Пара-па-па-па-па-пам /end/\n" +
                "ПИ-ДОР-ДНЯ-/pidor/\n" +
                "END_OF_LINE\n" +
                "Еб*нись, Торвальд, кажется мы нашли пидора дня! /pidor/\n" +
                "END_OF_LINE\n" +
                "Опять вы свои игрушки играете? А могли бы делом на работе заняться. /end/\n" +
                "Ну ладно.. /end/\n" +
                "Анализ завершён и этот прекрасный человек, а нет всего лишь пидор /pidor/\n" +
                "END_OF_LINE\n" +
                "Кто сегодня счастливчик? /end/\n" +
                "Где-же он... /end/\n" +
                "Что с нами стало... /end/\n" +
                "Анализ завершен. Ты пидор, /pidor/ /end/\n" +
                "END_OF_LINE\n" +
                "ЗАПУСКАЕМ/end/\n" +
                "░ГУСЯ░РАБОТЯГИ░/end/\n" +
                "Гусь - пидорднябусь - /pidor/\n" +
                "END_OF_LINE\n" +
                "Что тут у нас? /end/\n" +
                "(Ворчит) А могли бы на работе делом заниматься /end/\n" +
                "Так-так, что же тут у нас... /end/\n" +
                "Стоять! Не двигаться! Вы объявлены пидором дня, /pidor/\n" +
                "END_OF_LINE\n" +
                "Опять в эти ваши игрульки играете? Ну ладно... /end/\n" +
                "Военный спутник запущен, коды доступа внутри... /end/\n" +
                "Ого-го... /end/\n" +
                "Кажется, пидор дня - /pidor/\n" +
                "END_OF_LINE\n" +
                "Woop-woop! That's the sound of da pidor-police! /end/\n" +
                "Сканирую... /end/\n" +
                "Так, что тут у нас? /end/\n" +
                "Что? Где? Когда? А ты пидор дня - /pidor/\n" +
                "END_OF_LINE\n" +
                "Осторожно! Пидор дня активирован! /end/\n" +
                "Выезжаю на место... /end/\n" +
                "Высокий приоритет мобильному юниту. /end/\n" +
                ".∧＿∧\n" +
                "( ･ω･｡)つ━☆・*。 \n" +
                "⊂　 ノ 　　　・゜+. \n" +
                "しーＪ　　　°。+ *´¨) \n" +
                "　　　　　　　　　.· ´¸.·*´¨) \n" +
                "　　　　　　　　　　(¸.·´ (¸.·'* ☆ ВЖУХ И ТЫ ПИДОР, /pidor/\n";
    }

}
