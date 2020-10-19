package ua.com.foxminded.charcounter.service;

import java.util.LinkedHashMap;
import java.util.Map;
import ua.com.foxminded.charcounter.formatter.Formatter;
import ua.com.foxminded.charcounter.model.Cache;
import ua.com.foxminded.charcounter.model.CacheFactory;

public class CharCounter {

    private Cache textCache;
    private Cache wordCache;
    private Formatter formatter;

    public CharCounter(CacheFactory factory, Formatter formatter) {
        this.textCache = factory.getCacheInstance();
        this.wordCache = factory.getCacheInstance();
        this.formatter = formatter;
    }

    public String count(String input) {
        validate(input);

        Map<String, Map<Character, Integer>> result = new LinkedHashMap<>();

        if (input.length() == 1) {
            result.put(input, Map.of(input.charAt(0), 1));
        } else if (textCache.isCached(input)) {
            result.put(input, textCache.getFromCache(input));
        } else {
            result = countWords(input);
            result = mergeCalculations(input, result);
            textCache.cache(result);
        }
        return formatter.format(result);
    }

    private void validate(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Null input string provided. Shouldn't be null!");
        } else if (input.isEmpty()) {
            throw new IllegalArgumentException("Empty text provided, text should contain something!");
        }
    }
    
    private Map<String, Map<Character, Integer>> countWords(String text) {
        Map<String, Map<Character, Integer>> result = new LinkedHashMap<>();
        Map<Character, Integer> parsedWord = new LinkedHashMap<>();
        String[] words = text.split("\\b");

        for (String word: words) {
            if (word.length() == 1) {
                result.put(word, Map.of(text.charAt(0), 1));
            } else if (wordCache.isCached(word)) {
                System.out.println(word + " is cached!");
                System.out.println("Before adding word from cache: " + result);
                result = addCachedWord(word, result, wordCache.getFromCache(word));
                System.out.println("After adding word from cache: " + result);
            } else {
                System.out.println(word + " is not cached!");
                parsedWord = calculate(word);
                System.out.println("Parsed word: " + word + " Calculated: " + parsedWord);
                wordCache.cache(Map.of(word, parsedWord));
                System.out.println("WordCache is: " + wordCache.getCache());
                result.put(word, parsedWord);
            }
        }
        System.out.println("WordCount Result: " + result);
        return result;
    }

    private Map<Character, Integer> calculate(String word) {
        Map<Character, Integer> result = new LinkedHashMap<>();
        char[] chars = word.toCharArray();
        System.out.println("Calculate this: " + word);
        for (char ch: chars) {
            result.merge(ch, 1, (a, b) -> (a + b));
            System.out.println("Calculated chars: " + result);
        }
        System.out.println("Total chars result: " + result);
        return result;
    }
    
    private Map<String, Map<Character, Integer>> addCachedWord(String word, Map<String, Map<Character, Integer>> target, Map<Character, Integer> cachedWord) {
        Map<String, Map<Character, Integer>> result = target;
        System.out.println("Cached word: " + cachedWord);
        if (result.containsKey(word)) {
            for (Map.Entry<Character, Integer> entry: cachedWord.entrySet()) {
                System.out.println("Entry: " + entry);
                result.get(word).merge(entry.getKey(), entry.getValue(), (a, b) -> (a + b));
            }
        } else {
            result.put(word, cachedWord);
        }
        return result;
    }

    private Map<String, Map<Character, Integer>> mergeCalculations(String string, Map<String, Map<Character, Integer>> toMerge) {
        System.out.println("To Merge: " + toMerge);
        Map<String, Map<Character, Integer>> result = new LinkedHashMap<>();
        Map<Character, Integer> subResult = new LinkedHashMap<>();

        for (Map.Entry<String, Map<Character, Integer>> entry: toMerge.entrySet()) {
            for (Map.Entry<Character, Integer> subEntry: entry.getValue().entrySet()) {
                Character key = subEntry.getKey();
                System.out.println("Key is " + key);
                subResult.merge(key, subEntry.getValue(), (a, b) -> (a + b));
                System.out.println("Subresult: " + subResult);
            }
        }
        result.put(string, subResult);
        System.out.println("Merge result: " + result);
        return result;
    }
}
