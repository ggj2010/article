<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<html zh-CN>
<head>
<title>媒体列表</title>
<%@ include file="../../../common/header.jsp"%>
</head>
<body>
	<div class="container-fluent">
		<form:form id="mediaForm" modelAttribute="media"
			action="${path}/media/select" method="post" class="form-inline well">
			 <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
			 <input type="hidden" name="pageSize" id="pageSize"  value="${pageInfo.pageSize}"> 
			<div class="form-group">
				<label >渠道</label>
				<form:select id="mediaChannel" path="mediaChannel" class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${mediaChannelList}"/>
				</form:select>
			</div>
			<div class="form-group">
				<label >媒体类型</label>
				<form:select id="mediaType" path="mediaType" class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${mediaTypeList}"/>
				</form:select>
			</div>
			<div class="form-group">
				<label>媒体区域</label>
				<form:select id="mediaRegion" path="mediaRegion" class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${mediaRegionList}"/>
				</form:select>
			</div>
			<div class="form-group">
				<label>收录类型</label>
				<form:select id="collectionType" path="collectionType" class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${collectionTypeList}"/>
				</form:select>
			</div>
			<div class="form-group">
				<label>参考粉丝</label>
				<form:select id="fansNum" path="fansNum" class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${fansList}"/>
				</form:select>
			</div>
			<div class="form-group">
				<label>参考阅读数</label>
				<form:select id="readsNum" path="readsNum" class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${readsList}"/>
				</form:select>
			</div>
			<div class="form-group">
				<label for="name">媒体名称</label>
				<form:input type="text" class="form-control" path="name" id="name"/>
			</div>
			<button type="submit" class="btn btn-info">查询</button>
		</form:form>
		<div class="panel panel-default">
			<div class="panel-heading">媒体</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover  table-striped table-bordered table-condensed" style="font-size:5px">
						<tr class="info">
							<th>序列</th>
							<th>名称</th>
							<th>价格</th>
							<th>收录类型</th>
							<th>媒体类型</th>
							<th>媒体区域</th>
							<th>发布速度</th>
							<th>参考粉丝数</th>
							<th>参考阅读数</th>
							<th>百度权重</th>
							<th>案例网址</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${pageInfo.list}" var="entity">
							<tr>
								<td title="${entity.remark}">${entity.id}</td>
								<td>${entity.name}</td>
								<!--价格-->
								<c:if test="${principal.userType==0}">
									<td>${entity.silverPrice}</td>
								</c:if>
								<c:if test="${principal.userType==1}">
									<c:if test="${principal.level=='金牌'}">
									<td>${entity.goldPrice}</td>
									</c:if>
									<c:if test="${principal.level=='银牌'}">
									<td>${entity.silverPrice}</td>
									</c:if>
									<c:if test="${principal.level=='铜牌'}">
									<td>${entity.bronzePrice}</td>
									</c:if>
								</c:if>
								<%--<td>${entity.costPrice}</td>--%>
								<td>${entity.collectionType}${principal.level}</td>
								<td>${entity.mediaType}</td>
								<td>${entity.mediaRegion}</td>
								<td>${entity.publishSpeed}</td>
								<td>${entity.fansNum}</td>
								<td>${entity.readsNum}</td>
								<td>${entity.baiduSeo}</td>
								<td>
									<a name="type" href="${entity.exampleUrl}" data-toggle="tooltip"
									   data-placement="top" target="_blank">查看</a>
								</td>
								<td>${entity.remark}
								</td>
								<td>
									<a class="btn btn-info" href="javascript:void(0);" name="choose" data-toggle="tooltip" data-placement="top" title="选择" ><span class="glyphicon glyphicon-plus-sign"></span> </a>
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
				$('#mediaForm')[0].reset();
				$("#pageNum").val("1");
				$("select[name='type']").val($(this).attr("value"));
				$('#mediaForm').submit();
			})

			$('[data-toggle="tooltip"]').tooltip();//提示框启用
			
			$("a[name='pages']").on("click", function() {
				$("input[name='pageNum']").val($(this).attr("value"));
				$("#mediaForm").submit();
			})
			$("a[name='choose']").on("click", function() {
				var id=$(this).parent().parent().children('td:eq(0)').html();
				var name=$(this).parent().parent().children('td:eq(1)').html();
				var price=$(this).parent().parent().children('td:eq(2)').html();
				var html="";
				html+="<tr id=\""+id+"\">";
				html+="<td>"+name+"</td>";
				<c:if test="${principal.userType==0}">
				html += "<td><input type=\"text\" value=\"" + price + "\" onchange=\"getArticleInfo()\"/><input type=\"hidden\" value=\"" + id + "\" /></td>";
				</c:if>
				<c:if test="${principal.userType==1}">
				html+="<td><input type=\"text\" value=\""+price+"\" readonly><input type=\"hidden\" value=\""+id+"\"></td>";
				</c:if>
				html+="<td><a class=\"btn btn-info\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"删除\" onclick='deleteChoose(\""+id+"\")'><span class=\"glyphicon glyphicon-trash\"></span></td>";
				html+="</tr>";
				window.parent.appendChoose(id,html);
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