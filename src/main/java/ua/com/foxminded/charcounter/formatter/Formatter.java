package ua.com.foxminded.charcounter.formatter;

import java.util.Map;

public interface Formatter {

    String format(Map<String, Map<Character, Integer>> entries);
}
