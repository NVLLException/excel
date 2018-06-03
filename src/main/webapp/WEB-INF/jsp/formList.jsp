<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<style>
  .nav-li{
    width: 100%!important;
  }
</style>
<div>
  <div class="am-g">
    <div class="am-u-sm-3" name="formList">
      <nav data-am-widget="menu" class="am-menu  am-menu-stack">
        <a href="javascript: void(0)" class="am-menu-toggle">
          <i class="am-menu-toggle-icon am-icon-bars"></i>
        </a>
        <ul class="am-menu-nav am-avg-sm-3" name="nav-menu">
        </ul>
      </nav>
    </div>
    <div class="am-u-sm-9">
    </div>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function(){
    $.ajax({
      url : '/excel/getFormList',
      dataType : 'json',
      method : 'post'
    }).done(function(result){
      if("success" == result.statusCode){
        renderList(result.data);
      }
    });

    function renderList(infos){
      console.log(infos);
      for(var i=0;i < infos.length ; i++){
        var info = infos[i];
        var id = info.id;
        var name = info.fileName;
        var li = ' <li class="nav-li">'+
               ' <a href="loadFormData(' + id + ')">'+ name +'</a>'+
               ' </li>';
        $('[name="nav-menu"]').append(li);
      }
    }

    function loadFormData(formId){
      $.ajax({
        url : '/excel/getFormInfoGroupByUser',
        dataType : 'json',
        method : 'post'
      }).done(function(result){
        if('success' == result.statusCode){

        }
      });
    }
  });
</script>