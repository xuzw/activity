package com.github.xuzw.activity.api.svg;

import java.util.ArrayList;
import java.util.List;

import com.github.xuzw.activity.api.TimestampRange;
import com.github.xuzw.activity.model.Activity;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午9:28:48
 */
public class Timelines {
    private final TimestampRange timestampRange;
    private List<Timeline> timelines = new ArrayList<>();

    public Timelines(TimestampRange timestampRange) {
        this.timestampRange = timestampRange;
        timelines.add(new Timeline(timestampRange));
    }

    public void append(Activity activity) {
        for (Timeline timeline : timelines) {
            if (timeline.append(activity)) {
                return;
            }
        }
        Timeline newTimeline = new Timeline(timestampRange);
        newTimeline.append(activity);
        timelines.add(newTimeline);
    }

    public TimestampRange timestampRange() {
        return timestampRange;
    }

    public List<Timeline> getTimelines() {
        return timelines;
    }

    public Timeline get(int index) {
        return timelines.get(index);
    }

    public int size() {
        return timelines.size();
    }

    public long minDur() {
        long min = timelines.get(0).minDur();
        for (int i = 1; i < timelines.size(); i++) {
            long cur = timelines.get(i).minDur();
            if (cur < min) {
                min = cur;
            }
        }
        return min;
    }
}
