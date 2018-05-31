<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<form name="fileUpload" enctype="multipart/form-data" method="post">
    Excel<br>
    <input type="file" name="file"/><br><br>
</form>
<button name="upload">上传</button>
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