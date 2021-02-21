package com.laplasianin.telebot.service.gif;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.exception.GiphyException;
import com.laplasianin.telebot.utils.RandomUtils;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.util.CollectionUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.micronaut.core.util.CollectionUtils.isEmpty;

@Singleton
public class GifService {

    @Value("${giphy.apiKey}")
    public String giphyApiKey;

    @Inject RandomUtils randomUtils;

    private static final Map<String, List<String>> gifs = new HashMap<>();

    public String getRandomGif(String key) {
        final List<String> gifs = getGifs(key);
        if (CollectionUtils.isEmpty(gifs)) {
            return "";
        }
        return gifs.get(randomUtils.getRandomValue(gifs.size() - 1, null));
    }

    private List<String> getGifs(String key) {
        if (!gifs.containsKey(key) || isEmpty(gifs.get(key))) {
            Giphy giphy = new Giphy(giphyApiKey);
            try {
                SearchFeed search = giphy.search(key, 100, 0);
                List<String> foundGifs = search.getDataList().stream().map(a -> a.getImages().getOriginal().getUrl()).collect(Collectors.toList());
                gifs.put(key, foundGifs);
            } catch (GiphyException e) {}
        }

        return gifs.get(key);
    }

}
