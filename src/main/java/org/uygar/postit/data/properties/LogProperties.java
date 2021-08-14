package org.uygar.postit.data.properties;

import org.apache.commons.lang.math.NumberUtils;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class LogProperties extends PostManagerProperties {

    private Map<Integer, Integer> hoursAndFrequencies;
    private Map<String, Integer> monthsAndFrequencies;

    public LogProperties() {
        super(Paths.get("src/main/resources/logs.properties"));
        putAllInitialHoursIfAbsent();
    }

    private void putAllInitialHoursIfAbsent() {
        boolean missing = this.getProperty("0") == null;
        if (missing) {
            IntStream.rangeClosed(0, 23).forEach(hour -> this.putProperty(hour+"", "0"));
            Arrays.stream(Month.values()).forEach(month -> this.putProperty(month.toString(), "0"));
        }
    }

    private void storeLog(String now) {
        int val = Integer.parseInt(this.getStringProperty(now));
        val = val == -1 ? 1 : val;
        this.putProperty(now, Integer.toString(++val));
    }

    public void addHourLog() {
        String now = Integer.toString(LocalTime.now().getHour());
        storeLog(now);
        initFrequencies();
    }

    public void addMonthLog() {
        String now = LocalDate.now().getMonth().name();
        storeLog(now);
    }

    private void initFrequencies() {
        hoursAndFrequencies = new ConcurrentHashMap<>();
        monthsAndFrequencies = new ConcurrentHashMap<>();

        this.properties.forEach((key, value) -> {
            String stringKey = (String) key;

            if (NumberUtils.isNumber(stringKey))
                hoursAndFrequencies.put(Integer.parseInt(stringKey), Integer.parseInt(value.toString()));
            else
                monthsAndFrequencies.put(stringKey, Integer.parseInt(value.toString()));
        });
    }

    public void calculateLogStatistics() {
        initFrequencies();
    }

    public Map<Integer, Integer> getHoursAndFrequencies() {
        return hoursAndFrequencies;
    }

    public Map<String, Integer> getMonthsAndFrequencies() {
        return monthsAndFrequencies;
    }

}