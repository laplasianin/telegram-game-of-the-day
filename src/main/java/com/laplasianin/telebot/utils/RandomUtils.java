package com.laplasianin.telebot.utils;

import com.laplasianin.telebot.model.TeleResult;
import com.laplasianin.telebot.service.messages.MessagesServices;
import io.micronaut.context.MessageSource;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static com.laplasianin.telebot.utils.TextsUtils.containsAny;
import static com.laplasianin.telebot.utils.TextsUtils.startsWithAny;

@Singleton
public class RandomUtils {

    @Inject MessagesServices messagesServices;

    public int getRandomValue(int max, TeleResult result) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpGet request = new HttpGet("https://www.random.org/integers/?num=1&min=0&col=1&base=10&format=plain&rnd=new&max=" + max);
            CloseableHttpResponse response = httpClient.execute(request);
            return Integer.parseInt(EntityUtils.toString(response.getEntity()).trim());
        } catch (Exception e) {
            if (result != null) {
                result.getMessages().add(messagesServices.getMessage("random.error") + "\n" + e.getMessage());
            }
            if (max == 0) {
                return 0;
            } else {
                return getJavaRandom(max);
            }
        }
    }

    public static int getJavaRandom(int max) {
        return new Random().nextInt(max);
    }

    public static <A> A getJavaRandom(List<A> list) {
        return list.get(getJavaRandom(list.size()));
    }

    public String getLoh(String text) {

        final String[] words = text.replaceAll("-", "").replaceAll(" {2}", " ").split(" ");

        final List<String> parasites = Arrays.asList(messagesServices.getMessage("parasites.words").split(","));

        final List<String> cleansedWords = Arrays.stream(words)
                .filter(a -> !parasites.contains(a))
                .collect(Collectors.toList());

        for (int i = 0; i < cleansedWords.size(); i++) {
            if (i != 0 && cleansedWords.get(i).startsWith(messagesServices.getMessage("loh.word"))) {

                final String previous = cleansedWords.get(i - 1);

                if (previous.startsWith(messagesServices.getMessage("selfname.words"))) {
                    return messagesServices.getMessage("selfname.answer");
                } else if (previous.startsWith(messagesServices.getMessage("botname.words"))) {
                    return messagesServices.getMessage("botname.answer");
                } else if (startsWithAny(previous, Arrays.asList(messagesServices.getMessage("names.words").split(",")))) {
                    return messagesServices.getMessage("names.answer");
                }
            }
        }
        return null;
    }


    public String getHui(String word) {
        Map<String, String> test = new HashMap<>();
        test.put("а", messagesServices.getMessage("hui"));
        test.put("б", messagesServices.getMessage("hui"));
        test.put("в", messagesServices.getMessage("hui"));
        test.put("г", messagesServices.getMessage("hui"));
        test.put("д", messagesServices.getMessage("hui"));
        test.put("е", messagesServices.getMessage("huu"));
        test.put("ё", messagesServices.getMessage("huu"));
        test.put("ж", messagesServices.getMessage("huia"));
        test.put("з", messagesServices.getMessage("huia"));
        test.put("и", messagesServices.getMessage("huu"));
        test.put("й", messagesServices.getMessage("hu"));
        test.put("к", messagesServices.getMessage("hui"));
        test.put("л", messagesServices.getMessage("hui"));
        test.put("м", messagesServices.getMessage("hui"));
        test.put("н", messagesServices.getMessage("hui"));
        test.put("о", messagesServices.getMessage("huu"));
        test.put("п", messagesServices.getMessage("hui"));
        test.put("р", messagesServices.getMessage("hui"));
        test.put("с", messagesServices.getMessage("hui"));
        test.put("т", messagesServices.getMessage("hui"));
        test.put("у", messagesServices.getMessage("hui"));
        test.put("ф", messagesServices.getMessage("hui"));
        test.put("х", messagesServices.getMessage("hui"));
        test.put("ц", messagesServices.getMessage("hui"));
        test.put("ч", messagesServices.getMessage("hui"));
        test.put("ш", messagesServices.getMessage("hui"));
        test.put("щ", messagesServices.getMessage("hui"));
        test.put("э", messagesServices.getMessage("huu"));
        test.put("ю", messagesServices.getMessage("huu"));
        test.put("я", messagesServices.getMessage("huu"));

        return test.get(word);
    }


}
