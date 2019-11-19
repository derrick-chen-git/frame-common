package com.frame.common.aop;



import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.frame.common.annotation.RepeatSubmit;
import com.frame.common.enums.ResultCodeEnum;
import com.frame.common.exception.BusinessException;
import com.frame.starter.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 防重复提交拦截
 * Created by derrick
 */
@Slf4j
@Aspect
public class RepeatSubmittedAspect {
    
    @Autowired
    private RedisUtils redisUtils;
    
    /**
     * 定义一个切入点.
     * 解释下：
     * <p>
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("@annotation(com.frame.common.annotation.RepeatSubmit)")
    public void repeatSubmit() {
    
    }
    
    @Around("repeatSubmit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        
        //获取注解
        RepeatSubmit repeatSubmit = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(RepeatSubmit.class);
        String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        String lockKey = getLockKey(paramNames, paramValues, repeatSubmit.key());
        boolean result = redisUtils.acquireLock(lockKey,repeatSubmit.waiTime(), repeatSubmit.lockTime(), TimeUnit.SECONDS);
        if (result) {
            return joinPoint.proceed();
        }else{
            log.info("RepeatSubmittedAspect,{}重复提交，直接返回。", lockKey);
            throw new BusinessException(ResultCodeEnum.REPEAT_SUBMITTED.getMsg());
        }
    }
    
    /**
     * 把所有的参数加拼在一起，生成的唯一的key
     */
    public String getLockKey(String[] paramNames, Object[] paramValues, String lockKey) {
        
        //获取所有的参数
        Map<String, Object> map = objectToMap(paramNames, paramValues);

        String importFileRole = "\\$\\{[^}]+\\}";
        Pattern pattern = Pattern.compile(importFileRole);
        Matcher matcher = pattern.matcher(lockKey);//进行匹配
        while (matcher.find()) {
            String keyWord = matcher.group();
            String fieldName = keyWord.replace("${","").replace("}","");
            lockKey = lockKey.replace(keyWord, String.valueOf(map.get(fieldName)));
        }
        //String strLockKey = ShortStringUtil.shortText(lockKey);
        log.info("RepeatSubmittedAspect:lockKey:{}", lockKey);
        return lockKey;
    }
    
    /**
     * 获取利用反射获取类里面的值和名称
     */
    public Map<String, Object> objectToMap(String[] paramNames, Object[] paramValues) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for(int i = 0; i < paramValues.length; i ++) {
                Object paramValue = paramValues[i];
                Class<?> clazz = paramValue.getClass();
                if(paramValue instanceof Map){
                    map.putAll((Map)paramValue);
                }else if(clazz.getClassLoader() != null){
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        map.put(fieldName, field.get(paramValue));
                    }
                }else {
                    map.put(paramNames[i], paramValue);
                }
            }
        }catch (Exception ex){
            log.info("RepeatSubmittedAspect解析字段错误{}", ex);
        }
        return map;
    }

    /*
    public static void main(String[] args) {

        String[] params = {"name1", "name2", "name3","name4","name5"};
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResultCodeEnum.SUCCESS.getCode());
        responseVo.setMessage(ResultCodeEnum.SUCCESS.getMsg());
        Object[] objects = new Object[5];
        objects[0] = new Long("100");
        objects[1] = 4;
        objects[2] = responseVo;
        objects[3] = "HelloWorld";
        Map<String,String> values = new HashMap<String, String>();
        values.put("key1", "key1");
        values.put("key2", "key2");
        values.put("key3", "key3");
        objects[4] = values;
        RepeatSubmittedAspect repeat = new RepeatSubmittedAspect();
        Map valueMap = repeat.objectToMap(params, objects);
        System.out.println(valueMap);
    }*/
}
