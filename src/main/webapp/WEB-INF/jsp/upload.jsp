<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<link rel="stylesheet" href="/css/form.css"/>
<div class="middleDiv">
    <fieldset>
    <form name="fileUpload" enctype="multipart/form-data" method="post">

        <div class="am-form-group">
            <label>Excel</label>
            <input type="file" name="file" />
        </div>
        <div class="am-form-group">
            <label class="block-label">表单别名(可选)</label>
            <input type="text" name="formName"/>
        </div>
    </form>
        <div class="am-form-group">
            <button name="upload" class="am-btn am-btn-default">上传</button>
        </div>
        </fieldset>
</div>
</body>
<script>
    $('[name="upload"]').off().on('click',function(){
        $.ajax({
            url : "/excel/doUpload",
            method : "post",
            dataType : "json",
            enctype: 'multipart/form-data',
            data : new FormData($('[name="fileUpload"]')[0]),
            processData: false,
            contentType: false
        }).done(function(result){
            if("success" == result.statusCode){
                window.location = "/excel/configForm";
            } else {
                console.error("error");
            }
        });
    });
</script>