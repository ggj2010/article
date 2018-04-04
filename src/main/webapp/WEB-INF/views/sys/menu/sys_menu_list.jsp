<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>菜单列表</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>

	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="sys:dict:view">
				<li class="active">
					<a href="#">菜单列表</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:dict:edit">
				<li>
					<a href="${path}/sys/menu/form">菜单${not empty
						entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading">菜单列表</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover  table-striped table-bordered"
						id="menuTable">
						<tr class="info">
							<th>名称</th>
							<th>链接</th>
							<th>排序</th>
							<th>可见</th>
							<th>权限标示</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${list}" var="entity">
							<tr data-tt-id="${entity.id}"
								${entity.parentId=='1'?"":"data-tt-parent-id="}
								${entity.parentId}>
								<td>${entity.name}</td>
								<td>${entity.href}</td>
								<td>${entity.sort}</td>
								<td>${entity.isShow eq '0'?'显示':'隐藏'}</td>
								<td>${entity.permission}</td>
								<td>
									<a class="btn btn-info"
										href="${path}/sys/menu/form?id=${entity.id}"
										data-toggle="tooltip" data-placement="top" title="修改"><span
										class="glyphicon glyphicon-edit"></span> </a> 
									<a class="btn btn-info"
										href="${path}/sys/menu/form?parentId=${entity.id}&parentName=${entity.name}"
										data-toggle="tooltip" data-placement="top" title="添加下一级菜单"><span
										class="glyphicon glyphicon-plus"></span> </a> 
										
										<a
										class="btn  btn-info"
										href="${path}/sys/menu/delete?id=${entity.id}"
										data-toggle="tooltip" data-placement="top" title="删除"
										onclick="return confirm('确认要删除该按钮吗？', this.href)"><span
										class="glyphicon glyphicon-trash"></span></a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	require([ 'jquery', 'bootstrap', 'jqueryValidateMessages'], function($) {
		require([ 'Chosen','toastr' ,'treetable','sys' ], function() {
			$(function(){
			      $("#menuTable").treetable({ expandable: true });
			      $("#menuTable").treetable('expandAll');
			      $('[data-toggle="tooltip"]').tooltip();//提示框启用
			})
			
		})
	})
</script>

</html>