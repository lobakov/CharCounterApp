package ua.com.foxminded.charcounter;

import ua.com.foxminded.charcounter.formatter.EntryFormatter;
import ua.com.foxminded.charcounter.formatter.Formatter;
import ua.com.foxminded.charcounter.model.Cache;
import ua.com.foxminded.charcounter.model.InputCache;
import ua.com.foxminded.charcounter.service.CharCounter;

public class CharCounterApp {

    public static void main(String[] args) {
        String input = "hello world! hello world world hello";
        Cache textCache = new InputCache();
        Cache wordCache = new InputCache();
        Formatter formatter = new EntryFormatter();
        CharCounter charCounter = new CharCounter(textCache, wordCache, formatter);
        System.out.println(charCounter.count(input));
        //System.out.println(formatter.format(textCache.getCache()));
        //System.out.println(formatter.format(wordCache.getCache()));
    }
}
