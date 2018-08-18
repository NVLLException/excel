package com.excel.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.excel.entity.FileInfo;
import com.excel.entity.User;
import com.excel.service.DataService;
import com.excel.util.DataResponse;
import com.excel.util.ExcelUtil;
import com.excel.util.JSONUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by mqjia on 5/31/2018.
 */
@Controller
@RequestMapping("/excel")
public class FormControllor {
    @Autowired
    private DataService service;

    @RequestMapping("/configForm")
    public ModelAndView formBuild(HttpServletRequest request){
        try {
            String htmlPath = ResourceBundle.getBundle("domain").getString("htmlPath");
            ModelAndView modelAndView = new ModelAndView("/configForm");
            FileInfo fileInfo = (FileInfo) request.getSession().getAttribute("fileInfo");
            File htmlFile = new File(htmlPath + fileInfo.getHtmlFileName());
            FileInputStream in = new FileInputStream(htmlFile);
            byte[] bytes = new byte[((Long)htmlFile.length()).intValue()];
            in.read(bytes);
            String htmlString = new String(bytes);
            request.setAttribute("htmlString",htmlString);
            return modelAndView;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/createData")
    public ModelAndView createData(HttpServletRequest request){
        FileInfo fileInfo = (FileInfo)request.getSession().getAttribute("fileInfo");
        List<String> fieldNames = (ArrayList)request.getSession().getAttribute("fieldList");
        List<String> fieldValues = new ArrayList();
        for(String fieldName : fieldNames){
            String value = request.getParameter(fieldName);
            value = value != null ? value : "";
            fieldValues.add(value);
        }
        User user = (User)request.getSession().getAttribute("user");
        String fieldDataSql  = ExcelUtil.generateFieldDataSql(fileInfo.getTableName(), fieldNames, fieldValues, user);
        service.executeSql(fieldDataSql);
        return null;
    }

    @RequestMapping("/formList")
    public ModelAndView formList(HttpServletRequest request){
        String moduleId = (String)request.getSession().getAttribute("moduleId");
        ModelAndView modelAndView = new ModelAndView("/formList");
        modelAndView.addObject("moduleId", moduleId);
        return modelAndView;
    }

    @RequestMapping("/getFormList")
    public ModelAndView getFormList(HttpServletRequest request, HttpServletResponse response){
        String moduleId = (String)request.getSession().getAttribute("moduleId");
        List<Map> list = service.retrieveAllFileInfo(moduleId);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setData(list);
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/getFormInfoGroupByUser")
    public ModelAndView getFormInfoGroupByUser(HttpServletRequest request, HttpServletResponse response){
        String formId = request.getParameter("formId");
        String tableName = request.getParameter("tableName");
        List<Map> list = service.retrieveFileInfoGroupByUser(tableName);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setData(list);
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/retrieveFileInfoDataByUserId")
    public ModelAndView retrieveFileInfoDataByUserId(HttpServletRequest request, HttpServletResponse response){
        String tableName = request.getParameter("tableName");
        String userId = request.getParameter("userId");
        List<Map> list = service.retrieveFileInfoDataByUserId(tableName, userId);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setData(list);
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/deleteFileInfoData")
    public ModelAndView deleteFileInfoData(HttpServletRequest request, HttpServletResponse response){
        String tableName = request.getParameter("tableName");
        String id = request.getParameter("id");

        return null;
    }
}
