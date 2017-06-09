<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>菜单添加</title>
<%@ include file="../../../common/header.jsp"%>

</head>
<body>

	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="sys:menu:view">
				<li>
					<a href="${path}/sys/menu/">菜单列表 </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:menu:edit">
				<li class="active">
					<a href="#">菜单${not empty entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">字典字段添加</div>
		<div class="panel-body">
			<form:form id="entityForm" modelAttribute="menu"
				action="${path}/sys/menu/save" method="post"
				cssClass="form-horizontal">
			<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="type">上级菜单 </label>
					<div class="col-sm-4">
						<div class="input-group">
							<mutualhelp:treeselect id="menu" extId="${menu.id}" name="parentId" labelName="parentName"  labelValue="${menu.parentName}" url="/sys/menu/treeData" value="${menu.parentId}"  cssClass="form-control required" allowInput="false" title="选择菜单" checked="false"></mutualhelp:treeselect>
							<span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="type">名称</label>
					<div class="col-sm-4" >
						<form:input path="name" cssClass="form-control required" id="type" maxlength="255"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="type">链接</label>
					<div class="col-sm-6" >
						<form:input path="href" cssClass="form-control" maxlength="255" placeholder="点击菜单跳转的页面"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="target">跳转方式</label>
					<div class="col-sm-4" >
						<form:select path="target" cssClass="form-control" id="target">
							<form:options items="${typeList}" itemLabel="name" itemValue="value"/>
						</form:select>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="sort">排序</label>
					<div class="col-sm-4">
						<form:input path="sort" cssClass="form-control  number" maxlength="10" id="sort"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="permission">权限标示</label>
					<div class="col-sm-4">
						<form:input path="permission" cssClass="form-control"  maxlength="255" id="permission"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="isShow">是否显示</label>
					<div class="col-sm-4">
						 <input type="checkbox" class="js-switch" ${menu.isShow=='0'?'checked':''}  id="isShow"/>
						<input type="hidden" name="isShow" value="${menu.isShow}" id="isShowInput">
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="remark">备注</label>
					<div class="col-sm-4">
						<form:textarea path="remark" cssClass="form-control"  maxlength="255" id="remark"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-info" type="submit">保存</button>
					<a class="btn btn-default" href="javaScript:history.go(-1)">返回</a>
					</div>
				</div>
				<form:errors path="*" cssClass="alert-danger"></form:errors>
			</form:form>
		</div>
	</div>
</body>

<script type="text/javascript">
	require([ 'jquery', 'bootstrap'], function($) {
		require([ 'layer', 'jqueryValidateMessages'], function() {
			require(['Chosen','sys','suggest' ], function() {
			require(['Switchery' ], function(Switchery) {
				//选择框赋值
				$("select").chosen();
				var elem = document.querySelector('.js-switch');
		       	Switchery(elem, {
		            color: '#1AB394'
		        });

				$("#entityForm").validate({
					submitHandler: function(form){
						var hasChk = $('#isShow').is(':checked');
						if(hasChk){
							$('#isShowInput').val("0");
						}else{
							$('#isShowInput').val("1");
						}
							form.submit();
					}
				});

			})
		})
		})
	})
	
</script> 
</html>