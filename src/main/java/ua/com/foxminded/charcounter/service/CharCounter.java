package ua.com.foxminded.charcounter.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import ua.com.foxminded.charcounter.formatter.Formatter;
import ua.com.foxminded.charcounter.model.Cache;

public class CharCounter {

    private Cache textCache;
    private Cache wordCache;
    private Formatter formatter;

    public CharCounter(Cache textCache, Cache wordCache, Formatter formatter) {
        this.textCache = textCache;
        this.wordCache = wordCache;
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
        Map<Character, Integer> parsedWord;
        String[] words = text.split("((?<=\\s+)|(?=\\s+))|(?=\\W\\p{Punct}|\\p{Punct}\\W)|(?<=\\W\\p{Punct}|\\p{Punct}\\W})");
        
        System.out.println(Arrays.toString(words));
        
        for (String word: words) {
            if (wordCache.isCached(word)) {
                parsedWord = wordCache.getFromCache(word);
            } else {
                parsedWord = countChars(word);
                wordCache.cache(Map.of(word, parsedWord));
            }
            result = addWord(word, parsedWord, result);
        }
        return result;
    }

    private Map<String, Map<Character, Integer>> addWord(String word, Map<Character, Integer> parsed, Map<String, Map<Character, Integer>> initial) {
        Map<String, Map<Character, Integer>> result = new LinkedHashMap<>(initial);
        Map<Character, Integer> intermediate = new LinkedHashMap<>();
        
        if (result.containsKey(word)) {
            for (Map.Entry<Character, Integer> entry: parsed.entrySet()) {
                Character key = entry.getKey();
                intermediate.put(key, result.get(word).get(key) + entry.getValue());
            }
            result.put(word, intermediate); 
        } else {
            result.put(word, parsed);
        }
        return result;
    }
    
    private Map<Character, Integer> countChars(String word) {
        Map<Character, Integer> result = new LinkedHashMap<>();
        char[] chars = word.toCharArray();

        for (char ch: chars) {
            result.merge(ch, 1, (oldValue, newValue) -> (oldValue + newValue));
        }
        return result;
    }

    private Map<String, Map<Character, Integer>> mergeCalculations(String string,
                Map<String, Map<Character, Integer>> toMerge) {
        Map<String, Map<Character, Integer>> result = new LinkedHashMap<>();
        Map<Character, Integer> subResult = new LinkedHashMap<>();

        toMerge.forEach((key, value) -> value.forEach((key1, value1) ->
                subResult.merge(key1, value1, (oldValue, newValue) -> (oldValue + newValue))));
        result.put(string, subResult);
        return result;
    }
}
