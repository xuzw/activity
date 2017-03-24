package com.github.xuzw.activity.api.svg;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午6:03:12
 */
public class Offset {
    private final int x;
    private final int y;

    public Offset(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public String getXString() {
        return String.valueOf(x);
    }

    public int getY() {
        return y;
    }

    public String getYString() {
        return String.valueOf(y);
    }
}
