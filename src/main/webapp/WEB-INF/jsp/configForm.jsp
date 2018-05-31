<%@ page language="java" import="com.excel.entity.FileInfo" pageEncoding="UTF-8"%>
<%
    String htmlString  = (String)request.getAttribute("htmlString");
%>
<jsp:include page="common/common.jsp"></jsp:include>
<%=htmlString%>