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
            var module = "<div class='module middleDiv am-u-sm-3'>" + datas[key].name + "</div>";
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
</script>
<style>
    .moduleList {
        padding: 50px;
    }
    .module {
        width: 210px;
        height:280px;
        box-shadow:0 0 1px 1px grey;
    }
    .addModule,.middleDiv {
        font-size: xx-large;
        box-shadow:0 0 1px 1px grey;
        margin: 25px 50px 25px 50px;
        float: left!important;
        cursor: pointer;
    }
    .middleDiv {
        text-align: center;
        padding-top: 110px;
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
