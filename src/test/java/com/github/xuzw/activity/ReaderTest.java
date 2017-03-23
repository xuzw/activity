package com.github.xuzw.activity;

import com.alibaba.fastjson.JSONObject;
import com.github.xuzw.activity.api.ActivityRepositoryReader;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午6:25:24
 */
public class ReaderTest {
    public static void main(String[] args) throws Exception {
        String path = "/Users/xuzewei/tmp/activity.repository";
        ActivityRepositoryReader repositoryReader = new ActivityRepositoryReader(path);
        System.out.println(JSONObject.toJSONString(repositoryReader.read()));
        System.out.println(JSONObject.toJSONString(repositoryReader.read()));
        System.out.println(JSONObject.toJSONString(repositoryReader.read()));
        System.out.println(JSONObject.toJSONString(repositoryReader.read()));
        repositoryReader.close();
    }
}
