package com.github.xuzw.activity.api;

import java.io.IOException;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;

import com.alibaba.fastjson.JSON;
import com.github.xuzw.activity.api.ActivityRepositoryFileFormat.LineType;
import com.github.xuzw.activity.api.ActivityRepositoryFileFormat.Metadata;
import com.github.xuzw.activity.model.Activity;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午5:45:13
 */
public class ActivityRepositoryWriter {
    private String path;
    private FileWriterWithEncoding writer;

    public ActivityRepositoryWriter(String path) throws IOException {
        this.path = path;
        writer = new FileWriterWithEncoding(path, ActivityRepositoryFileFormat.encoding, true);
    }

    public String getPath() {
        return path;
    }

    public void append(Activity activity) throws IOException {
        String activityLine = _toJsonLine(activity.toTreeMap(), LineType.activity);
        String metadataLine = _toJsonLine(_buildMetadata(activityLine).toTreeMap(), LineType.metadata_of_activity);
        StringBuffer sb = new StringBuffer();
        sb.append(metadataLine).append(ActivityRepositoryFileFormat.line_separator);
        sb.append(activityLine).append(ActivityRepositoryFileFormat.line_separator);
        writer.append(sb.toString());
    }

    public void close() {
        IOUtils.closeQuietly(writer);
    }

    private static String _toJsonLine(TreeMap<String, Object> treeMap, LineType lineType) {
        treeMap.put(LineType.property_key, lineType.getValue());
        return JSON.toJSONString(treeMap);
    }

    private static Metadata _buildMetadata(String line) {
        Metadata metadata = new Metadata();
        metadata.setSign(_sign(line));
        return metadata;
    }

    private static String _sign(String line) {
        return DigestUtils.md5Hex(line);
    }
}
