package com.github.xuzw.activity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午5:39:42
 */
public class ActivityBuilder {
    private List<String> sources = new ArrayList<>();
    private Activity obj = new Activity();

    public ActivityBuilder() {
        obj.setSources(sources);
    }

    public ActivityBuilder type(String type) {
        obj.setType(type);
        return this;
    }

    public ActivityBuilder effect(String effect) {
        obj.setEffect(effect);
        return this;
    }

    public ActivityBuilder expectEffect(String expectEffect) {
        obj.setExpectEffect(expectEffect);
        return this;
    }

    public ActivityBuilder timestamp(long timestamp) {
        obj.setTimestamp(timestamp);
        return this;
    }

    public ActivityBuilder expectTimestamp(long expectTimestamp) {
        obj.setExpectTimestamp(expectTimestamp);
        return this;
    }

    public ActivityBuilder dur(long dur) {
        obj.setDur(dur);
        return this;
    }

    public ActivityBuilder expectDur(long expectDur) {
        obj.setExpectDur(expectDur);
        return this;
    }

    public ActivityBuilder target(String target) {
        obj.setTarget(target);
        return this;
    }

    public ActivityBuilder source(String source) {
        sources.add(source);
        return this;
    }

    public ActivityBuilder locale(String locale) {
        obj.setLocale(locale);
        return this;
    }

    public ActivityBuilder uuid(String uuid) {
        obj.setUuid(uuid);
        return this;
    }

    public Activity build() {
        return obj;
    }
}
