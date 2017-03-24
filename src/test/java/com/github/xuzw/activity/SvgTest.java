package com.github.xuzw.activity;

import java.util.Random;

import com.github.xuzw.activity.api.svg.Color;
import com.github.xuzw.activity.api.svg.HarmonyColor;
import com.github.xuzw.activity.api.svg.MaterialDesignSymbol;
import com.github.xuzw.activity.api.utils.xml.TagBuilder;
import com.github.xuzw.activity.api.utils.xml.XML;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 上午12:35:52
 */
public class SvgTest {
    public static void main(String[] args) {
        System.out.println(toActivityXml());
    }

    public static String toActivityXml() {
        HarmonyColor harmonyColor = new HarmonyColor();
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        TagBuilder builder = XML.build("svg").attr("width", "500").attr("height", "500").attr("xmlns", "http://www.w3.org/2000/svg").attr("xmlns:xlink", "http://www.w3.org/1999/xlink").attr("version", "1.0");
        Color backgroundColor = harmonyColor.random();
        Color symbolColor = harmonyColor.random();
        Color textColor = backgroundColor.reverse();
        Color bubblingColor = backgroundColor.mix(symbolColor, 0.5);
        Color bubblingTextColor = bubblingColor.reverse();
        TagBuilder bubblingSymbolTagBuilder = builder.child("symbol").attr("id", "bubbling").attr("viewBox", "0 0 24 24");
        bubblingSymbolTagBuilder.child("path").attr("d", "M2 12h20L12 24z");
        bubblingSymbolTagBuilder.child("circle").attr("r", "10").attr("cx", "12").attr("cy", "12");
        builder.child("use").attr("xlink:href", "#bubbling").attr("fill", bubblingColor.toString()).attr("x", "0").attr("y", "200").attr("width", "200").attr("height", "200");
        builder.child("text").attr("x", "100").attr("y", "300").attr("text-anchor", "middle").attr("font-size", "28").attr("fill", bubblingTextColor.toString()).text("source");
        builder.child("use").attr("xlink:href", "#bubbling").attr("fill", bubblingColor.toString()).attr("x", "300").attr("y", "200").attr("width", "200").attr("height", "200");
        builder.child("text").attr("x", "400").attr("y", "300").attr("text-anchor", "middle").attr("font-size", "28").attr("fill", bubblingTextColor.toString()).text("target");
        builder.child("rect").attr("x", "0").attr("y", "400").attr("width", "500").attr("height", "100").attr("fill", backgroundColor.toString());
        builder.child("use").attr("xlink:href", "all-material-design-symbols.svg#" + MaterialDesignSymbol.random().getId()).attr("fill", symbolColor.toString()).attr("x", "0").attr("y", "400").attr("width", "100").attr("height", "100");
        builder.child("text").attr("x", "100").attr("y", "456").attr("font-size", "28").attr("fill", textColor.toString()).text("这是汉字，这是汉字，这是汉…");
        sb.append(builder.print(XML.FOR_HUMAN));
        return sb.toString();
    }

    public static String toReduceColorXml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        TagBuilder builder = XML.build("svg").attr("width", "500").attr("height", "5200").attr("xmlns", "http://www.w3.org/2000/svg").attr("xmlns:xlink", "http://www.w3.org/1999/xlink").attr("version", "1.0");
        Color randomColor = new Color(Color.max, Color.max, Color.max);
        for (int i = 0; i <= 255 / 5; i++) {
            Color color = randomColor.reduce(5 * i);
            builder.child("rect").attr("x", "0").attr("y", String.valueOf(100 * i)).attr("width", "500").attr("height", "100").attr("fill", color.toString());
            builder.child("text").attr("x", "0").attr("y", String.valueOf(100 * i + 28)).attr("font-size", "28").text(color.toString());
        }
        sb.append(builder.print(XML.FOR_HUMAN));
        return sb.toString();
    }

    public static String toMixColorXml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        TagBuilder builder = XML.build("svg").attr("width", "900").attr("height", "5200").attr("xmlns", "http://www.w3.org/2000/svg").attr("xmlns:xlink", "http://www.w3.org/1999/xlink").attr("version", "1.0");
        Color randomMainColor = Color.random();
        for (int i = 0; i <= 255 / 5; i++) {
            double ratio = new Random().nextInt(101) / 100.0;
            Color randomColor = Color.random();
            Color mixColor = randomMainColor.mix(randomColor, ratio);
            builder.child("rect").attr("x", "0").attr("y", String.valueOf(100 * i)).attr("width", "300").attr("height", "100").attr("fill", randomMainColor.toString());
            builder.child("text").attr("x", "0").attr("y", String.valueOf(100 * i + 28)).attr("font-size", "28").text(randomMainColor.toString());
            builder.child("rect").attr("x", "300").attr("y", String.valueOf(100 * i)).attr("width", "300").attr("height", "100").attr("fill", mixColor.toString());
            builder.child("text").attr("x", "300").attr("y", String.valueOf(100 * i + 28)).attr("font-size", "28").text(mixColor.toString() + " " + ratio);
            builder.child("rect").attr("x", "600").attr("y", String.valueOf(100 * i)).attr("width", "300").attr("height", "100").attr("fill", randomColor.toString());
            builder.child("text").attr("x", "600").attr("y", String.valueOf(100 * i + 28)).attr("font-size", "28").text(randomColor.toString());
        }
        sb.append(builder.print(XML.FOR_HUMAN));
        return sb.toString();
    }

    public static String toTagXml() {
        HarmonyColor harmonyColor = new HarmonyColor();
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        TagBuilder builder = XML.build("svg").attr("width", "500").attr("height", "100").attr("xmlns", "http://www.w3.org/2000/svg").attr("xmlns:xlink", "http://www.w3.org/1999/xlink").attr("version", "1.0");
        Color backgroundColor = harmonyColor.random();
        Color symbolColor = harmonyColor.random();
        Color textColor = backgroundColor.reverse();
        builder.child("rect").attr("id", "background").attr("width", "500").attr("height", "100").attr("fill", backgroundColor.toString());
        builder.child("use").attr("id", "symbol").attr("xlink:href", "all-material-design-symbols.svg#" + MaterialDesignSymbol.random().getId()).attr("fill", symbolColor.toString()).attr("width", "100").attr("height", "100");
        builder.child("text").attr("id", "text").attr("x", "100").attr("y", "56").attr("font-size", "28").attr("fill", textColor.toString()).text("这是汉字，这是汉字，这是汉…");
        sb.append(builder.print(XML.FOR_HUMAN));
        return sb.toString();
    }

    public static String toBubblingXml() {
        HarmonyColor harmonyColor = new HarmonyColor();
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        TagBuilder builder = XML.build("svg").attr("width", "24").attr("height", "24").attr("xmlns", "http://www.w3.org/2000/svg").attr("xmlns:xlink", "http://www.w3.org/1999/xlink").attr("version", "1.0");
        String color = harmonyColor.random().toString();
        builder.child("polygon").attr("points", "2,12 22,12 12,24").attr("fill", color);
        builder.child("circle").attr("r", "10").attr("cx", "12").attr("cy", "12").attr("fill", color);
        sb.append(builder.print(XML.FOR_HUMAN));
        return sb.toString();
    }
}
