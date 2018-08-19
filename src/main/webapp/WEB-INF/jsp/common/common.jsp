<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.excel.entity.User" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/nb/css/amazeui.min.css"/>
<link rel="stylesheet" href="/nb/font-awesome/css/font-awesome.min.css">
<script src="/nb/js/jquery-3.3.1.min.js"></script>
<script src="/nb/js/app.js" type="text/javascript"></script>
<script src="/nb/js/amazeui.min.js" type="text/javascript"></script>
<script src="/nb/js/vue.js" type="text/javascript"></script>
<script src="/nb/js/jquery.validate.min.js" type="text/javascript"></script>
<%
    User user = (User)request.getSession().getAttribute("user");
    if(user != null){
        Boolean isSuperAdmin = "2".equals(user.getIsAdmin()) ? true : false;
%>
<div style="height: 40px">
<div style="float: left">
    <ul class="am-nav am-nav-pills">
        <li><a href="/nb/excel/moduleList">模块列表</a></li>
        <%if(isSuperAdmin){%>
        <li><a href="/nb/excel/assignPermission">权限分配</a></li>
        <%} else {%>
        <li  class="am-disabled"><a href="#">权限分配</a></li>
        <%}%>
    </ul>
</div>
    <div style="float: right;margin-right: 20px">欢迎：<%=user.getNickName()%>
        <span><a href="#" class="logiOut">退出登录</a></span>
    </div>
    <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
</div>
<script>
    $(document).ready(function(){
        $('.logiOut').off().on('click',function(){
            window.location.href = '/nb/excel/logOut'
        })
    })
</script>
<%}%>