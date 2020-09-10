package org.uygar.postit.data.properties;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class LogProperties extends PostProperties {

    public LogProperties() {
        super(Paths.get("src/main/resources/logs.properties"));
    }

    private Map<Integer, Integer> hoursAndFrequencies;
    private Map<String, Integer> monthsAndFrequencies;

    public void addHourLog() {
        String now = Integer.toString(LocalTime.now().getHour());
        storeLog(now);
        initFrequencies();
    }

    public void addMonthLog() {
        String now = LocalDate.now().getMonth().name();
        storeLog(now);
    }

    private void storeLog(String now) {
        int val = Integer.parseInt(this.getStringProperty(now));
        val = val == -1 ? 1 : val;
        this.putProperty(now, Integer.toString(++val));
    }

    private void initFrequencies() {
        hoursAndFrequencies = new ConcurrentHashMap<>();
        monthsAndFrequencies = new ConcurrentHashMap<>();

        Predicate<String> isDigit = s -> Character.isDigit(s.charAt(0));

        this.properties.forEach((key, value) -> {
            String stringKey = (String) key;

            if (isDigit.test(stringKey))
                hoursAndFrequencies.put(Integer.parseInt(stringKey), Integer.parseInt(value.toString()));
            else
                monthsAndFrequencies.put(stringKey, Integer.parseInt(value.toString()));
        });
    }

    public void updateFrequencies() {
        initFrequencies();
    }

    public Map<Integer, Integer> getHoursAndFrequencies() {
        return hoursAndFrequencies;
    }

    public Map<String, Integer> getMonthsAndFrequencies() {
        return monthsAndFrequencies;
    }

}