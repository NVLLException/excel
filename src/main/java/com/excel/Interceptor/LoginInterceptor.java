package com.excel.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excel.entity.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by mqjia on 6/1/2018.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            //not login
            if("/excel/login".equals(path)||
                    "/excel/checkLogin".equals(path)||
                    "/excel/register".equals(path)||
                    "/excel/checkLoginName".equals(path)||
                    "/excel/doRegister".equals(path)||
                    "/excel/404".equals(path)){
                return true;
            } else {
                //禁止访问
                if(!"/excel/login".equals(path))
                    response.sendRedirect("/nb/excel/404");
                return false;
            }
        } else {
            //logged
            //todo check permission
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
