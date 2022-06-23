package com.zc.store.util;

import lombok.Data;

import java.io.Serializable;

/**
 *  Json格式的数据进行响应
 */
@Data
public class JsonResult implements Serializable {

    //状态码
    private Integer state;
    //描述信息
    private String message;
    //数据
    private Object data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, Object data) {
        this.state = state;
        this.data = data;
    }
}
