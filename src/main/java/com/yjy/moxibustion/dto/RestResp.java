package com.yjy.moxibustion.dto;

import java.io.Serializable;

/**
 * @author 杨景元
 * @date 2021/1/19 15:53
 */
public class RestResp<T> implements Serializable {
    private static final int STATUS_SUCCESS = 200;
    private static final int STATUS_FAIL = 500;

    private int status;
    private String message;
    private T data;

    public static RestResp success() {
        return createResult(STATUS_SUCCESS, "成功", null);
    }

    public static RestResp success(String message) {
        return createResult(STATUS_SUCCESS, message, null);
    }

    public static <T> RestResp<T> success(String message, T data) {
        return createResult(STATUS_SUCCESS, message, data);
    }

    public static RestResp fail() {
        return createResult(STATUS_FAIL, "失败", null);
    }

    public static RestResp fail(String message) {
        return createResult(STATUS_FAIL, message, null);
    }

    public static <T> RestResp<T> fail(String message, T data) {
        return createResult(STATUS_FAIL, message, data);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    private static <T> RestResp<T> createResult(int status, String message, T data) {
        RestResp<T> resp = new RestResp<T>();
        resp.setStatus(status);
        resp.setMessage(message);
        resp.setData(data);
        return resp;
    }
}
