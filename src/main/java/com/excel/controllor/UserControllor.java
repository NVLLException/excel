package com.excel.controllor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excel.entity.User;
import com.excel.service.DataService;
import com.excel.util.DataResponse;
import com.excel.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by mqjia on 6/1/2018.
 */
@Controller
@RequestMapping("/excel")
public class UserControllor {

    @Autowired
    DataService service;

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }

    @RequestMapping("/checkLogin")
    public ModelAndView checkLogin(HttpServletRequest request, HttpServletResponse response){
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        List<User> users = service.retrieveUser(loginName, password);

        DataResponse dataResponse = new DataResponse();
        if(users != null && users.size() > 0){
            dataResponse.succ();
            request.getSession().setAttribute("user", users.get(0));
        } else {
            dataResponse.addError("loginFailed");
        }
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/register")
    public ModelAndView regidter(){
        return new ModelAndView("/register");
    }

    @RequestMapping("/checkLoginName")
    public ModelAndView checkLoginName(HttpServletRequest request, HttpServletResponse response){
        String loginName = request.getParameter("loginName");
        Long loginNameCount = service.checkLoginName(loginName);
        Boolean isValid;
        if(loginNameCount != null && loginNameCount.intValue()==0){
            isValid = true;
        } else {
            isValid = false;
        }
        try {
            JSONUtil.ajaxSendResponse(response, isValid.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/doRegister")
    public ModelAndView doRegister(HttpServletRequest request, HttpServletResponse response){
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        String nikeName = request.getParameter("nikeName");
        User user = new User();
        user.setLoginName(loginName);
        user.setNikeName(nikeName);
        user.setPassword(password);
        service.createUser(user);
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }
}