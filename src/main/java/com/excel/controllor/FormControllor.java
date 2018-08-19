package com.excel.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

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
import org.springframework.util.StringUtils;
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


    @RequestMapping("/addData")
    public ModelAndView addData(HttpServletRequest request){
        String infoId = request.getParameter("infoId");
        List<FileInfo> files = service.retrieveFileInfo(infoId);
        request.getSession().setAttribute("fileInfo", files.get(0));
        ModelAndView modelAndView = new ModelAndView("/configForm");
        try {
            String htmlPath = ResourceBundle.getBundle("domain").getString("htmlPath");
            File htmlFile = new File(htmlPath + files.get(0).getHtmlFileName());
            FileInputStream in = new FileInputStream(htmlFile);
            byte[] bytes = new byte[((Long) htmlFile.length()).intValue()];
            in.read(bytes);
            String htmlString = new String(bytes);
            request.setAttribute("htmlString", htmlString);
        } catch (Exception e){
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/configForm");
        getFormData(modelAndView, request);
        return modelAndView;
    }

    @RequestMapping("/viewForm")
    public ModelAndView viewForm(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/formView");
        getFormData(modelAndView, request);
        return modelAndView;
    }

    private void getFormData(ModelAndView modelAndView, HttpServletRequest request){
        String infoId = request.getParameter("infoId");
        String dataId = request.getParameter("dataId");
        List<FileInfo> files = service.retrieveFileInfo(infoId);
        request.getSession().setAttribute("fileInfo", files.get(0));
        String tableName = files.get(0).getTableName();
        List datas = service.executeQuerySql("select * from " + tableName + " where id=" + dataId);
        request.getSession().setAttribute("dataId", dataId);
        modelAndView.addObject("datas", datas);
        modelAndView.addObject("isNew", "0");
        try {
            String htmlPath = ResourceBundle.getBundle("domain").getString("htmlPath");
            FileInfo fileInfo = (FileInfo) files.get(0);
            File htmlFile = new File(htmlPath + fileInfo.getHtmlFileName());
            FileInputStream in = new FileInputStream(htmlFile);
            byte[] bytes = new byte[((Long) htmlFile.length()).intValue()];
            in.read(bytes);
            String htmlString = new String(bytes);
            request.setAttribute("htmlString", htmlString);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    @RequestMapping("/createData")
    public ModelAndView createData(HttpServletRequest request, HttpServletResponse response){
        FileInfo fileInfo = (FileInfo)request.getSession().getAttribute("fileInfo");
        List<String> fieldNames = (ArrayList)request.getSession().getAttribute("fieldList");
        if(fieldNames == null){
            fieldNames = new ArrayList<String>();
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()){
                fieldNames.add(enumeration.nextElement());
            }
        }
        List<String> fieldValues = new ArrayList();
        for(String fieldName : fieldNames){
            String value = request.getParameter(fieldName);
            value = value != null ? value : "";
            fieldValues.add(value);
        }
        User user = (User)request.getSession().getAttribute("user");
        String fieldDataSql  = ExcelUtil.generateFieldDataSql(fileInfo.getTableName(), fieldNames, fieldValues, user);
        service.executeSql(fieldDataSql);
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/doEditData")
    public ModelAndView doEditData(HttpServletRequest request , HttpServletResponse response){
        FileInfo fileInfo = (FileInfo)request.getSession().getAttribute("fileInfo");
        Enumeration<String> enumeration = request.getParameterNames();
        List<String> fieldNames = new ArrayList<String>();
        while (enumeration.hasMoreElements()){
            fieldNames.add(enumeration.nextElement());
        }
        List<String> fieldValues = new ArrayList();
        for(String fieldName : fieldNames){
            String value = request.getParameter(fieldName);
            value = value != null ? value : "";
            fieldValues.add(value);
        }
        User user = (User)request.getSession().getAttribute("user");
        String dataId = (String)request.getSession().getAttribute("dataId");
        String fieldDataSql  = ExcelUtil.editFileDataSql(fileInfo.getTableName(), fieldNames, fieldValues, user, dataId);
        service.executeSql(fieldDataSql);
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/formList")
    public ModelAndView formList(HttpServletRequest request){
        String newModuleId = request.getParameter("moduleId");
        if(!StringUtils.isEmpty(newModuleId))
            request.getSession().setAttribute("moduleId", newModuleId);
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
        User user = (User)request.getSession().getAttribute("user");
        List<Map> list;
        if(!"1".equals(user.getIsAdmin()) && !"2".equals(user.getIsAdmin())){
            list = service.retrieveFileInfoByUserId(tableName, user.getId().toString());
        } else {
            list = service.retrieveFileInfoGroupByUser(tableName);
        }
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

    @RequestMapping("/deleteForm")
    public ModelAndView deleteForm(HttpServletRequest request, HttpServletResponse response){
        String dataId = request.getParameter("dataId");
        String tableName = request.getParameter("tableName");
        service.executeQuerySql("delete from " + tableName + " where id =" + dataId);
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }

    @RequestMapping("/deleteFormStruct")
    public ModelAndView deleteFormStruct(HttpServletRequest request, HttpServletResponse response){
        String infoId = request.getParameter("infoId");
        service.executeSql("delete from fileinfo where id=" + infoId);
        DataResponse dataResponse = new DataResponse();
        dataResponse.succ();
        JSONUtil.ajaxSendResponse(response, dataResponse);
        return null;
    }
}
