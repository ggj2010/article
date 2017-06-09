<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
	<title>编辑媒体关联</title>
	<%@ include file="../../../common/header.jsp"%>
</head>
<body>
<div class="container-fluent">
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="bussiness:userInfo:view">
			<li>
				<a href="${path}/userInfo/">用户列表</a>
			</li>
		</shiro:hasPermission>
	</ul>
</div>
<div class="panel panel-default">
	<div class="panel-heading">媒体关联</div>
	<div class="panel-body">
		<form:form id="entityForm" modelAttribute="userInfo"
				   action="${path}/userInfo/savemedia" method="post"
				   cssClass="form-horizontal">
			<form:hidden path="id"/>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="mediaTree">媒体关联(必勾选)</label>
				<div class="col-sm-8" >
					<form:hidden path="mediaIds" id="mediaIds" />
					<ul id="mediaTree" class="ztree"></ul>
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

				$.get("${path}/media/treeData?t="+ new Date().getTime(), function(zNodes){
					treeObj= $.fn.zTree.init($("#mediaTree"), setting, zNodes);
					var ids = "${userInfo.mediaIds}".split(",");
					for(var i=0; i<ids.length; i++) {
						var node = treeObj.getNodeByParam("id", ids[i]);
						try{treeObj.checkNode(node, true, false);}catch(e){}
					}
				})

				$("#entityForm").validate({
					submitHandler: function(form){
						var mediaIds= [],nodes = [];
						nodes=treeObj.getCheckedNodes();
						for(var i=0; i<nodes.length; i++) {
							mediaIds.push(nodes[i].id);
						}
						$("#mediaIds").val(mediaIds.join(","));
						if($("#mediaIds").val()==""){
							swal("编辑关联文章不能为空")
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