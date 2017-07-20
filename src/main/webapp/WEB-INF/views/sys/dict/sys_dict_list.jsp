<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>字典表列表</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>

	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="sys:dict:view">
				<li class="active">
					<a href="#">字典表列表</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:dict:edit">
				<li>
					<a href="${path}/sys/dict/form">字典表${not empty
						entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
		<form:form id="dictForm" modelAttribute="dictionaryTable"
			action="${path}/sys/dict/" method="post" class="form-inline well">
			 <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
			 <input type="hidden" name="pageSize" id="pageSize"  value="${pageInfo.pageSize}"> 
			<div class="form-group">
				<label>类型</label>
				<form:select id="type" path="type" class="form-control">
					<form:option value="" label="请选择" />
					<form:options items="${typeList}"/>
				</form:select>
			</div>
			<div class="form-group">
				<label for="名称">名称</label>
				<form:input type="text" class="form-control" path="name" />
			</div>
			<button type="submit" class="btn btn-info">查询</button>
		</form:form>
		<div class="panel panel-default">
			<div class="panel-heading">字典列表</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover  table-striped table-bordered">
						<tr class="info">
							<th>名称</th>
							<th>键值</th>
							<th>类型</th>
							<th>描述</th>
							<th>排序</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${pageInfo.list}" var="entity">
							<tr>
								<td>${entity.name}</td>
								<td>${entity.value}</td>
								<td>
									<a name="type" value="${entity.type}" data-toggle="tooltip"
										data-placement="top" title="查询所有类型=${entity.type}字典值">${entity.type}</a>
								</td>
								<td>${entity.description}</td>
								<td>${entity.sort}</td>
								<td>
									<a class="btn btn-info" href="${path}/sys/dict/add?id=${entity.id}"  data-toggle="tooltip" data-placement="top" title="增加" ><span class="glyphicon glyphicon-plus-sign"></span> </a>
									<a class="btn btn-info" href="${path}/sys/dict/form?id=${entity.id}"  data-toggle="tooltip" data-placement="top" title="修改" ><span class="glyphicon glyphicon-edit"></span> </a>
									<a class="btn  btn-info" url="${path}/sys/dict/delete?id=${entity.id}" data-toggle="tooltip" data-placement="top" title="删除" name="delete"><span class="glyphicon glyphicon-trash"></span></a>
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
	require([ 'jquery', 'bootstrap', 'jqueryValidateMessages'], function($) {
		require([ 'Chosen','toastr' ,'sys' ], function() {
			//选择框赋值
			$("select").chosen();

			$("a[name='type']").on("click", function() {
				$('#dictForm')[0].reset();
				$("#pageNum").val("1");
				$("select[name='type']").val($(this).attr("value"));
				$('#dictForm').submit();
			})

			$('[data-toggle="tooltip"]').tooltip();//提示框启用
			
			$("a[name='pages']").on("click", function() {
				$("input[name='pageNum']").val($(this).attr("value"));
				$("#dictForm").submit();
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