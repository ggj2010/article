<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>媒体添加</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>
	<div class="container-fluent">
		<ul class="nav nav-tabs">
			<shiro:hasPermission name="bussiness:media:view">
				<li>
					<a href="${path}/media/?typeParam=${media.typeParam}">媒体列表</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="bussiness:media:edit">
				<li class="active">
					<a href="#">媒体${not empty entity.id?'修改':'添加'}</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">媒体添加</div>
		<div class="panel-body">
			<form:form id="entityForm" modelAttribute="media"
				action="${path}/media/save" method="post"
				cssClass="form-horizontal">
					<form:hidden path="id"/>
					<form:hidden path="typeParam"/>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="name">名称</label>
					<div class="col-sm-8">
						<form:input path="name" cssClass="form-control required" id="name" maxlength="255"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="goldPrice">金牌价格</label>
					<div class="col-sm-8">
						<form:input path="goldPrice" cssClass="form-control required number" id="goldPrice" maxlength="10"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="silverPrice">银牌价格</label>
					<div class="col-sm-8">
						<form:input path="silverPrice" cssClass="form-control required number" id="silverPrice" maxlength="10"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="bronzePrice">铜牌价格</label>
					<div class="col-sm-8">
						<form:input path="bronzePrice" cssClass="form-control required number"  id="bronzePrice" maxlength="10"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="costPrice">成本价格</label>
					<div class="col-sm-8">
						<form:input path="costPrice" cssClass="form-control required number"  id="costPrice" maxlength="10"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label" for="exampleUrl">案例网址</label>
					<div class="col-sm-8">
						<form:input path="exampleUrl" cssClass="form-control required" id="exampleUrl" maxlength="255"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="mediaType">媒体类型</label>
					<div class="col-sm-8">
						<form:select id="mediaType" path="mediaType" cssClass="form-control">
							<form:option value="" label="请选择"/>
							<form:options items="${mediaTypeList}"/>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="mediaRegion">媒体区域</label>
					<div class="col-sm-8">
						<form:select id="mediaRegion" path="mediaRegion" cssClass="form-control">
							<form:option value="" label="请选择"/>
							<form:options items="${mediaRegionList}"/>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="publishSpeed">发布速度</label>
					<div class="col-sm-8">
						<form:select id="publishSpeed" path="publishSpeed" cssClass="form-control">
							<form:option value="" label="请选择"/>
							<form:options items="${publishSpeedList}"/>
						</form:select>
					</div>
				</div>

            <div class="form-group">
                <label class="col-sm-2 control-label" for="collectionType">收录类型</label>
                <div class="col-sm-8">
                    <form:select id="collectionType" path="collectionType" cssClass="form-control">
                        <form:option value="" label="请选择"/>
                        <form:options items="${collectionTypeList}"/>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="baiduSeo">百度权重</label>
                <div class="col-sm-8">
                    <form:select id="baiduSeo" path="baiduSeo" cssClass="form-control">
                        <form:option value="" label="请选择"/>
                        <form:options items="${baiduSeoList}"/>
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
		require([  'jqueryValidateMessages', 'suggest'], function() {
			require([ 'Chosen','sys'], function() {
				//选择框赋值
				$("select").chosen();
				$("#entityForm").validate();
			})
		})
	})
</script>
</html>