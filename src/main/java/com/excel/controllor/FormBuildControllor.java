package com.excel.controllor;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;

import com.excel.entity.FileInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by mqjia on 5/31/2018.
 */
@Controller
@RequestMapping("/excel")
public class FormBuildControllor {
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
}
