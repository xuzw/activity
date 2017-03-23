package com.github.xuzw.activity.api;

import java.util.Random;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午6:09:36
 */
public class DurRange {
    private int begin;
    private int end;

    public DurRange(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int random() {
        return begin + new Random().nextInt(end - begin);
    }
}
