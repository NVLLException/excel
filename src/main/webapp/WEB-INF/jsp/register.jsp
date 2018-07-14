<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<link rel="stylesheet" href="/nb/css/form.css"/>
<form name="registerForm" onsubmit="return false;">
<div class="middleDiv">
    <fieldset>
    <div class="am-form-group">
        <label class="block-label">昵称</label>
        <input type="text" name="nickName" placeholder="请输入昵称"/>
    </div>
    <div class="am-form-group">
        <label class="block-label">登录名</label>
        <input type="text" name="loginName"  placeholder="请输入登录名"/>
    </div>
    <div class="am-form-group">
        <label class="block-label">密码</label>
        <input type="password" name="password" placeholder="请输入密码"/>
    </div>
    <div class="am-form-group">
        <label class="block-label">确认密码</label>
        <input type="password" name="retypePassword" placeholder="重新输入密码"/>
    </div>
    <div class="am-form-group">
        <button class="am-btn  am-btn-default" name="register">注册</button>
    </div>
    </fieldset>
</div>
</form>
<script type="text/javascript">
    $(document).ready(function(){
        $('[name="register"]').off().on('click',function(){
            var isValid = $('[name="registerForm"]').valid();
            if(isValid){
                $.ajax({
                    url : '/nb/excel/doRegister',
                    dataType : 'json',
                    method : 'post',
                    data : $('[name="registerForm"]').serialize()
                }).done(function(result){
                    if("success"==result.statusCode){
                        window.location.href = "/nb/excel/login";
                    } else {
                        alert("注册失败！");
                    }
                });
            }
        });
    });
    $('[name="registerForm"]').validate({
        rules : {
            loginName : {
                required : true,
                remote: {
                    url: "/nb/excel/checkLoginName",
                    type: "post",
                    dataType: "json",
                    data: {
                        loginName: function() {
                            return $("#loginName").val();
                        }
                    }
                }
            },
            password : {
                required : true
            },
            retypePassword : {
                required : true
            }
        },
        messages : {
            loginName : {
                required : "登录名不能为空",
                remote : "登录名不可用"
            },
            password : {
                required : "密码不能为空"
            },
            retypePassword : {
                required : "请再次输入密码"
            }
        }
    });
</script>