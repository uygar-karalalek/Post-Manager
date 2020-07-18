package org.uygar.postit.data.properties;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

public class LogProperties extends PostProperties {

    public LogProperties() {
        super(Paths.get("src/main/resources/logs.properties"));
    }

    public void addHourLog() {
        String now = Integer.toString(LocalTime.now().getHour());
        storeLog(now);
    }

    public void addMonthLog() {
        String now = LocalDate.now().getMonth().name();
        storeLog(now);
    }

    private void storeLog(String now) {
        System.out.println(now);
        System.out.println(this.getStringProperty(now));
        int val = Integer.parseInt(this.getStringProperty(now));
        val = val == -1 ? 1 : val;
        this.putProperty(now, Integer.toString(++val));
    }

}