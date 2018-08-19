package com.excel.controllor;

import com.excel.entity.User;
import com.excel.service.DataService;
import com.excel.util.DataResponse;
import com.excel.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */
@Controller
@RequestMapping("/excel")
public class ModuleControllor {
    @Autowired
    private DataService service;

    @RequestMapping("/moduleList")
    public ModelAndView moduleList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/moduleList");
        return modelAndView;
    }

    @RequestMapping("/createModule")
    public ModelAndView createModule(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/createModule");
        return modelAndView;
    }

    @RequestMapping("/addModule")
    public ModelAndView addModule(HttpServletRequest request, HttpServletResponse response) {
        String moduleName = request.getParameter("moduleName");
        service.addModule(moduleName);
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/getModuleList")
    public ModelAndView getModuleList(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");
        List modules = null;
        if(!"1".equals(user.getIsAdmin())) {
             modules = service.getModuleList();
        } else {
            modules = service.retrieveModuleListByUserId(user.getId().toString());
        }
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        dataResponse.setData(modules);
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/deleteModule")
    public ModelAndView deleteModule(HttpServletRequest request, HttpServletResponse response) {
        String moduleId = request.getParameter("id");
        service.deleteModule(moduleId);
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }
}
