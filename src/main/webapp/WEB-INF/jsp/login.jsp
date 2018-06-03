<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<link rel="stylesheet" href="/css/form.css"/>
<div class="middleDiv">
    <fieldset>
    <div class="am-form-group">
        <label class="block-label">用户名</label>
        <input name="loginName" type="text"/>
    </div>
    <div class="am-form-group">
        <label class="block-label">密码</label>
        <input name="password" type="password"/>
    </div>
    <div class="am-form-group">
        <button name="login" class="am-btn am-btn-default">登录</button>
        <span ><a style="float: right" href="/excel/register">没有账号？现在注册</a></span>
    </div>
    </fieldset>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $('[name="login"]').off().on('click',function(){
            checkLogin(loginSuccess);
        });

        function loginSuccess(){
            window.location.href = "/exce/formList";
        }

        function checkLogin(_callback){
            $.ajax({
                url : '/exce/login',
                dataType : "json",
                method : 'post',
                data : {loginName : $('[name="loginName"]').val(), password : $('[name="password"]').val()}
            }).done(function(result){
                if('success' == result.statusCode){
                    _callback();
                }
            });
        }
    });
</script>