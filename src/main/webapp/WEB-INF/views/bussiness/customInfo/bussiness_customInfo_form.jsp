<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
	<title>客户添加</title>
	<%@ include file="../../../common/header.jsp"%>
</head>
<body>
<div class="container-fluent">
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="bussiness:customInfo:view">
			<li>
				<a href="${path}/customInfo/${customInfo.formUrl}">客户列表</a>
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="bussiness:customInfo:edit">
			<li class="active">
				<a href="#">客户${not empty entity.id?'修改':'添加'}</a>
			</li>
		</shiro:hasPermission>
	</ul>
</div>
<div class="panel panel-default">
	<div class="panel-heading">客户添加</div>
	<div class="panel-body">
		<form:form id="entityForm" modelAttribute="customInfo"
				   action="${path}/customInfo/save" method="post"
				   cssClass="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="formUrl"/>
			<form:hidden path="customUserId"/>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="loginName">登录名称</label>
				<div class="col-sm-8">
					<form:input path="loginName" cssClass="form-control required" id="loginName" maxlength="255"/>
					<span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="password">密码</label>
				<div class="col-sm-8">
					<form:input path="password" cssClass="form-control required" id="password" maxlength="255" autocomplete="off" onfocus="this.type='password'"/>
					<span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="userName">姓名</label>
				<div class="col-sm-8">
					<form:input path="userName" cssClass="form-control required " id="userName" maxlength="255"/>
					<span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="phoneNumber">手机号码</label>
				<div class="col-sm-8">
					<form:input path="phoneNumber" cssClass="form-control number required" id="phoneNumber" maxlength="11"/>
					<span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="qqNumber">QQ</label>
				<div class="col-sm-8">
					<form:input path="qqNumber" cssClass="form-control number required" id="qqNumber" maxlength="20"/>
					<span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="level">会员类型</label>
				<div class="col-sm-8">
					<form:select id="level" path="level" cssClass="form-control required">
						<form:options items="${customLevelList}"/>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="remark">备注</label>
				<div class="col-sm-8">
					<form:textarea path="remark" cssClass="form-control" maxlength="255"
								   id="remark" rows="3" />
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
		require([  'jqueryValidateMessages', 'suggest','jquerytree','sweetalert'], function() {
			require(['sys'], function() {
				$("#entityForm").validate();
			})
		})
	})
</script>
</html>