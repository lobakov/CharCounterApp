package ua.com.foxminded.charcounter.formatter;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

public class EntryFormatter implements Formatter {

    private static final String NL = System.lineSeparator();

    @Override
    public String format(Map<String, Map<Character, Integer>> entries) {
        StringJoiner joiner = new StringJoiner("");
        Iterator<Entry<String, Map<Character, Integer>>> iterator = entries.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Map<Character, Integer>> entry = iterator.next();
            joiner.add(entry.getKey());
            entry.getValue().forEach( (key, value) -> {
                joiner.add(NL);
                joiner.add("\"");
                joiner.add(key.toString());
                joiner.add("\" - ");
                joiner.add(value.toString());
            });

            if (iterator.hasNext()) {
                joiner.add(NL);
            }
        }
        return joiner.toString();
    }
}
