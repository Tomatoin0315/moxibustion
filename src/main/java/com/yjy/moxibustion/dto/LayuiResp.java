package com.yjy.moxibustion.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author 杨景元
 * @date 2021/1/22 15:08
 */
public class LayuiResp<T> implements Serializable {
    private int code;
    private String msg;
    private Long count;
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static <T> LayuiResp<T> createResult(Long count, List<T> data) {
        LayuiResp<T> resp = new LayuiResp<>();
        resp.setCode(0);
        resp.setMsg("");
        resp.setCount(count);
        resp.setData(data);
        return resp;
    }
}
