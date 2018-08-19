<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="org.springframework.util.StringUtils" %>
<jsp:include page="common/common.jsp"></jsp:include>
<%
    String htmlString  = (String)request.getAttribute("htmlString");
    List<String> fieldList = (ArrayList)request.getSession().getAttribute("fieldList");
    List<Map<Object,Object>> datas = (ArrayList)request.getAttribute("datas");
%>
<div>
    <form name="fieldInputForm">
        <%=htmlString%>
    </form>
</div>
<script>
    //populate data
    var datas = JSON.parse('<%=JSON.toJSON(datas).toString()%>');
    for(var key in datas[0]){
        $('[name="' + key + '"]').val(datas[0][key]);
    }

    $('[name="editData"]').off().on('click', function(){
        $.ajax({
            url : "/nb/excel/doEditData",
            dataType : "json",
            data : $('[name="fieldInputForm"]').serialize()
        }).done(function(result){
            alert('修改成功!');
        });
    });
    $('[name^="fieldName_"]').attr("disabled",'true');
</script>
<style>
    textarea{
        background: white!important;
    }
</style>