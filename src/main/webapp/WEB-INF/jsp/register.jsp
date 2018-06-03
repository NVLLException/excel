<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<link rel="stylesheet" href="/css/form.css"/>
<div class="middleDiv">
    <fieldset>
    <div class="am-form-group">
        <label class="block-label">昵称</label>
        <input type="text" name="nikeName" placeholder="请输入昵称"/>
    </div>
    <div class="am-form-group">
        <label class="block-label">登录名</label>
        <input type="text" id="loginName" v-model="loginName"  placeholder="请输入登录名"/>
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
        <button class="am-btn  am-btn-default">注册</button>
    </div>
    </fieldset>
</div>
<script type="text/javascript">
    var loginName = new Vue({
        el : 'loginName',
        data : {
            loginName : ""
        },
        methods : {
            checkLoginName : function(){
                $.ajax({
                    url : '/excel/checkLoginName',
                    dataType : 'json',
                    method : 'post',
                    data : {losginName : this.losginName}
                }).done(function(result){
                    if('error' == result.statusCode){

                    }
                });
            }
        }
    });
</script>