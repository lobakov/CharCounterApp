package ua.com.foxminded.charcounter.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.StringJoiner;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.charcounter.formatter.EntryFormatter;
import ua.com.foxminded.charcounter.formatter.Formatter;
import ua.com.foxminded.charcounter.service.CharCounter;

public class InputCacheTest {

    private static final String NL = System.lineSeparator();

    private Cache textCache = new InputCache();
    private Cache wordCache = new InputCache();
    private Formatter formatter = new EntryFormatter();
    private CharCounter charCounter = new CharCounter(textCache, wordCache, formatter);

    @Test
    void shouldCacheWordsProperlyWhenGivenRepetitiveWords() {
        String input = "hello world! hello world world hello";

        StringJoiner expected = new StringJoiner(NL);
        expected.add("world");
        expected.add("\"w\" - 1");
        expected.add("\"o\" - 1");
        expected.add("\"r\" - 1");
        expected.add("\"l\" - 1");
        expected.add("\"d\" - 1");
        expected.add("!");
        expected.add("\"!\" - 1");
        expected.add(" ");
        expected.add("\" \" - 1");
        expected.add("hello");
        expected.add("\"h\" - 1");
        expected.add("\"e\" - 1");
        expected.add("\"l\" - 2");
        expected.add("\"o\" - 1");

        charCounter.count(input);
        String actual = formatter.format(wordCache.getCache());

        assertEquals(expected.toString(), actual);
    }

    @Test
    void shouldCacheTextOnceWhenSameTextGivenMultipleTimes() {
        String input = "hello world! hello world world hello";

        StringJoiner expected = new StringJoiner(NL);
        expected.add("hello world! hello world world hello");
        expected.add("\"h\" - 3");
        expected.add("\"e\" - 3");
        expected.add("\"l\" - 9");
        expected.add("\"o\" - 6");
        expected.add("\" \" - 5");
        expected.add("\"w\" - 3");
        expected.add("\"r\" - 3");
        expected.add("\"d\" - 3");
        expected.add("\"!\" - 1");

        charCounter.count(input);
        String actual = formatter.format(textCache.getCache());

        assertEquals(expected.toString(), actual);
    }

    @Test
    void shouldMaintainTextCacheProperlyWhenGivenMultipleTexts() {
        String[] testInput = {
                "hello, world!",
                "I don't like this task anymore",
        };

        StringJoiner expected = new StringJoiner(NL);
        expected.add("I don't like this task anymore");
        expected.add("\"I\" - 1");
        expected.add("\" \" - 5");
        expected.add("\"d\" - 1");
        expected.add("\"o\" - 2");
        expected.add("\"n\" - 2");
        expected.add("\"'\" - 1");
        expected.add("\"t\" - 3");
        expected.add("\"l\" - 1");
        expected.add("\"i\" - 2");
        expected.add("\"k\" - 2");
        expected.add("\"e\" - 2");
        expected.add("\"h\" - 1");
        expected.add("\"s\" - 2");
        expected.add("\"a\" - 2");
        expected.add("\"y\" - 1");
        expected.add("\"m\" - 1");
        expected.add("\"r\" - 1");
        expected.add("hello, world!");
        expected.add("\"h\" - 1");
        expected.add("\"e\" - 1");
        expected.add("\"l\" - 3");
        expected.add("\"o\" - 2");
        expected.add("\",\" - 1");
        expected.add("\" \" - 1");
        expected.add("\"w\" - 1");
        expected.add("\"r\" - 1");
        expected.add("\"d\" - 1");
        expected.add("\"!\" - 1");

        for (String input: testInput) {
            charCounter.count(input);
        }
        String actual = formatter.format(textCache.getCache());

        assertEquals(expected.toString(), actual);
    }

    @Test
    void shouldMaintainWordCacheProperlyWhenGivenMultipleTexts() {
        String[] testInput = {
                "hello, world!",
                "I don't like this task anymore",
        };

        StringJoiner expected = new StringJoiner(NL);
        expected.add(",");
        expected.add("\",\" - 1");
        expected.add("this");
        expected.add("\"t\" - 1");
        expected.add("\"h\" - 1");
        expected.add("\"i\" - 1");
        expected.add("\"s\" - 1");
        expected.add("I");
        expected.add("\"I\" - 1");
        expected.add("like");
        expected.add("\"l\" - 1");
        expected.add("\"i\" - 1");
        expected.add("\"k\" - 1");
        expected.add("\"e\" - 1");
        expected.add("anymore");
        expected.add("\"a\" - 1");
        expected.add("\"n\" - 1");
        expected.add("\"y\" - 1");
        expected.add("\"m\" - 1");
        expected.add("\"o\" - 1");
        expected.add("\"r\" - 1");
        expected.add("\"e\" - 1");
        expected.add("don't");
        expected.add("\"d\" - 1");
        expected.add("\"o\" - 1");
        expected.add("\"n\" - 1");
        expected.add("\"'\" - 1");
        expected.add("\"t\" - 1");
        expected.add("world!");
        expected.add("\"w\" - 1");
        expected.add("\"o\" - 1");
        expected.add("\"r\" - 1");
        expected.add("\"l\" - 1");
        expected.add("\"d\" - 1");
        expected.add("\"!\" - 1");
        expected.add("task");
        expected.add("\"t\" - 1");
        expected.add("\"a\" - 1");
        expected.add("\"s\" - 1");
        expected.add("\"k\" - 1");
        expected.add(" "); 
        expected.add("\" \" - 1");
        expected.add("hello");
        expected.add("\"h\" - 1");
        expected.add("\"e\" - 1");
        expected.add("\"l\" - 2");
        expected.add("\"o\" - 1");

        for (String input: testInput) {
            charCounter.count(input);
        }
        String actual = formatter.format(wordCache.getCache());

        assertEquals(expected.toString(), actual);
    }
}
