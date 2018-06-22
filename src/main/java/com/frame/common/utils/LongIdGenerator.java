package com.frame.common.utils;

/**
 * Created by Administrator on 2018/6/13.
 */
public class LongIdGenerator {
    private static final SnowflakeIdWorker woker = new SnowflakeIdWorker(0,0);
    public static long getLongId(){
        return woker.nextId();
    }
}
