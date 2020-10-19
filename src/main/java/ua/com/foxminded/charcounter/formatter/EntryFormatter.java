package ua.com.foxminded.charcounter.formatter;

import java.util.Map;
import java.util.StringJoiner;

public class EntryFormatter implements Formatter {

    public static final String NL = System.lineSeparator();

    @Override
    public String format(Map<String, Map<Character, Integer>> entries) {
        StringJoiner joiner = new StringJoiner("");

        for (String key: entries.keySet()) {
            joiner.add(key);
            for (Map.Entry<Character, Integer> entry: entries.get(key).entrySet()) {
                joiner.add(NL);
                joiner.add("\"");
                joiner.add(entry.getKey().toString());
                joiner.add("\" - ");
                joiner.add(entry.getValue().toString());
            }
            joiner.add(NL);
        }
        return joiner.toString();
    }
}
