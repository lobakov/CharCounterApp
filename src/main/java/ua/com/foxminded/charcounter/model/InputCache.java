package ua.com.foxminded.charcounter.model;

import java.util.Map;
import java.util.WeakHashMap;

public class InputCache implements Cache {

    private static Map<String, Map<Character, Integer>> cache = new WeakHashMap<>();

    public boolean isCached(String string) {
        return cache.containsKey(string);
    }

    public void cache(Map<String, Map<Character, Integer>> processedString) {
        cache.putAll(processedString);
    }

    public Map<Character, Integer> getFromCache(String cachedInput) {
        return cache.get(cachedInput);
    }

    public Map<String, Map<Character, Integer>> getCache() {
        return cache;
    }
}
