package com.github.xuzw.activity.api.svg;

import java.util.Random;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午3:05:37
 */
public class Color {
    public static final int max = 255;
    public static final int min = 0;
    private static final String format = "rgb(%s,%s,%s)";
    private int r;
    private int g;
    private int b;

    public Color(int r, int g, int b) {
        this.r = getFormalValue(r);
        this.g = getFormalValue(g);
        this.b = getFormalValue(b);
    }

    public Color reverse() {
        return new Color(max - r, max - g, max - b);
    }

    public int maxValue() {
        return Math.max(Math.max(r, g), b);
    }

    public int minValue() {
        return Math.min(Math.min(r, g), b);
    }

    public Color increase(int number) {
        return increase(number, true);
    }

    public Color increase(int number, boolean keepColor) {
        int remainder = max - maxValue();
        if (keepColor && number > remainder) {
            return new Color(r + remainder, g + remainder, b + remainder);
        }
        return new Color(r + number, g + number, b + number);
    }

    public Color reduce(int number) {
        return reduce(number, true);
    }

    public Color reduce(int number, boolean keepColor) {
        int remainder = minValue();
        if (keepColor && number > remainder) {
            return new Color(r - remainder, g - remainder, b - remainder);
        }
        return new Color(r - number, g - number, b - number);
    }

    public Color mix(Color color) {
        return mix(color, 0.5);
    }

    public Color mix(Color color, double ratio) {
        return new Color(_mix(r, color.r, ratio), _mix(g, color.g, ratio), _mix(b, color.b, ratio));
    }

    public static int _mix(int source, int target, double ratio) {
        return (int) (source * (1 - ratio) + target * ratio);
    }

    public static int getFormalValue(int value) {
        return Math.min(Math.max(min, value), max);
    }

    public static Color random() {
        return new Color(new Random().nextInt(max + 1), new Random().nextInt(max + 1), new Random().nextInt(max + 1));
    }

    @Override
    public String toString() {
        return String.format(format, r, g, b);
    }
}
