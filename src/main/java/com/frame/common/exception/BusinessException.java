package com.frame.common.exception;


/**
 * @author derrick
 * @version v1.0
 * @desc 统一异常类
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
