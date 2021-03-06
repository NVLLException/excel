<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.excel.entity.User" %>
<jsp:include page="common/common.jsp"></jsp:include>
<%
  User user = (User)request.getSession().getAttribute("user");
  String isAdmin = user.getIsAdmin();
  String moduleId = request.getParameter("moduleId");
%>
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
        url : '/nb/excel/getFormList',
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
      <%if("1".equals(isAdmin) || "2".equals(isAdmin)){%>
        var new_li = "<li class='nav-li'><a href='/nb/excel/upload?moduleId=<%=moduleId%>'>创建新表单</a></li>";
        $('#'+$defaults.leftMenuId).append(new_li);
      <%}%>
      setTimeout(function(){
        $firstLi.find('a').trigger('click');
      },0);
    }
    FormList.prototype.bindLeftMenuEvent = function($li,info){
      var fileName = info.fileName;
      var tableName = info.tableName;
      var $linkP = $("<span/>")
      var $link = $('<a/>');
      $link.append(fileName);
      $li.append($link);
      $link.off().on('click',function(){
        //添加样式
        $('#'+$defaults.leftMenuId).find('li').removeClass('active-li');
        $li.addClass('active-li');

        $.ajax({
          url : '/nb/excel/getFormInfoGroupByUser',
          dataType : 'json',
          method : 'post',
          data : {tableName : tableName}
        }).done(function(result){
          if('success' == result.statusCode){
            $this.renderGroupFormData(info,result.data);
          }
        });
      });

      <%if(!"0".equals(user.getIsAdmin())){%>
      var deleteForm = $('<span style="float: right;" class="deleteForm"><span class="fa fa-trash-o fa-fw"></span>&nbsp;删除</span>');
      $link.append(deleteForm);
      this.deleteForm(deleteForm,info)
       <%}%>
    }

    FormList.prototype.deleteForm = function($el,$info){
        $el.off().on('click',function(e){
            e.stopPropagation();
           $.ajax({
               url: '/nb/excel/deleteFormStruct',
               data: {infoId: $info.id},
               dataType: 'json'
           }).done(function(){
               window.location.reload();
           })
        });
    }

    FormList.prototype.renderGroupFormData = function(info,datas){
      if(datas && datas.length > 0){
        $('#'+$defaults.rightListId).empty();
        for(var i=0;i<datas.length;i++){
          $this.renderGroupForm(info,datas[i]);
        }
      } else {
          this.noData(info);
      }
    }
    FormList.prototype.noData = function(info){
        var span = $('<span>当前表单无数据</span>');
       var addData = $('<a href="#" style="margin-right: 20px" class="addData">添加数据</a>');
        $('#'+$defaults.rightListId).empty();
        $('#'+$defaults.rightListId).append(span);
        $('#'+$defaults.rightListId).append(addData);
        this.bindAddDataEvent(addData, info, null);
    }

    FormList.prototype.renderGroupForm = function(info,data){
      var el = $('<section data-am-widget="accordion" class="am-accordion am-accordion-gapped" data-am-accordion="{ }"/>');
      var dl = $('<dl class="am-accordion-item"/>');
      var dt = $('<dt class="am-accordion-title"/>');
      var dd = $('<dd class="am-accordion-bd am-collapse"/>');
      var content = $('<div class="am-accordion-content"/>');
      dt.append(data.nickName);
        dt.append("(#" + data.id + ")");
      var addData = $('<a href="#" style="float: right;margin-right: 20px" class="addData">添加数据</a>');
      dt.append('<span style="float: right;margin-right: 20px">共创建/修改(' + data.count + ')条数据</span>');
        dt.append(addData);
      dd.append(content);
      dl.append(dt);
      dl.append(dd);
      el.append(dl);
      $this.bindPanelEvent(dl,dt,dd,content,info,data.id);
      this.bindAddDataEvent(addData, info, data);
      $('#'+$defaults.rightListId).append(el);
    }

    FormList.prototype.bindAddDataEvent = function(addData, info, data){
        addData.off().on('click',function(){
            window.location.href = '/nb/excel/addData?infoId=' + info.id;
        })
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
        url : '/nb/excel/retrieveFileInfoDataByUserId',
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
          td1.append("创建时间: "+this.formatDate(data[i].createTime));
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
   FormList.prototype.formatDate = function(timeStamp){
        var date = new Date(timeStamp)
       var year = date.getYear()+1900;
        var month = date.getMonth()+1;
        var day = date.getDate();
        return month + "/" + day + "/" + year;
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
      $this.bindViewAction($view,data,info);
      $this.bindEditAction($edit,data,info);
      $this.bindDeleteAction($delete,data,info);
    }

      FormList.prototype.bindViewAction = function($view, data, info){
          $view.off().on('click',function(){
              window.location.href = '/nb/excel/viewForm?infoId='+ info.id +"&dataId=" + data.id;
          })
      }
    FormList.prototype.bindEditAction = function($edit, data, info){
        $edit.off().on('click',function(){
           window.location.href = '/nb/excel/editForm?infoId='+ info.id +"&dataId=" + data.id;
        })

    }

    FormList.prototype.bindDeleteAction = function($delete,data,info){
      $delete.attr("data-am-modal","{target: '#deleteModel'}");
      $('#'+$defaults.deleteId).find('#delete').off().on('click',function(){
          $.ajax({
              url: '/nb/excel/deleteForm',
              dataType: 'json',
              data: {dataId: data.id, tableName: info.tableName}
          }).done(function(){
             window.location.reload();
          });
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
<style>
    .deleteForm{
        cursor: pointer;
    }
</style>

