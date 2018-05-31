<%@ page language="java" import="com.excel.entity.FileInfo" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%
    String htmlString  = (String)request.getAttribute("htmlString");
    List<String> fieldList = (ArrayList)request.getSession().getAttribute("fieldList");
%>
<jsp:include page="common/common.jsp"></jsp:include>

<form name="fieldInputForm">
<%=htmlString%>
</form>
<button name="createData">提交数据</button>

<script type="text/javascript">
    var fieldNames = <%=JSONArray.toJSONString(fieldList)%>;
    $(document).ready(function(){
        $('[name="createData"]').off().on('click',function(){
            $.ajax({
                url : "/excel/createData",
                dataType : "json",
                data : $('[name="fieldInputForm"]').serialize()
            }).done(function(result){

            });
        });
    });
</script>