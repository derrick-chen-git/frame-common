package com.frame.common.aop;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.PrintWriter;

import com.frame.common.base.ResponseData;
import com.frame.common.config.SignAuthProperties;
import com.frame.common.enums.ResultCodeEnum;
import com.frame.common.utils.SignatureHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 外部调用权限验证
 */
@Slf4j
@Aspect
public class SignAuthorization {
    
    private SignAuthProperties signAuthProperties;
    
    public SignAuthorization(SignAuthProperties signAuthProperties) {
        this.signAuthProperties = signAuthProperties;
    }
    
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
    @Pointcut("@annotation(com.frame.common.annotation.SignAuth)")
    public void thirdAuth() {
    
    }
    
    @Around("thirdAuth()")
    public Object around(ProceedingJoinPoint point) {
        // 接收到请求，记录请求内容
        log.info("MethodParamValidation.doBefore()");
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        
        boolean auth = authValidate(request, response);
        if (auth) {
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 权限验证
     */
    public boolean authValidate(HttpServletRequest request, HttpServletResponse response) {
        log.info("验签开始。。。。。");
        String timestamp = request.getHeader("X-Timestamp");
        String mobile = request.getHeader("X-User-Mobile");
        String partnerName = request.getHeader("X-Partner-Name");
        String signData = request.getHeader("X-Sign-Data");
        
        log.info("请求信息：X-Timestamp:{},X-User-Mobile:{},X-Partner-Name:{},X-Sign-Data:{}",
            timestamp, mobile, partnerName, signData);
        
        ResponseData responseVo = new ResponseData(ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getMsg());
        if (timestamp == null || mobile == null || partnerName == null || signData == null) {
            log.info("请求参数非法");
            responseVo.setCode(ResultCodeEnum.PARAM_ERROR.getCode());
            responseVo.setMsg(ResultCodeEnum.PARAM_ERROR.getMsg());
            responseResult(response, responseVo);
            return false;
        }
        
        try {
            //签名失效时间
            long signExpireTime = Long.parseLong(timestamp) + 60000;
            long currentTime = System.currentTimeMillis();
            if (signExpireTime < currentTime) {
                log.info("请求超时 : signTime:{} ms, currentTime : {} ms", signExpireTime, currentTime);
                responseVo.setCode(ResultCodeEnum.TIMED_OUT.getCode());
                responseVo.setMsg(ResultCodeEnum.TIMED_OUT.getMsg());
                responseResult(response, responseVo);
                return false;
            } else {
                SignatureHelper signatureHelper = SignatureHelper
                    .getInstance(signAuthProperties.getThirdPublicKey(), signAuthProperties.getPrivateKey());
                boolean verify = signatureHelper.verify(partnerName + "&" + mobile + "&" + timestamp, signData);
                if (!verify) {
                    log.error("验签失败");
                    responseVo.setCode(ResultCodeEnum.SIGN_ERROR.getCode());
                    responseVo.setMsg(ResultCodeEnum.SIGN_ERROR.getMsg());
                    responseResult(response, responseVo);
                    return false;
                }
            }
        } catch (Exception ex) {
            responseVo.setCode(ResultCodeEnum.SIGN_ERROR.getCode());
            responseVo.setMsg(ResultCodeEnum.SIGN_ERROR.getMsg());
            log.error("验签失败,{}", ex);
            responseResult(response, responseVo);
            return false;
        }
        log.info("验签结束。。。。。");
        
        return true;
    }
    
    
    public void responseResult(HttpServletResponse response, ResponseData result) {
        try {
            String jsonString = JSON.toJSONString(result);
            log.info("返回结果：{}", jsonString);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(jsonString);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
    
}
