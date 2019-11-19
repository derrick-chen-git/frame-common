package com.frame.common.exception;


/**
 * @author xiaoqian.wen
 * @version v1.0
 * @desc 财务统一异常类
 * @date 2018/9/13 20:07
 */
public class BusinessException extends Exception {
    
    public BusinessException() {
        super();
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BusinessException(Throwable cause) {
        super(cause);
    }
    
    public BusinessException(final String code) {
        super(code);
    }

    
}
