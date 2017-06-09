<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>角色添加</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>

	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="sys:role:view">
				<li>
					<a href="${path}/sys/role/">角色列表</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:role:edit">
				<li class="active">
					<a href="#">角色${not empty entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">角色添加</div>
		<div class="panel-body">
			<form:form id="entityForm" modelAttribute="role"
				action="${path}/sys/role/save" method="post"
				cssClass="form-horizontal">
					<form:hidden path="id"/>
					<form:hidden path="menuIds" id="menuIds"/>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="name">名称</label>
					<div class="col-sm-6">
						<form:input path="name" cssClass="form-control required" id="name" maxlength="255"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="roleTree">角色授权</label>
					<div class="col-sm-8" >
						<ul id="roleTree" class="ztree"></ul>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="remark">备注</label>
					<div class="col-sm-6">
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
				var treeObj;
				var setting = {
						check:{
								nocheckInherit: true,
								enable:true
							},
						data : {
							simpleData : {
								enable : true,
								idKey : "id",
								pIdKey : "pId"
							}
						}
					};
				
				$.get("${path}/sys/menu/treeData?t="+ new Date().getTime(), function(zNodes){
					treeObj= $.fn.zTree.init($("#roleTree"), setting, zNodes);
					var ids = "${role.menuIds}".split(",");
					for(var i=0; i<ids.length; i++) {
						var node = treeObj.getNodeByParam("id", ids[i]);
						try{treeObj.checkNode(node, true, false);}catch(e){}
					}
				})
				
				$("#entityForm").validate({
					submitHandler: function(form){
						var menuIds= [],nodes = [];
						nodes=treeObj.getCheckedNodes();
						for(var i=0; i<nodes.length; i++) {
							menuIds.push(nodes[i].id);
						}
						$("#menuIds").val(menuIds.join(","));
						if($("#roleIds").val()==""){
							swal("用户角色不能为空")
						}else{
							form.submit();
						}
					}
				});
				
			})
		})
	})
</script>
</html>