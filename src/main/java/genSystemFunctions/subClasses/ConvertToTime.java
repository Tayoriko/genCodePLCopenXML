package genSystemFunctions.subClasses;

public class ConvertToTime {
    private String time;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private int sec = 0;
    private int ms = 0;

    public ConvertToTime () {}

    public ConvertToTime (int day, int hour, int minute, int sec, int ms) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.sec = sec;
        this.ms = ms;
    }

    public String getSecMs (int sec, int ms) {
        clean();
        this.sec = sec;
        this.ms = ms;
        return getTime();
    }

    public String getDay (int day) {
        clean();
        this.day = day;
        return getTime();
    }

    public String getHour (int hour) {
        clean();
        this.hour = hour;
        return getTime();
    }

    public String getMinute (int minute) {
        clean();
        this.minute = minute;
        return getTime();
    }

    public String getTime () {
        StringBuilder time = new StringBuilder();
        if (day > 0) {time.append(day).append("D");}
        if (hour > 0) {time.append(hour).append("H");}
        if (minute > 0) {time.append(minute).append("M");}
        if (sec > 0) {time.append(sec).append("S");}
        if (ms > 0) {time.append(ms).append("MS");}
        return time.toString();
    }

    private void clean () {
        this.day = 0;
        this.hour = 0;
        this.minute = 0;
        this.sec = 0;
        this.ms = 0;
        this.time = "";
    }

}
