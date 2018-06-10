<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="common/common.jsp"></jsp:include>
<style>
  .nav-li{
    width: 100%!important;
  }
  .active-li>a{
    background-color: lightgrey!important;
  }
</style>
<div>
  <div class="am-g">
    <div class="am-u-sm-3" name="formList">
      <nav data-am-widget="menu" class="am-menu  am-menu-stack">
        <a href="javascript: void(0)" class="am-menu-toggle">
          <i class="am-menu-toggle-icon am-icon-bars"></i>
        </a>
        <ul class="am-menu-nav am-avg-sm-3" id="nav-menu">
        </ul>
      </nav>
    </div>
    <div class="am-u-sm-9" id="nav-right-list">
    </div>
  </div>

  <div class="am-modal am-modal-alert" tabindex="-1" id="deleteModel">
    <div class="am-modal-dialog">
      <%--<div class="am-modal-hd">Amaze UI</div>--%>
      <div class="am-modal-bd">
        确定要删除这条记录？
      </div>
      <div class="am-modal-footer">
        <span class="am-modal-btn">取消</span>
        <span class="am-modal-btn" id="delete">确定</span>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  var FormList = (function(){
    var $defaults = {
      leftMenuId : "",
      rightListId : "",
      deleteId : ""
    }
    var $this;
    var FormList = function($options){
      $this = this;
      $defaults = $.extend(this.$defaults,$options);
      this.loadLeftMenu();
    }
    FormList.prototype.loadLeftMenu = function(){
      $.ajax({
        url : '/excel/getFormList',
        dataType : 'json',
        method : 'post'
      }).done(function(result) {
        if ("success" == result.statusCode) {
          $this.renderLeftMenu(result.data);
        }
      });
    }
    FormList.prototype.renderLeftMenu = function(data){
      var $firstLi;
      for(var i=0;i < data.length ; i++){
        var $li = $('<li class="nav-li"/>');
        $this.bindLeftMenuEvent($li,data[i]);
        $('#'+$defaults.leftMenuId).append($li);
        if(i==0){
          $firstLi = $li;
        }
      }
      var new_li = "<li class='nav-li'><a href='/excel/upload'>创建新表单</a></li>";
      $('#'+$defaults.leftMenuId).append(new_li);
      setTimeout(function(){
        $firstLi.find('a').trigger('click');
      },0);
    }
    FormList.prototype.bindLeftMenuEvent = function($li,info){
      var fileName = info.fileName;
      var tableName = info.tableName;
      var $link = $('<a/>');
      $link.append(fileName);
      $li.append($link);
      $link.off().on('click',function(){

        //添加样式
        $('#'+$defaults.leftMenuId).find('li').removeClass('active-li');
        $li.addClass('active-li');

        $.ajax({
          url : '/excel/getFormInfoGroupByUser',
          dataType : 'json',
          method : 'post',
          data : {tableName : tableName}
        }).done(function(result){
          if('success' == result.statusCode){
            $this.renderGroupFormData(info,result.data);
          }
        });
      });
    }
    FormList.prototype.renderGroupFormData = function(info,datas){
      if(datas && datas.length > 0){
        $('#'+$defaults.rightListId).empty();
        for(var i=0;i<datas.length;i++){
          $this.renderGroupForm(info,datas[i]);
        }
      }
    }
    FormList.prototype.renderGroupForm = function(info,data){
      var el = $('<section data-am-widget="accordion" class="am-accordion am-accordion-gapped" data-am-accordion="{ }"/>');
      var dl = $('<dl class="am-accordion-item"/>');
      var dt = $('<dt class="am-accordion-title"/>');
      var dd = $('<dd class="am-accordion-bd am-collapse"/>');
      var content = $('<div class="am-accordion-content"/>');
      dt.append(data.nickName);
      dt.append('<span style="float: right;margin-right: 20px">共创建/修改(' + data.count + ')条数据</span>');
      dd.append(content);
      dl.append(dt);
      dl.append(dd);
      el.append(dl);
      $this.bindPanelEvent(dl,dt,dd,content,info,data.id);
      $('#'+$defaults.rightListId).append(el);
    }

    FormList.prototype.bindPanelEvent = function($dl,$dt,$dd,$content,info,userId){
      $dt.off().on('click',function(){
        if($dl.hasClass("am-active")){
          $dl.removeClass("am-active");
          $dd.removeClass("am-in");
        } else {
          $dl.addClass("am-active");
          $dd.addClass("am-in");
          $this.loadContent(info,userId,$content)
        }
      });
    }

    FormList.prototype.loadContent = function(info,userId,$content){
      $.ajax({
        url : '/excel/retrieveFileInfoDataByUserId',
        data : {tableName : info.tableName, userId : userId},
        dataType : "json",
        method : "post"
      }).done(function(result){
        if("success" == result.statusCode){
          $this.renderContent(result.data,$content,info);
        }
      });
    }

    FormList.prototype.renderContent = function(data,$content,info){
      var table = $('<table class="am-table"/>');
      var tbody = $('<tbody/>');
      if(data && data.length > 0){
        for(var i=0;i<data.length;i++){
          var tr = $('<tr/>');
          var td1 = $('<td/>');
          var td2 = $('<td style="text-align: right"/>');
          td1.append("创建时间: "+data[i].createTime);
          tr.append(td1);
          tr.append(td2);
          tbody.append(tr);
          $this.bindAction(data[i], info,td2);
        }
        table.append(tbody);
        $content.empty();
        $content.append(table);
      }
    }

    FormList.prototype.bindAction = function(data, info, $td){
      var $span = $('<span/>');
      var $view = $('<a href="#" style="margin-right: 20px">查看</a>');
      var $edit = $('<a href="#" style="margin-right: 20px">编辑</a>');
      var $delete = $('<a href="#">删除</a>');
      $span.append($view);
      $span.append($edit);
      $span.append($delete);
      $td.append($span);
      $this.bindDeleteAction($delete,data,info);
    }

    FormList.prototype.bindDeleteAction = function($delete,data,info){
      $delete.attr("data-am-modal","{target: '#deleteModel'}");
      $('#'+$defaults.deleteId).find('#delete').off().on('click',function(){

      });
    }

    return FormList;
  })(jQuery);
  var options = {
    leftMenuId : "nav-menu",
    rightListId : "nav-right-list",
    deleteId : "deleteModel"
  };
  new FormList(options);
</script>

