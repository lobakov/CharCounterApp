package ua.com.foxminded.charcounter.model;

import java.util.Map;
import java.util.WeakHashMap;

public class InputCache implements Cache {

    private Map<String, Map<Character, Integer>> cache = new WeakHashMap<>();

    public boolean isCached(String string) {
        return this.cache.containsKey(string);
    }

    public void cache(Map<String, Map<Character, Integer>> processedString) {
        this.cache.putAll(processedString);
    }

    public Map<Character, Integer> getFromCache(String cachedInput) {
        return this.cache.get(cachedInput);
    }

    public Map<String, Map<Character, Integer>> getCache() {
        return this.cache;
    }
}
