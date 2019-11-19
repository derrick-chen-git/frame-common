package com.frame.common.enums;

/**
 * 返回响应编码
 * Created by derrick
 */
public enum ResultCodeEnum {
    
    SUCCESS("000000", "操作成功"),
    ERROR("999999", "操作失败"),
    PARAM_ERROR("999001", "参数错误"),
    SIGN_ERROR("999002", "签名错误"),
    TIMED_OUT("999003", "请求超时"),
    REPEAT_SUBMITTED("999004", "请不要重复提交");
    
    private String code;
    private String msg;
    
    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return getCode();
    }
}


