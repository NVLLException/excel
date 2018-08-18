package com.excel.controllor;

import com.excel.service.DataService;
import com.excel.util.DataResponse;
import com.excel.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/excel")
public class PermissionControllor {
    @Autowired
    private DataService service;

    @RequestMapping("/assignPermission")
    public ModelAndView assignPermission(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/assignPermission");
        List userList = service.executeQuerySql("select * from user where isAdmin=1");
        List moduleList = service.executeQuerySql("select * from module");
        List<Map<Object,Object>> permissons = service.executeQuerySql("select concat_ws('_',moduleId,userId) as permission from permission");
        List<String> permissionList = new ArrayList();
        for(Map<Object,Object> map: permissons){
            permissionList.add((String)map.get("permission"));
        }
        modelAndView.addObject("userList", userList);
        modelAndView.addObject("moduleList", moduleList);
        modelAndView.addObject("permissionList", permissionList);
        return modelAndView;
    }

    @RequestMapping("/doAssignPermission")
    public ModelAndView doAssignPermission(String[] moduleUserId, HttpServletRequest request, HttpServletResponse response) {
        service.executeSql("delete from permission");
        for(String moduleUser : moduleUserId){
            String moduleId = moduleUser.split("_")[0];
            String userId = moduleUser.split("_")[1];
            service.executeSql("insert into permission(moduleId,userId)values("+moduleId+","+userId+")");
        }
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }
}
