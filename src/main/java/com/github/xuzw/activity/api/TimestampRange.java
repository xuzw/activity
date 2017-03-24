package com.github.xuzw.activity.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午6:05:09
 */
public class TimestampRange {
    public static final String pattern = "yyyy-MM-dd hh:mm:ss";
    private final long beginTimestamp;
    private final long endTimestamp;

    public TimestampRange(String beginTime, String endTime) throws ParseException {
        beginTimestamp = parse(beginTime);
        endTimestamp = parse(endTime);
    }

    public SimpleDateFormat buildSimpleDateFormat() {
        return new SimpleDateFormat(pattern);
    }

    public String format(long timestamp) {
        return buildSimpleDateFormat().format(new Date(timestamp));
    }

    public long parse(String time) throws ParseException {
        return buildSimpleDateFormat().parse(time).getTime();
    }

    public long random() {
        return beginTimestamp + new Random().nextInt((int) (endTimestamp - beginTimestamp));
    }

    public long getBeginTimestamp() {
        return beginTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public long getDur() {
        return endTimestamp - beginTimestamp;
    }
}
