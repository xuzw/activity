package com.github.xuzw.activity.api.svg;

import java.util.concurrent.TimeUnit;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午10:12:03
 */
public class DurationFormat {
    private final long dur;
    private String string;
    private long remainningDur;
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
    }
}
