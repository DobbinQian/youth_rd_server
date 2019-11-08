package com.youth_rd.demo.Interceptor;

import com.alibaba.fastjson.JSONArray;
import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 基于URL实现的拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(null==user){
            try {
                writer = response.getWriter();
                writer.print(JSONArray.toJSON(ServerResponse.createByNeedLogin().toString()));

            } catch (IOException e) {

            } finally {
                if (writer != null)
                    writer.close();
            }
            return false;
        }
        return true;
    }
}