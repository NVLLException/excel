package com.excel.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
    public static void ajaxSendResponse(HttpServletResponse response, DataResponse dataResponse){
        try {
            ajaxSendResponse(response, JSONObject.toJSONString(dataResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ajaxSendResponse(HttpServletResponse response, String content) throws IOException{
        if(content == null)
            content = "";
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(content);
        writer.flush();
    }
}
