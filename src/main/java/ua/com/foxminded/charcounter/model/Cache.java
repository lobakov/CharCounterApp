package ua.com.foxminded.charcounter.model;

import java.util.Map;

public interface Cache {

    boolean isCached(String text);
    void cache(Map<String, Map<Character, Integer>> text);
    Map<Character, Integer> getFromCache(String cachedText);
    Map<String, Map<Character, Integer>> getCache();
}
