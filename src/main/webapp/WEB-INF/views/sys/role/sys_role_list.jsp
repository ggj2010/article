<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>角色列表</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>

	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="sys:role:view">
				<li class="active">
					<a href="#">角色列表</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:role:edit">
				<li>
					<a href="${path}/sys/role/form">角色${not empty
						entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
		<form:form id="roleFrom" modelAttribute="role"
			action="${path}/sys/role/" method="post" class="form-inline well">
			 <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
			 <input type="hidden" name="pageSize" id="pageSize"  value="${pageInfo.pageSize}"> 
			<div class="form-group">
				<label for="name">名称</label>
				<form:input type="text" class="form-control" path="name"  id="name"/>
			</div>
			<button type="submit" class="btn btn-info">查询</button>
		</form:form>
		<div class="panel panel-default">
			<div class="panel-heading">角色列表</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover  table-striped table-bordered">
						<tr class="info">
							<th>名称</th>
							<th>创建日期</th>
							<th>描述</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${pageInfo.list}" var="entity">
							<tr>
								<td>${entity.name}</td>
								<td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd"/></td>
								<td>${entity.remark}</td>
								<td>
									<a class="btn btn-info" href="${path}/sys/role/form?id=${entity.id}"  data-toggle="tooltip" data-placement="top" title="修改" ><span class="glyphicon glyphicon-edit"></span> </a>
									<a class="btn  btn-info" url="${path}/sys/role/delete?id=${entity.id}" data-toggle="tooltip" data-placement="top" title="删除" name="delete"><span class="glyphicon glyphicon-trash"></span></a>
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
	require([ 'jquery', 'bootstrap',  'sweetalert','jqueryValidateMessages','sweetalert'], function($) {
		require([ 'Chosen','toastr' ,'sys' ], function() {
			//选择框赋值
			$("select").chosen();


			$('[data-toggle="tooltip"]').tooltip();//提示框启用
			
			$("a[name='pages']").on("click", function() {
				$("input[name='pageNum']").val($(this).attr("value"));
				$("#roleFrom").submit();
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