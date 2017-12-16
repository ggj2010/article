<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>字典表添加</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>
	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="sys:dict:view">
				<li>
					<a href="${path}/sys/dict/">字典表列表</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:dict:edit">
				<li class="active">
					<a href="#">字典表${not empty entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">字典字段添加</div>
		<div class="panel-body">
			<form:form id="entityForm" modelAttribute="notice"
				action="${path}/notice/save" method="post"
				cssClass="form-horizontal">
					<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="name">标题</label>
					<div class="col-sm-8">
						<form:input path="title" cssClass="form-control required" id="title" maxlength="255"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="content">描述</label>
					<div class="col-sm-8">
						<form:textarea path="content" cssClass="form-control required" maxlength="1000"
									   id="content" rows="8" />
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
	require([ 'jquery', 'bootstrap'], function() {
		require([  'jqueryValidateMessages', 'suggest'], function() {
			require(['sys'], function() {
				$("#entityForm").validate();

			})
	})
	
	})
</script>
</html>