<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>客户列表</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>

	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="bussiness:customInfo:view">
				<li class="active">
					<a href="#">客户列表</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="bussiness:customInfo:edit">
				<li>
					<a href="${path}/customInfo/form?formUrl=${formUrl}">客户${not empty
						entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
		<form:form id="customInfoForm" modelAttribute="customInfo"
			action="${path}/customInfo/${formUrl}" method="post" class="form-inline well">
			 <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
			 <input type="hidden" name="formUrl" id="formUrl" value="${formUrl}">
			 <input type="hidden" name="pageSize" id="pageSize"  value="${pageInfo.pageSize}">
			<div class="form-group">
				<label for="userName">客户名称</label>
				<form:input type="text" class="form-control" path="userName" id="userName"/>
			</div>
			<div class="form-group">
				<label for="status">状态</label>
				<form:select id="status" path="customStatus" class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${customStatusList}" itemLabel="name" itemValue="value"/>
				</form:select>
			</div>
			<button type="submit" class="btn btn-info">查询</button>
		</form:form>
		<div class="panel panel-default">
			<div class="panel-heading">客户列表</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover  table-striped table-bordered">
						<tr class="info">
							<th>登录名称</th>
							<th>姓名</th>
							<th>QQ</th>
							<th>会员等级</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${pageInfo.list}" var="entity">
							<tr>
								<td>${entity.loginName}</td>
								<td>${entity.userName}</td>
								<td>${entity.qqNumber}</td>
								<td>${entity.level}</td>
								<td>
									<c:if test="${entity.customStatus=='0'}">
										非会员
									</c:if>
									<c:if test="${entity.customStatus=='1'}">
										会员
									</c:if>
								</td>
								<td>
									<shiro:hasPermission name="bussiness:customInfo:verify">
									<a class="btn btn-info" href="${path}/customInfo/verify/view?id=${entity.id}"  data-toggle="tooltip" data-placement="top" title="审核与授权" ><span class="glyphicon glyphicon-user"></span> </a>
									</shiro:hasPermission>
									<a class="btn btn-info" href="${path}/customInfo/form?id=${entity.id}&formUrl=${formUrl}"  data-toggle="tooltip" data-placement="top" title="修改" ><span class="glyphicon glyphicon-edit"></span> </a>
									<a class="btn  btn-info" url="${path}/customInfo/delete?id=${entity.id}&formUrl=${formUrl}" data-toggle="tooltip" data-placement="top" title="删除" name="delete" ><span class="glyphicon glyphicon-trash"></span></a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<mutualhelp:page></mutualhelp:page>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	require([ 'jquery', 'bootstrap', 'jqueryValidateMessages' ,'sweetalert'], function($) {
		require([ 'Chosen','toastr' ,'sys' ], function() {
			//选择框赋值
			$("select").chosen();

			$("a[name='type']").on("click", function() {
				$('#customInfoForm')[0].reset();
				$("#pageNum").val("1");
				$("select[name='type']").val($(this).attr("value"));
				$('#customInfoForm').submit();
			})

			$('[data-toggle="tooltip"]').tooltip();//提示框启用
			
			$("a[name='pages']").on("click", function() {
				$("input[name='pageNum']").val($(this).attr("value"));
				$("#customInfoForm").submit();
			})
			
			if(${not empty message }){
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
				 toastr.success("${message}");
			}
		})
	})
</script>

</html>