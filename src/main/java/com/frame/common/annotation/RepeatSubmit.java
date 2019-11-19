package com.frame.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author derrick
 * @version v1.0
 * @desc 防重复提交注解
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {
    
    String key() default "";

    /**
     * 锁定时间 s
     * @return
     */
    int lockTime() default 5;

    /**
     * 加锁等待时间 s
     * @return
     */
    int waiTime() default 0;
    
}
