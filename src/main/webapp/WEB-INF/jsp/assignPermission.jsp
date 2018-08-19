<%@ page import="java.util.List" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<%
    List<Map<Object,Object>> userlist = (ArrayList)request.getAttribute("userList");
    List<Map<Object,Object>> moduleList = (ArrayList) request.getAttribute("moduleList");
    List<String> permisonList = (ArrayList)request.getAttribute("permissionList");
%>
<div class="assignPermission">
    <form name="permissionForm" onsubmit="return false;">
    <section data-am-widget="accordion" class="am-accordion am-accordion-default" data-am-accordion='{ "multiple": true }'>
        <%for(Map<Object,Object> module: moduleList){%>
            <dl class="am-accordion-item">
                <dt class="am-accordion-title">
                    <%=module.get("name")%>
                </dt>
                <dd class="am-accordion-bd am-collapse">
                    <div class="am-accordion-content">
                        <%for(Map<Object,Object> user: userlist ){
                            String moduleUserId = module.get("id")+"_"+user.get("id");
                            System.out.println(moduleUserId);
                            Boolean checked = permisonList.contains(moduleUserId);
                        %>
                            <input type="checkbox" name="moduleUserId" <%=checked ? "checked" : ""%> value="<%=moduleUserId%>"/><%=user.get("nickName") != null ? user.get("nickName"):""%>(<%=user.get("id")%>)
                        <br>
                        <%}%>
                    </div>
                </dd>
            </dl>
        <%}%>
    </section>
    </form>
    <button type="button" class="am-btn am-btn-default" onclick="backToList()">返回</button>
    <button type="button" class="am-btn am-btn-default" onclick="savePermission()">保存</button>
</div>
<script>
    function savePermission(){
        $.ajax({
            url: '/nb/excel/doAssignPermission',
            dataType: 'json',
            data: $('[name="permissionForm"]').serialize()
        }).done(function(result){
            if(result.statusCode == 'success'){
                alert("保存成功！");
            }else {
                alert("保存失败！");
            }
        })
    }
    function backToList(){
        window.location.href = '/nb/excel/moduleList'
    }
</script>
<style>
    .assignPermission {
        width: 70%;
    }
</style>