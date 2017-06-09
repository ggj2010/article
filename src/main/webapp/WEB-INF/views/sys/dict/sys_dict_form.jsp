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
			<form:form id="entityForm" modelAttribute="dictionaryTable"
				action="${path}/sys/dict/save" method="post"
				cssClass="form-horizontal">
					<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="type">字典类型</label>
					<div class="col-sm-4">
						<div class="input-group">
								<form:input path="type" cssClass="form-control required"
									id="type" maxlength="255" />
								<span class="glyphicon  form-control-feedback"
									aria-hidden="true"></span>
								<div class="input-group-btn">
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown">
										<span class="caret"></span>
									</button>
									<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="name">名称</label>
					<div class="col-sm-8">
						<form:input path="name" cssClass="form-control required" id="name" maxlength="255"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="value">键值</label>
					<div class="col-sm-8">
						<form:input path="value" cssClass="form-control required" id="value" maxlength="255"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="sort">排序</label>
					<div class="col-sm-8">
						<form:input path="sort" cssClass="form-control  number" id="sort" maxlength="10"/>
						 <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="description">描述</label>
					<div class="col-sm-8">
						<form:textarea path="description" cssClass="form-control" maxlength="255"
							id="description" rows="3" />
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
				
				var taobaoBsSuggest = $("#type").bsSuggest({
			        indexId: 0,             //data.value 的第几个数据，作为input输入框的内容
			        indexKey: 0,            //data.value 的第几个数据，作为input输入框的内容
			        allowNoKeyword: false,  //是否允许无关键字时请求数据。为 false 则无输入时不执行过滤请求
			        multiWord: true,        //以分隔符号分割的多关键字支持
			        separator: ",",         //多关键字支持时的分隔符，默认为空格
			        getDataMethod: "url",   //获取数据的方式，总是从 URL 获取
			        showHeader: true,       //显示多个字段的表头
			        showBtn: false,
			        effectiveFieldsAlias:{type: "字典类型"}, 
			        url: '${path}/sys/dict/ajax/getdicttype?type=', /*优先从url ajax 请求 json 帮助数据，注意最后一个参数为关键字请求参数*/
			        //jsonp: 'callback',               //如果从 url 获取数据，并且需要跨域，则该参数必须设置
			        processData: function(json){     // url 获取数据时，对数据的处理，作为 getData 的回调函数
			                 var i, len, data = {value: []};
			                 len = json.value.length;
			                 for (i = 0; i < len; i++) {
			                     data.value.push({
			                         "type": json.value[i]
			                     });
			                 }
			                 console.log(data);
			                 return data;
			             }
				
				
				});

			})
	})
	
	})
</script>
</html>