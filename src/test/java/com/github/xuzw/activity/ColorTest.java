package com.github.xuzw.activity;

import com.github.xuzw.activity.api.svg.Color;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年4月10日 下午12:52:46
 */
public class ColorTest {
    public static void main(String[] args) {
        System.out.println(new Color(12, 127, 77).mix(new Color(255, 0, 0)).toHexString());
    }
}
