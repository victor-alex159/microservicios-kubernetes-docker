package com.victor.microserviciousuarios.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbstractResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;

    private List<T> dataList;
    private String msg;
    private boolean success;

    public AbstractResponse(){}

    public AbstractResponse(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }
    public AbstractResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
