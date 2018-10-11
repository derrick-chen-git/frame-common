package com.frame.common.config.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("!@annotation(com.frame.common.config.annotation.Master) " +
            "&& (execution(* com..*.*Mapper.select*(..)) " +
            "|| execution(* com..*.*Mapper.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.frame.common.config.annotation.Master) " +
            "|| execution(* com..*.*Mapper.insert*(..)) " +
            "|| execution(* com..*.*Mapper.add*(..)) " +
            "|| execution(* com..*.*Mapper.update*(..)) " +
            "|| execution(* com..*.*Mapper.edit*(..)) " +
            "|| execution(* com..*.*Mapper.delete*(..)) " +
            "|| execution(* com..*.*Mapper.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }


    /**
     * 另一种写法：if...else...  判断哪些需要读从数据库，其余的走主数据库
     */
//    @Before("execution(* com.cjs.example.service.impl.*.*(..))")
//    public void before(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
//            DBContextHolder.slave();
//        }else {
//            DBContextHolder.master();
//        }
//    }
}
