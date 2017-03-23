package com.github.xuzw.activity.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xuzw.activity.api.ActivityRepositoryFileFormat.LineType;
import com.github.xuzw.activity.api.ActivityRepositoryFileFormat.Metadata;
import com.github.xuzw.activity.model.Activity;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午6:22:30
 */
public class ActivityRepositoryReader {
    private String path;
    private FileReader reader;
    private BufferedReader bReader;

    public ActivityRepositoryReader(String path) throws FileNotFoundException {
        this.path = path;
        reader = new FileReader(path);
        bReader = new BufferedReader(reader);
    }

    public String getPath() {
        return path;
    }

    public Activity read() throws IOException, ActivityRepositoryFileFormatException {
        String line = bReader.readLine();
        if (line == null) {
            return null;
        }
        JSONObject json = JSONObject.parseObject(line);
        LineType lineType = LineType.parse(json.getString(LineType.property_key));
        if (lineType == LineType.metadata_of_activity) {
            Metadata metadata = JSON.toJavaObject(json, Metadata.class);
            String nextLine = bReader.readLine();
            if (!_isValidSign(metadata.getSign(), nextLine)) {
                throw new ActivityRepositoryFileFormatException("invalid sign");
            }
            return JSONObject.parseObject(nextLine, Activity.class);
        } else if (lineType == LineType.activity) {
            return JSON.toJavaObject(json, Activity.class);
        } else {
            return read();
        }
    }

    public void close() {
        IOUtils.closeQuietly(bReader);
        IOUtils.closeQuietly(reader);
    }

    private static boolean _isValidSign(String sign, String line) {
        return sign.equals(DigestUtils.md5Hex(line));
    }
}
