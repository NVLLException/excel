package com.excel.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by mqjia on 6/1/2018.
 */
@Controller
@RequestMapping("/excel")
public class UserControllor {
    @RequestMapping("/404")
    public ModelAndView page404(){
        ModelAndView modelAndView = new ModelAndView("/404");
        return modelAndView;
    }
}