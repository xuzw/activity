package com.github.xuzw.activity.api;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午6:36:46
 */
public class UnknownEntityException extends Exception {
    private static final long serialVersionUID = 1L;

    public UnknownEntityException(String message) {
        super(message);
    }
}
