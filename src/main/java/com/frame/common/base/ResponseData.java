package com.frame.common.base;


import com.frame.common.base.BaseObject;

/**
 * Created by Administrator on 2018/6/19.
 */
public class ResponseData<T>  extends BaseObject {
    private int code;
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public ResponseData(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
