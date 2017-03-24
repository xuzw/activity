package com.github.xuzw.activity;

import java.util.List;

import com.github.xuzw.activity.api.ActivitySearchSpace;
import com.github.xuzw.activity.api.TimestampRange;
import com.github.xuzw.activity.api.svg.StaticSvg;
import com.github.xuzw.activity.model.Activity;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午7:36:21
 */
public class StaticSvgTest {
    public static void main(String[] args) throws Exception {
        ActivitySearchSpace activitySearchSpace = new ActivitySearchSpace("/Users/xuzewei/tmp/activity.repository.graphdb/");
        activitySearchSpace.getEntitySearchSpace().loadEntityRepository("/Users/xuzewei/tmp/entity.repository");
        activitySearchSpace.loadActivityRepository("/Users/xuzewei/tmp/activity.repository");
        TimestampRange timestampRange = new TimestampRange("2017-03-23 18:05:00", "2017-03-23 18:13:00");
        List<Activity> activities = activitySearchSpace.search(timestampRange);
        StaticSvg staticSvg = new StaticSvg(timestampRange, activities);
        System.out.println(staticSvg.toXml());
        activitySearchSpace.shutdown();
    }
}
