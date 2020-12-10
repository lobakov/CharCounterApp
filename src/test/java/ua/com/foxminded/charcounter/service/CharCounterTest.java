package ua.com.foxminded.charcounter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.StringJoiner;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.charcounter.formatter.EntryFormatter;
import ua.com.foxminded.charcounter.formatter.Formatter;
import ua.com.foxminded.charcounter.model.Cache;
import ua.com.foxminded.charcounter.model.InputCache;

public class CharCounterTest {

    private static final String NL = System.lineSeparator();
    private Cache textCache = new InputCache();
    private Cache wordCache = new InputCache();
    private Formatter formatter = new EntryFormatter();
    private CharCounter charCounter = new CharCounter(textCache, wordCache, formatter);

    @Test
    void shouldThrowExceptionIfStringIsNull() {
        String input = null;

        Exception thrownException = assertThrows(IllegalArgumentException.class,
                () -> charCounter.count(input));

        assertEquals("Null input string provided. Shouldn't be null!", thrownException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfStringIsEmpty() {
        String input = "";

        Exception thrownException = assertThrows(IllegalArgumentException.class,
                () -> charCounter.count(input));

        assertEquals("Empty text provided, text should contain something!", thrownException.getMessage());
    }

    @Test
    void shouldReturnProperCalculationsWhenStringOfSingleChar() {
        String input = "l";

        String expected = input + NL + "\"l\" - 1" + NL;

        assertEquals(expected, charCounter.count(input));
    }

    @Test
    void shouldReturnProperCalculationsWhenStringOfRepeatedChar() {
        String input = "lllll";

        String expected = input + NL + "\"l\" - 5" + NL;

        assertEquals(expected, charCounter.count(input));
    }

    @Test
    void shouldReturnProperCalculationsWhenStringProvided() {
        String input = "hello world!";

        StringJoiner expected = new StringJoiner(NL);
        expected.add(input);
        expected.add("\"h\" - 1");
        expected.add("\"e\" - 1");
        expected.add("\"l\" - 3");
        expected.add("\"o\" - 2");
        expected.add("\" \" - 1");
        expected.add("\"w\" - 1");
        expected.add("\"r\" - 1");
        expected.add("\"d\" - 1");
        expected.add("\"!\" - 1");
        expected.add("");

        assertEquals(expected.toString(), charCounter.count(input));
    }
}
