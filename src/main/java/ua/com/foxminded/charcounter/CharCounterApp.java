package ua.com.foxminded.charcounter;

import ua.com.foxminded.charcounter.formatter.EntryFormatter;
import ua.com.foxminded.charcounter.model.InputCache;
import ua.com.foxminded.charcounter.model.InputCacheFactory;
import ua.com.foxminded.charcounter.service.CharCounter;

public class CharCounterApp {

    public static void main(String[] args) {
        String input = "hello world! hello world world hello";
        CharCounter charCounter = new CharCounter(InputCacheFactory.getInstance(), new EntryFormatter());
        System.out.println(charCounter.count(input));
    }
}
