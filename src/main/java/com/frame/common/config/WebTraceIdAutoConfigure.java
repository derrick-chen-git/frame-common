package com.frame.common.config;

import com.frame.common.utils.UUIDShort;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc 使用springboot的web项目启用traceId打印
 * @version v1.0
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "frame.finance.trace", name = "enable", havingValue = "true")
public class WebTraceIdAutoConfigure {

    @Bean
    public TraceInterceptor getTraceInterceptor() {
        return new TraceInterceptor();
    }

    @Bean
    public FinanceWebMvcConfigure initWebMVCConfigure(){
        return new FinanceWebMvcConfigure();
    }

    public class FinanceWebMvcConfigure extends WebMvcConfigurerAdapter{

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            InterceptorRegistration addInterceptor = registry.addInterceptor(getTraceInterceptor());
            // 拦截配置
            addInterceptor.addPathPatterns("/**");
        }
    }

    public class TraceInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            // "traceId"
            MDC.put("traceId", UUIDShort.generate());
            return true;
        }

    }

}
