package uk.co.oaktest.results;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestTimer {
    Calendar startTime;
    Calendar finishTime;

    public TestTimer() {

    }

    public Calendar startTimer() {
        this.startTime = Calendar.getInstance();
        this.finishTime = null;

        return this.startTime;
    }

    public String getFormattedStartTime() {
        if (this.startTime != null) {
            return getTimeFromCalendar(this.startTime);
        }
        else {
            return null;
        }
    }

    public Calendar stopTimer() {
        this.finishTime = Calendar.getInstance();
        return this.finishTime;
    }

    public String getFormattedFinishTime() {
        if (this.finishTime != null) {
            return getTimeFromCalendar(this.finishTime);
        }
        else {
            return null;
        }
    }

    public String getTimeFromCalendar(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        dateFormat.setTimeZone(calendar.getTimeZone());
        return dateFormat.format(calendar.getTime());
    }

    public long getElapsedTime() {
        if (this.startTime != null && this.finishTime != null) {
            long end = this.startTime.getTimeInMillis();
            long start = this.finishTime.getTimeInMillis();
            return Math.abs(end - start);
        }
        else {
            return -1;
        }
    }
}
