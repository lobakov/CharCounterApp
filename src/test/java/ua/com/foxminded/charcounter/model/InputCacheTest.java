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
    void shouldMaintainCacheProperlyWhenGivenMultipleStrings() {
        String[] testInput = {
                "Hello, world!",
                "I  like this task",
                "Some random stuff",
                "Some more random stuff",
                "I don't like typing",
                "Hello, world!",
                "I wish there were automatic generator of test strings",
                "Let me sssplain that to you",
                "Hello, world!",
                "One-two, one-two",
                "What you gonna do",
                "Some random stuff",
                "I wish there were automatic generator of test strings",
                "Some random stuff",
                "Some random stuff",
                "Something good",
                "There are no easter eggs in these sentences",
                "Some more",
                "Hello, world!",
                "Really, no easter eggs at all",
                "There is no cow level",
                "blah-blah-blah",
                "Some random stuff",
                "Some random stuff",
                "Some random stuff",
                "Some random stuff",
                "Testing caching",
                "Testing caching renewal",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!",
                "Hello, world!"
        };
        
        StringJoiner expected = new StringJoiner(NL);
        expected.add("Testing caching");
        expected.add("\"t\" - 2");
        expected.add("\"e\" - 1");
        expected.add("\"s\" - 1");
        expected.add("\"i\" - 2");
        expected.add("\"n\" - 2");
        expected.add("\"g\" - 2");
        expected.add("\" \" - 1");
        expected.add("\"c\" - 2");
        expected.add("\"a\" - 1");
        expected.add("\"h\" - 1");
        expected.add("");
        expected.add("Testing caching renewal");
        expected.add("\"t\" - 2");
        expected.add("\"e\" - 3");
        expected.add("\"s\" - 1");
        expected.add("\"i\" - 2");
        expected.add("\"n\" - 3");
        expected.add("\"g\" - 2");
        expected.add("\" \" - 2");
        expected.add("\"c\" - 2");
        expected.add("\"a\" - 2");
        expected.add("\"h\" - 1");
        expected.add("\"r\" - 1");
        expected.add("\"w\" - 1");
        expected.add("\"l\" - 1");
        expected.add("");
        expected.add("Hello, world!");
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
        expected.add("");

        for (String input: testInput) {
            charCounter.count(input);
        }

        assertEquals(expected.toString(), formatter.format(textCache.getCache()));
    }
}
