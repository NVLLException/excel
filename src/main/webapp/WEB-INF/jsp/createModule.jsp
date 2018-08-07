<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<link rel="stylesheet" href="/nb/css/form.css"/>
<div class="middleDiv">
  <fieldset>
  <div class="am-form-group">
    <label class="block-label">模块名</label>
    <input name="moduleName" type="text"/>
  </div>
    <div class="am-form-group">
      <button name="create" class="am-btn am-btn-default">创建</button>
    </div>
   </fieldset>
</div>
<script type="text/javascript">
  $('[name="create"]').off().on('click',function(){
    $.ajax({
      url:'/nb/excel/addModule',
      data:{moduleName:$('[name="moduleName"]').val()}
    }).done(function(result){
      window.location.href = '/nb/excel/moduleList';
    });
  });
</script>