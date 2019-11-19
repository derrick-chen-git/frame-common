package com.frame.common.base;


import com.frame.common.enums.ResultCodeEnum;
import lombok.Data;

/**
 * Created by derrick on 2018/6/19.
 */
@Data
public  class ResponseData<T>  extends BaseObject {
    private String code;
    private String msg;
    private T data;

    public ResponseData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public static ResponseData success(){
        return new ResponseData<>(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMsg());
    }

    public ResponseData(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseData error(String msg){
        return new ResponseData<>(ResultCodeEnum.ERROR.getCode(),ResultCodeEnum.ERROR.getMsg());
    }
    
}
