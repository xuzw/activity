package com.github.xuzw.activity.api.svg;

import java.util.ArrayList;
import java.util.List;

import com.github.xuzw.activity.api.TimestampRange;
import com.github.xuzw.activity.model.Activity;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午9:28:24
 */
public class Timeline {
    private final TimestampRange timestampRange;
    private final List<Activity> activities = new ArrayList<>();

    public Timeline(TimestampRange timestampRange) {
        this.timestampRange = timestampRange;
    }

    public boolean append(Activity activity) {
        for (Activity x : activities) {
            if (hasConflict(activity, x)) {
                return false;
            }
        }
        activities.add(activity);
        return true;
    }

    public TimestampRange timestampRange() {
        return timestampRange;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public long minDur() {
        long min = activities.get(0).getDur();
        for (int i = 1; i < activities.size(); i++) {
            long cur = activities.get(i).getDur();
            if (cur < min) {
                min = cur;
            }
        }
        return min;
    }

    public Activity get(int index) {
        return activities.get(index);
    }

    public int size() {
        return activities.size();
    }

    public static boolean hasConflict(Activity activity1, Activity activity2) {
        long timestamp1 = activity1.getTimestamp();
        long timestampEnd1 = timestamp1 + activity1.getDur();
        long timestamp2 = activity2.getTimestamp();
        long timestampEnd2 = timestamp2 + activity2.getDur();
        return !(timestamp2 > timestampEnd1 || timestampEnd2 < timestamp1);
    }
}
