package com.github.xuzw.activity.api.svg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.github.xuzw.activity.api.TimestampRange;
import com.github.xuzw.activity.api.utils.xml.TagBuilder;
import com.github.xuzw.activity.api.utils.xml.XML;
import com.github.xuzw.activity.model.Activity;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月24日 下午5:17:06
 */
public class StaticSvg {
    /**
     * 气泡高度
     */
    public static final int bubblingHeight = 100;
    /**
     * 气泡宽度
     */
    public static final int bubblingWidth = bubblingHeight;
    /**
     * 气泡字体大小
     */
    public static final int bubblingFontSize = 16;
    /**
     * 符号高度
     */
    public static final int symbolHeight = 50;
    /**
     * 符号宽度
     */
    public static final int symbolWidth = symbolHeight;
    /**
     * 字体大小
     */
    public static final int textFontSize = 20;
    /**
     * 下角标字体大小
     */
    public static final int subscriptTextFontSize = 10;
    /**
     * 行高
     */
    public static final int lineHeight = bubblingHeight + symbolHeight;
    /**
     * 最小符号容器宽度
     */
    public static final int minSymbolContainerWidth = bubblingWidth * 2;
    /**
     * 上下留白
     */
    public static final int topAndBottomBlankLeaving = 10;
    /**
     * 行留白
     */
    public static final int lineBlankLeaving = 10;
    /**
     * 左右留白
     */
    public static final int leftAndRightBlankLeaving = 10;
    /**
     * 高度
     */
    private int height;
    /**
     * 宽度
     */
    private int width;
    /**
     * 宽度每毫秒
     */
    private double widthPerMs;

    private Timelines timelines;
    private List<TimelinesLayoutStep> timelinesLayoutSteps = new ArrayList<>();

    public StaticSvg(TimestampRange timestampRange, List<Activity> activities) {
        timelines = new Timelines(timestampRange);
        for (Activity activity : activities) {
            TimelinesLayoutStep step = new TimelinesLayoutStep(JSON.toJSONString(activity), JSON.toJSONString(timelines));
            timelines.append(activity);
            step.setTimelinesAfterAppend(JSON.toJSONString(timelines));
            timelinesLayoutSteps.add(step);
        }
        height = _calcHeight(timelines);
        widthPerMs = _calcWidthPerMs(timelines);
        width = (int) (widthPerMs * timestampRange.getDur());
    }

    public List<TimelinesLayoutStep> getTimelinesLayoutSteps() {
        return timelinesLayoutSteps;
    }

    private static int _calcHeight(Timelines timelines) {
        return topAndBottomBlankLeaving * 2 + lineHeight * timelines.size() + lineBlankLeaving * (timelines.size() - 1);
    }

    private static double _calcWidthPerMs(Timelines timelines) {
        return minSymbolContainerWidth * 1.0 / timelines.minDur();
    }

