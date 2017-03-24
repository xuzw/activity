package com.github.xuzw.activity.api.svg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午3:06:06
 */
public class HarmonyColor {
    private List<Integer> points = new ArrayList<>();

    public HarmonyColor() {
        points.add(42);
        points.add(84);
        complement();
    }

    public HarmonyColor(Set<Integer> points) {
        this.points.addAll(points);
        complement();
    }

    private void complement() {
        List<Integer> cpoints = new ArrayList<>();
        for (Integer p : points) {
            cpoints.add(255 - p);
        }
        for (Integer p : cpoints) {
            if (!points.contains(p)) {
                points.add(p);
            }
        }
    }

    public Color random() {
        int size = points.size();
        return new Color(points.get(new Random().nextInt(size)), points.get(new Random().nextInt(size)), points.get(new Random().nextInt(size)));
    }
}
