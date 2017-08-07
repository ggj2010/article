<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>修改密码</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">修改密码</div>
		<div class="panel-body">
			<form:form id="entityForm" modelAttribute="userInfo"
				action="${path}/userInfo/changepwd/save" method="post"
				cssClass="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="password">新密码</label>
					<div class="col-sm-8">
						<input type="password" name="password" id="password" class="form-control required" minlength="6">
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="password2">确认新密码</label>
					<div class="col-sm-8">
						<input type="password" name="password2" id="password2" class="form-control required" minlength="6">
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
			require(['toastr','sys'], function() {
				$("#entityForm").validate({
					//去除onkey验证
					// onkeyup:false,
					rules:{
						password: {
							required:true,
							minlength:6,
						},
						password2: {
							required:true,
							equalTo: "#password",
							minlength:6,
						}
					},
					messages:{
						password:{
							required:"请输入您的密码",
							minlength:"密码不能少于6位"
						},
						password2:{
							required:"请输入您的密码",
							minlength:"密码不能少于6位",
							equalTo: "您输入的密码与确认密码不一致"
						},
					}
				});
				if (${not empty message }) {
					toastr.options = {
						"closeButton": true,
						"progressBar": true,
						"positionClass": "toast-top-full-width",
						"timeOut": "2000",
						"showEasing": "swing",
						"hideEasing": "linear",
						"showMethod": "fadeIn",
						"hideMethod": "fadeOut"
					}
					if (${not empty level }){
						toastr.error("${message}");
					}else{
						toastr.success("${message}");
					}
				}
			})
		})
	})
	
	
</script>
</html>