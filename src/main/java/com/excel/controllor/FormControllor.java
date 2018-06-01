package com.excel.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excel.entity.FileInfo;
import com.excel.entity.User;
import com.excel.service.DataService;
import com.excel.util.ExcelUtil;
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
    private DataService fileInfoService;

    @RequestMapping("/configForm")
    public ModelAndView formBuild(HttpServletRequest request){
        try {
            String htmlPath = "resources/html/";
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
        User user = new User();//todo
        String fieldDataSql  = ExcelUtil.generateFieldDataSql(fileInfo.getTableName(), fieldNames, fieldValues, user);
        fileInfoService.executeSql(fieldDataSql);
        return null;
    }
}
