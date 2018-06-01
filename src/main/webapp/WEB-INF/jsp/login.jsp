<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<div>
    <form name="loginForm">
        <input name="loginName" type="text"/>
        <input name="password" type="password"/>
        <button name="login">登录</button>
    </form>
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