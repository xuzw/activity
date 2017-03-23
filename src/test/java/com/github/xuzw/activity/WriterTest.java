package com.github.xuzw.activity;

import java.util.UUID;

import com.github.xuzw.activity.api.DurRange;
import com.github.xuzw.activity.api.ActivityRepositoryWriter;
import com.github.xuzw.activity.api.TimestampRange;
import com.github.xuzw.activity.model.ActivityBuilder;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午5:49:37
 */
public class WriterTest {
    public static void main(String[] args) throws Exception {
        String path = "/Users/xuzewei/tmp/activity.repository";
        ActivityRepositoryWriter repositoryWriter = new ActivityRepositoryWriter(path);
        TimestampRange timeRange = new TimestampRange("2017-03-23 18:03:00", "2017-03-23 18:13:00");
        DurRange durRange = new DurRange(60 * 1000, 10 * 60 * 1000);
        for (int i = 0; i < 10; i++) {
            repositoryWriter.append(new ActivityBuilder().type("读圣经").effect("效果" + i).timestamp(timeRange.random()).dur(durRange.random()).target("电子版圣经").source("徐泽威").locale("家").uuid(UUID.randomUUID().toString()).build());
        }
        repositoryWriter.close();
    }
}
