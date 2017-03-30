package com.github.xuzw.activity.api.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午10:12:03
 */
public class DurationFormat {
    private static final long year_in_mills = 365 * TimeUnit.DAYS.toMillis(1);

    private final long dur;
    private String string;
    private long remainningDur;
    private long years;
    private long days;
    private long hours;
    private long minutes;
    private long seconds;

    public DurationFormat(long dur) {
        this.dur = dur;
        this.remainningDur = dur;
        _parse();
    }

    private void _parse() {
        StringBuffer sb = new StringBuffer();
        years = remainningDur / year_in_mills;
        if (years > 0) {
            remainningDur -= year_in_mills * years;
            sb.append(years).append("年");
        }
        days = TimeUnit.MILLISECONDS.toDays(remainningDur);
        if (days > 0) {
            remainningDur -= TimeUnit.DAYS.toMillis(1) * days;
            sb.append(days).append("天");
        }
        hours = TimeUnit.MILLISECONDS.toHours(remainningDur);
        if (hours > 0) {
            remainningDur -= TimeUnit.HOURS.toMillis(1) * hours;
            sb.append(hours).append("时");
        }
        minutes = TimeUnit.MILLISECONDS.toMinutes(remainningDur);
        if (minutes > 0) {
            remainningDur -= TimeUnit.MINUTES.toMillis(1) * minutes;
            sb.append(minutes).append("分");
        }
        seconds = TimeUnit.MILLISECONDS.toSeconds(remainningDur);
        if (seconds > 0) {
            remainningDur -= TimeUnit.SECONDS.toMillis(1) * seconds;
            sb.append(seconds).append("秒");
        }
        string = sb.toString();
    }

    public long getLong() {
        return dur;
    }

    public String getString() {
        return string;
    }

    public static void main(String[] args) {
        System.out.println(new DurationFormat(999).getString());
        System.out.println(new DurationFormat(1000).getString());
        System.out.println(new DurationFormat(1999).getString());
        System.out.println(new DurationFormat(2000).getString());
        System.out.println(new DurationFormat(59 * 1000).getString());
        System.out.println(new DurationFormat(60 * 1000).getString());
        System.out.println(new DurationFormat(61 * 1000).getString());
        System.out.println(new DurationFormat(610 * 1000).getString());
        System.out.println(new DurationFormat(3600 * 1000).getString());
        System.out.println(new DurationFormat(3700 * 1000).getString());
        System.out.println(new DurationFormat(4700 * 1000).getString());
        System.out.println(new DurationFormat(24 * 60 * 60 * 1000L).getString());
        System.out.println(new DurationFormat(24 * 60 * 60 * 1000L + 4700 * 1000).getString());
        System.out.println(new DurationFormat(365 * 24 * 60 * 60 * 1000L).getString());
        System.out.println(new DurationFormat(365 * 24 * 60 * 60 * 1000L + 4700 * 1000).getString());
        System.out.println(new DurationFormat(5 * 365 * 24 * 60 * 60 * 1000L).getString());
        System.out.println(new DurationFormat(5 * 365 * 24 * 60 * 60 * 1000L + 4700 * 1000).getString());
    }
}
