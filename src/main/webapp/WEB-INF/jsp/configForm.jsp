<%@ page language="java" import="com.excel.entity.FileInfo" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%
    String htmlString  = (String)request.getAttribute("htmlString");
    List<String> fieldList = (ArrayList)request.getSession().getAttribute("fieldList");
%>
<jsp:include page="common/common.jsp"></jsp:include>

<div>
<form name="fieldInputForm">
<%=htmlString%>
</form>
    <div class="am-form-group">
        <button name="createData" class="am-btn am-btn-default">提交数据</button>
        <button name="returnList" class="am-btn am-btn-default">返回表单列表</button>
    </div>
</div>

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

        $('[name="returnList"]').off().on('click',function(){
            window.location.href = "/excel/formList";
        });
    });
</script>