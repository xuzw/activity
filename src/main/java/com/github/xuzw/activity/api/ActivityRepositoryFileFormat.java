package com.github.xuzw.activity.api;

import java.util.TreeMap;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午5:43:41
 */
public class ActivityRepositoryFileFormat {
    public static final String line_separator = "\n";
    public static final String encoding = "utf-8";

    public static class Metadata {
        private String sign;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public TreeMap<String, Object> toTreeMap() {
            TreeMap<String, Object> treeMap = new TreeMap<>();
            treeMap.put("sign", sign);
            return treeMap;
        }
    }

    public static enum LineType {
        activity("activity"), metadata_of_activity("metadata_of_activity");
        public static final String property_key = "t";
        private String value;

        private LineType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static LineType parse(String value) {
            for (LineType t : values()) {
                if (t.value.equals(value)) {
                    return t;
                }
            }
            return null;
        }
    }
}
