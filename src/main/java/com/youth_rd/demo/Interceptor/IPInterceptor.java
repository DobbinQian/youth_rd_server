package com.youth_rd.demo.Interceptor;

import com.youth_rd.demo.tools.IPUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * CREATE BY funnyZpC ON 2018/5/3
 **/

public class IPInterceptor implements HandlerInterceptor {
    private static final Logger LOG= Logger.getLogger(IPInterceptor.class.getName());


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //过滤ip,若用户在白名单内，则放行
        String ipAddress= IPUtils.getRealIP(request);
        LOG.info("USER IP ADDRESS IS =>"+ipAddress);
        if(!ipAddress.equals("192.168.43.22"))
            return false;
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}