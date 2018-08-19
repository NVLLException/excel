<%@ page language="java" import="com.excel.entity.FileInfo" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.excel.util.JSONUtil" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%
    String htmlString  = (String)request.getAttribute("htmlString");
    List<String> fieldList = (ArrayList)request.getSession().getAttribute("fieldList");
    List<Map<Object,Object>> datas = (ArrayList)request.getAttribute("datas");
    String isNew = (String)request.getAttribute("isNew");
%>
<jsp:include page="common/common.jsp"></jsp:include>

<div>
<form name="fieldInputForm">
<%=htmlString%>
</form>
    <div class="am-form-group">
        <%if("0".equals(isNew)){%>
        <button name="editData" class="am-btn am-btn-default">确认修改</button>
        <%} else {%>
        <button name="createData" class="am-btn am-btn-default">提交数据</button>
        <%}%>
        <button name="returnList" class="am-btn am-btn-default">返回表单列表</button>
    </div>
</div>

<script type="text/javascript">
    var fieldNames = <%=JSONArray.toJSONString(fieldList)%>;
    $(document).ready(function(){
        $('[name="createData"]').off().on('click',function(){
            $.ajax({
                url : "/nb/excel/createData",
                dataType : "json",
                data : $('[name="fieldInputForm"]').serialize()
            }).done(function(result){
                alert('创建成功!');
            });
        });

        $('[name="returnList"]').off().on('click',function(){
            window.location.href = "/nb/excel/formList";
        });

        <%if("0".equals(request.getAttribute("isNew"))){%>
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
        <%}%>
    });
</script>