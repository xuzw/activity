package com.github.xuzw.activity.model;

import java.util.List;
import java.util.TreeMap;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午5:22:35
 */
public class Activity {
    private String type;
    private String effect;
    private String expectEffect;
    private long timestamp;
    private long expectTimestamp;
    private long dur;
    private long expectDur;
    private String target;
    private List<String> sources;
    private String locale;
    private String uuid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getExpectEffect() {
        return expectEffect;
    }

    public void setExpectEffect(String expectEffect) {
        this.expectEffect = expectEffect;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getExpectTimestamp() {
        return expectTimestamp;
    }

    public void setExpectTimestamp(long expectTimestamp) {
        this.expectTimestamp = expectTimestamp;
    }

    public long getDur() {
        return dur;
    }

    public void setDur(long dur) {
        this.dur = dur;
    }

    public long getExpectDur() {
        return expectDur;
    }

    public void setExpectDur(long expectDur) {
        this.expectDur = expectDur;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TreeMap<String, Object> toTreeMap() {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("type", type);
        treeMap.put("effect", effect);
        treeMap.put("expectEffect", expectEffect);
        treeMap.put("timestamp", timestamp);
        treeMap.put("expectTimestamp", expectTimestamp);
        treeMap.put("dur", dur);
        treeMap.put("expectDur", expectDur);
        treeMap.put("target", target);
        treeMap.put("sources", sources);
        treeMap.put("locale", locale);
        treeMap.put("uuid", uuid);
        return treeMap;
    }
}