    public String toXml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        TagBuilder builder = XML.build("svg").attr("width", String.valueOf(width)).attr("height", String.valueOf(height)).attr("xmlns", "http://www.w3.org/2000/svg").attr("xmlns:xlink", "http://www.w3.org/1999/xlink").attr("version", "1.0");
        TagBuilder bubblingSymbolTagBuilder = builder.child("symbol").attr("id", "bubbling").attr("viewBox", "0 0 24 24");
        bubblingSymbolTagBuilder.child("path").attr("d", "M2 12h20L12 24z");
        bubblingSymbolTagBuilder.child("circle").attr("r", "10").attr("cx", "12").attr("cy", "12");
        for (int i = 0; i < timelines.size(); i++) {
            _appendLineToTagBuilder(timelines.get(i), new Offset(0, topAndBottomBlankLeaving + lineHeight * i), builder);
        }
        sb.append(builder.print(XML.FOR_HUMAN));
        return sb.toString();
    }

    private void _appendLineToTagBuilder(Timeline timeline, Offset offset, TagBuilder builder) {
        for (int i = 0; i < timeline.size(); i++) {
            Activity activity = timeline.get(i);
            int timestampOffset = (int) (activity.getTimestamp() - timeline.timestampRange().getBeginTimestamp());
            _appendActivityToTagBuilder(activity, new Offset((int) (offset.getX() + timestampOffset * widthPerMs), offset.getY()), builder);
        }
    }

    private void _appendActivityToTagBuilder(Activity activity, Offset offset, TagBuilder builder) {
        HarmonyColor harmonyColor = new HarmonyColor();
        Color backgroundColor = harmonyColor.random();
        Color symbolColor = harmonyColor.random();
        Color textColor = backgroundColor.reverse();
        Color bubblingColor = backgroundColor.mix(symbolColor);
        Color bubblingTextColor = bubblingColor.reverse();
        int symbolOffsetX = offset.getX();
        int symbolOffsetY = offset.getY() + bubblingHeight;
        int symbolContainerOffsetX = symbolOffsetX;
        int symbolContainerOffsetY = symbolOffsetY;
        int symbolContainerWidth = (int) (widthPerMs * activity.getDur());
        int textOffsetX = symbolOffsetX + symbolWidth;
        int textOffsetY = symbolOffsetY + symbolHeight / 2;
        int subscriptTextOffsetX = textOffsetX;
        int subscriptTextOffsetY = symbolContainerOffsetY + symbolHeight - symbolHeight / 4;
        int sourceBubblingOffsetX = offset.getX();
        int targetBubblingOffsetX = offset.getX() + symbolContainerWidth - bubblingWidth;
        int bubblingOffsetY = offset.getY();
        int sourceBubblingTextOffsetX = sourceBubblingOffsetX + bubblingWidth / 2;
        int targetBubblingTextOffsetX = targetBubblingOffsetX + bubblingWidth / 2;
        int bubblingTextOffsetY = bubblingOffsetY + bubblingHeight / 2;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String subscriptText = String.format("耗时%s %s", new DurationFormat(activity.getDur()).getString(), dateFormat.format(activity.getTimestamp()));
        String sourceBubblingText = activity.getSources().isEmpty() ? "unknow" : activity.getSources().get(0);
        String targetBubblingText = StringUtils.isBlank(activity.getTarget()) ? "unknow" : activity.getTarget();
        builder.child("use").attr("xlink:href", "#bubbling").attr("fill", bubblingColor.toString()).attr("x", String.valueOf(sourceBubblingOffsetX)).attr("y", String.valueOf(bubblingOffsetY)).attr("width", String.valueOf(bubblingWidth)).attr("height", String.valueOf(bubblingHeight));
        builder.child("text").attr("x", String.valueOf(sourceBubblingTextOffsetX)).attr("y", String.valueOf(bubblingTextOffsetY)).attr("text-anchor", "middle").attr("font-size", String.valueOf(bubblingFontSize)).attr("fill", bubblingTextColor.toString()).text(sourceBubblingText);
        builder.child("use").attr("xlink:href", "#bubbling").attr("fill", bubblingColor.toString()).attr("x", String.valueOf(targetBubblingOffsetX)).attr("y", String.valueOf(bubblingOffsetY)).attr("width", String.valueOf(bubblingWidth)).attr("height", String.valueOf(bubblingHeight));
        builder.child("text").attr("x", String.valueOf(targetBubblingTextOffsetX)).attr("y", String.valueOf(bubblingTextOffsetY)).attr("text-anchor", "middle").attr("font-size", String.valueOf(bubblingFontSize)).attr("fill", bubblingTextColor.toString()).text(targetBubblingText);
        builder.child("rect").attr("x", String.valueOf(symbolContainerOffsetX)).attr("y", String.valueOf(symbolContainerOffsetY)).attr("width", String.valueOf(symbolContainerWidth)).attr("height", String.valueOf(symbolHeight)).attr("fill", backgroundColor.toString());
        builder.child("use").attr("xlink:href", "all-material-design-symbols.svg#" + MaterialDesignSymbol.random().getId()).attr("fill", symbolColor.toString()).attr("x", String.valueOf(symbolOffsetX)).attr("y", String.valueOf(symbolOffsetY)).attr("width", String.valueOf(symbolWidth)).attr("height", String.valueOf(symbolHeight));
        builder.child("text").attr("x", String.valueOf(textOffsetX)).attr("y", String.valueOf(textOffsetY)).attr("font-size", String.valueOf(textFontSize)).attr("fill", textColor.toString()).text(activity.getEffect());
        builder.child("text").attr("x", String.valueOf(subscriptTextOffsetX)).attr("y", String.valueOf(subscriptTextOffsetY)).attr("font-size", String.valueOf(subscriptTextFontSize)).attr("fill", textColor.toString()).text(subscriptText);
    }

    public static class TimelinesLayoutStep {
        private String activity;
        private String timelinesBeforeAppend;
        private String timelinesAfterAppend;

        public TimelinesLayoutStep(String activity, String timelinesBeforeAppend) {
            this.activity = activity;
            this.timelinesBeforeAppend = timelinesBeforeAppend;
        }

        public String getActivity() {
            return activity;
        }

        public String getTimelinesBeforeAppend() {
            return timelinesBeforeAppend;
        }

        public String getTimelinesAfterAppend() {
            return timelinesAfterAppend;
        }

        public void setTimelinesAfterAppend(String timelinesAfterAppend) {
            this.timelinesAfterAppend = timelinesAfterAppend;
        }
    }
}
