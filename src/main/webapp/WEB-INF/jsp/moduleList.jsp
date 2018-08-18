<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.excel.entity.User" %>
<jsp:include page="common/common.jsp"></jsp:include>
<%
    User user = (User)request.getSession().getAttribute("user");
    String isAdmin = user.getIsAdmin();
%>
<div class="adminList am-g">
    <h1>权限管理</h1>
    <div class='module middleDiv am-u-sm-3' onclick="addAdminUser()">
        添加管理员
    </div>
    <div class='module middleDiv am-u-sm-3' onclick="assignPermission()">
        分配表单权限
    </div>
</div>
<div class="moduleList am-g">
    <h1>模块管理</h1>
</div>
<script>
    $(document).ready(function(){
        $.ajax({
            url:"/nb/excel/getModuleList",
            dataType:'json'
        }).done(function(result){
            console.log(result)
            renderModuleList(result.data);
        });
    });
    function renderModuleList(datas){
        for(var key in datas){
            var module = $("<div class='module middleDiv am-u-sm-3'><span title='" + datas[key].name + "'>" + datas[key].name + "</span></div>");
            var action = $('<div class="action"></div>');
            var addForm = $('<button class="am-btn am-btn-default" onclick="addForm(' + datas[key].id + ')">添加表单</button>');
            var manageForm = $('<button class="am-btn am-btn-default" onclick="manageForm(' + datas[key].id + ')"">管理表单</button>');
            var deleteModule = $('<button class="am-btn am-btn-default" onclick=deleteModule('+ datas[key].id +')>删除</button>');
            action.append(addForm);
            action.append(manageForm);
            <%if("2".equals(isAdmin)){%>
            action.append(deleteModule);
            <%}%>
            module.append(action);
            $('.moduleList').append(module);
        }
        $('.moduleList').append("<div class='module addModule am-u-sm-3'><span class='fa fa-plus module-text'></span></div>");
        addNewModule($('.addModule'))
    }

    function addNewModule($ele){
        $ele.off().on('click',function(){
            window.location.href = '/nb/excel/createModule';
        });
    }
    function deleteModule($id){
        $.ajax({
            url:'/nb/excel/deleteModule',
            data:{id:$id}
        }).done(function(result){
            window.location.reload()
        });
    }

    function addForm($moduleId) {
        window.location.href = '/nb/excel/upload?moduleId=' + $moduleId
    }

    function manageForm($moduleId) {
        window.location.href = '/nb/excel/formList?moduleId=' + $moduleId
    }

    function addAdminUser() {
        window.location.href = '/nb/excel/register?isAdmin=1'
    }
    function assignPermission() {
        window.location.href = '/nb/excel/assignPermission'
    }
</script>
<style>
    .adminList {
        padding: 50px;
    }
    .moduleList {
        padding: 50px;
    }
    .module {
        width: 230px;
        height:280px;
        box-shadow:0 0 1px 1px grey;
    }
    .addModule,.middleDiv {
        font-size: xx-large;
        box-shadow:0 0 1px 1px grey;
        margin: 25px 40px 25px 40px;
        float: left!important;
        cursor: pointer;
    }
    .middleDiv {
        text-align: center;
        padding-top: 110px;
        max-width: 90%;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space:nowrap;
    }
    .middleDiv .action{
        display: none;
        margin-top: 70px;
    }
    .middleDiv:hover .action{
        display: block;
    }
    .action button{
        margin: 1px;
    }
    .module-text {
        width: 100%;
        height: 100%;
        padding-top: 50%;
        text-align: center;
        color: cadetblue;
        font-size: 80;
        cursor: pointer;
    }
</style>
