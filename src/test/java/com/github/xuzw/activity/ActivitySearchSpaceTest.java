package com.github.xuzw.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.xuzw.activity.api.ActivitySearchSpace;
import com.github.xuzw.activity.api.TimestampRange;
import com.github.xuzw.activity.model.Activity;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午7:24:26
 */
public class ActivitySearchSpaceTest {
    public static void main(String[] args) throws Exception {
        ActivitySearchSpace activitySearchSpace = new ActivitySearchSpace("/Users/xuzewei/tmp/activity.repository.graphdb/");
        activitySearchSpace.getEntitySearchSpace().loadEntityRepository("/Users/xuzewei/tmp/entity.repository");
        activitySearchSpace.loadActivityRepository("/Users/xuzewei/tmp/activity.repository");
        System.out.println(activitySearchSpace.getEntitySearchSpace().has("徐泽威"));
        System.out.println(activitySearchSpace.getEntitySearchSpace().has("泽威"));
        System.out.println(JSON.toJSONString(activitySearchSpace.getEntitySearchSpace().search("徐泽威")));
        // List<Activity> activities = activitySearchSpace.search(new
        // TimestampRange("2017-03-23 18:03:00", "2017-03-23 18:13:00"));
        List<Activity> activities = activitySearchSpace.search(new TimestampRange("2017-03-23 18:05:00", "2017-03-23 18:13:00"));
        System.out.println(activities.size());
        System.out.println(JSON.toJSONString(activities));
        activitySearchSpace.shutdown();
    }
}
