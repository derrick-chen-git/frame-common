package com.frame.common.aop;

import com.alibaba.fastjson.JSON;
import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 实现Web层的请求参数，返回结果，耗时时间打印
 */
@Slf4j
@Aspect
public class WebLogAspect {

    /**
     * 定义一个切入点.
     * 解释下：
     * <p>
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        MDC.put("time", System.currentTimeMillis()+"");
        // 接收到请求，记录请求内容
        log.info("WebLogAspect.doBefore()");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("请求URL : " + request.getRequestURL().toString());
        log.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));

        //获取所有参数方法一：
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            log.info(paraName + ": " + request.getParameter(paraName));
        }
    }

    @AfterReturning(value = "webLog()", returning = "resultMap")
    public void doAfterReturning(JoinPoint joinPoint, Object resultMap) {
        // 处理完请求，返回内容
        log.info("WebLogAspect.doAfterReturning()");
        String json = JSON.toJSONString(resultMap);
        if(StringUtils.isNotBlank(json) && json.length() > 1000){
            json = json.substring(0, 1000);
        }
        log.info("返回结果：{}", json);
        log.info("耗时（毫秒） : " + (System.currentTimeMillis() - Long.parseLong(MDC.get("time"))));
        MDC.remove("time");
    }

}