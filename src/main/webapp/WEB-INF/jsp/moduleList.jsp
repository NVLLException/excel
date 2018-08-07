<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<div class="moduleList am-g">

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
            var addForm = $('<button class="am-btn am-btn-default">添加表单</button>');
            var deleteModule = $('<button class="am-btn am-btn-default" onclick=deleteModule('+ datas[key].id +')>删除</button>');
            action.append(addForm);
            action.append(deleteModule);
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
</script>
<style>
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
