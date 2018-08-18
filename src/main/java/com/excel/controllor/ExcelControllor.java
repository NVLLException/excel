package com.excel.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excel.entity.FileInfo;
import com.excel.entity.User;
import com.excel.service.DataService;
import com.excel.util.DataResponse;
import com.excel.util.ExcelUtil;
import com.excel.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by mqjia on 5/31/2018.
 */
@Controller
@RequestMapping("/excel")//request path
public class ExcelControllor {
    @Autowired
    private DataService service;

    @RequestMapping("/upload")//requestpath
    public ModelAndView upload(@RequestParam String moduleId, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/upload");//forward jsp file
        request.getSession().setAttribute("moduleId", moduleId);
        return modelAndView;
    }

    @RequestMapping("/doUpload")
    public ModelAndView doUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                                 HttpServletResponse response){
        try {
            String moduleId = (String)request.getSession().getAttribute("moduleId");
            String fileName = file.getName();
            byte[] bytes = file.getBytes();
            Long now = new Date().getTime();
            ResourceBundle rb = ResourceBundle.getBundle("domain");
            String excelPath = rb.getString("excelPath");
            String htmlPath = rb.getString("htmlPath");
            String randomExcelFileName = now + ".xls";
            String randomHtmlFileName = now + ".html";

            saveExcelFile(excelPath + randomExcelFileName, bytes);



            Map<String,Object> result = ExcelUtil.parseExcel2Form(excelPath + randomExcelFileName);
            generateAndSaveHtmlFile((StringBuffer)result.get("html"), htmlPath + randomHtmlFileName);
            FileInfo fileInfo = constructAndPersistFileInfo(request, fileName, now, randomExcelFileName, randomHtmlFileName);

            //根据excel的空白字段生成相应的表结构
            String tableSql = ExcelUtil.generateTableSql(fileInfo.getTableName(),(ArrayList)result.get("fieldList"));
            service.executeSql(tableSql);

            request.getSession().setAttribute("fileInfo", fileInfo);
            request.getSession().setAttribute("fieldList", result.get("fieldList"));
            DataResponse dataResponse = new DataResponse();
            dataResponse.succ();
            JSONUtil.ajaxSendResponse(response,dataResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void saveExcelFile(String filePath, byte[] bytes) throws Exception{
        File f_excel = new File(filePath);
        FileOutputStream out_excel = new FileOutputStream(f_excel);
        out_excel.write(bytes);
        out_excel.flush();
        out_excel.close();
    }

    private void generateAndSaveHtmlFile(StringBuffer htmlBuffer, String htmlPath) throws Exception{
        File f_html = new File(htmlPath);
        FileOutputStream out_html = new FileOutputStream(f_html);
        byte[] htmlBytes = htmlBuffer.toString().getBytes();
        out_html.write(htmlBytes);
        out_html.flush();
        out_html.close();
    }

    private FileInfo constructAndPersistFileInfo(HttpServletRequest request, String fileName, Long createTime, String randomExcelFileName, String randomHtmlFileName) throws Exception{
        FileInfo fileInfo = new FileInfo();
        String formName = request.getParameter("formName");
        User user = (User) request.getSession().getAttribute("user");
        String moduleId =  (String)request.getSession().getAttribute("moduleId");
        fileInfo.setModuleId(moduleId);
        fileInfo.setUserId(user.getId());
        fileInfo.setFileName(!StringUtils.isEmpty(formName) ? formName : fileName);
        fileInfo.setTableName("e_"+ Math.abs(new Random().nextInt()));
        fileInfo.setCreateTime(new java.sql.Date(createTime));
        fileInfo.setExcelFileName(randomExcelFileName);
        fileInfo.setHtmlFileName(randomHtmlFileName);
        service.createFileInfo(fileInfo);
        return fileInfo;
    }

    private void generateTable(List<String> fileds, FileInfo fileInfo) throws Exception{

    }
}
