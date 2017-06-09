<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html zh-CN>
<head>
    <title>结算列表</title>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>

<div class="container-fluent">
    <ul class="nav nav-tabs">
        <shiro:hasPermission name="bussiness:settle:view">
            <li class="active">
                <a href="#">结算列表</a>
            </li>
        </shiro:hasPermission>
    </ul>
    <form:form id="settleForm" modelAttribute="mediaSettleMent"
               action="${path}/settle/${formUrl}"
               method="post" class="form-inline well">
        <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
        <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
        <input type="hidden" name="bussinnessType" id="bussinnessType" value="${mediaSettleMent.bussinnessType}">
        <div class="form-group">
            <label for="status">结算状态</label>
            <form:select id="status" path="status" class="form-control">
                <form:option value="" label=""/>
                <form:options items="${articleStatusList}" itemLabel="name" itemValue="value"/>
            </form:select>
        </div>
        <button type="submit" class="btn btn-info">查询</button>
    </form:form>
    <div class="panel panel-default">
        <div class="panel-heading">结算列表</div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover  table-striped table-bordered">
                    <tr class="info">
                        <th>标题</th>
                        <th>媒体</th>
                        <th>状态</th>
                        <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                            <th>客户</th>
                            <th>客户价格</th>
                        </c:if>
                        <c:if test="${mediaSettleMent.type=='3'}">
                            <th>编辑价格</th>
                        </c:if>
                        <th>结算价格</th>
                        <th>备注</th>
                        <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                            <th>操作</th>
                        </c:if>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="entity">
                        <tr <c:if test="${entity.status=='0'}">class="success"</c:if> <c:if test="${entity.status=='0'}">class="danger"</c:if>  >
                            <td title="${entity.article.title}">${fn:substring(entity.article.title, 0, 10)}</td>
                            <td title="${entity.article.mediaName}">${fn:substring(entity.article.mediaName, 0, 10)}</td>
                            <td>
                                <c:if test="${entity.status=='0'}">
                                    未结算
                                </c:if>
                                <c:if test="${entity.status=='1'}">
                                    已结算
                                </c:if>
                            </td>
                            <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                            <td>${entity.article.customName}</td>
                            <td>${entity.article.customPrice}</td>
                            </c:if>
                            <td>${entity.price}</td>
                            <c:if test="${mediaSettleMent.type=='3'}">
                                <td>${entity.article.costPrice}</td>
                            </c:if>
                            <td title="${entity.remark}">${fn:substring(entity.remark, 0, 10)}</td>
                            <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                                <td>
                                    <shiro:hasPermission name="bussiness:settle:form">
                                        <c:if test="${entity.status=='0'}">
                                        <a class="btn btn-info" href="javaScript:settleView('${entity.id}','${entity.type}','${entity.article.title}','${entity.article.costPrice}','${entity.article.customPrice}')" data-toggle="tooltip" data-placement="top" title="结算" ><span class="glyphicon glyphicon-edit"></span> </a>
                                        </c:if>
                                        <c:if test="${entity.status=='1'}">
                                            无
                                         </c:if>
                                    </shiro:hasPermission>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
                <mutualhelp:page></mutualhelp:page>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="verifyModule" tabindex="-1"
     aria-labelledby="uploadModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">结算</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal">
                        <form action="${path}/settle/save" method="post" id="verifyForm">
                            <div class="form-group">
                                <label for="articleTitleId" class="col-sm-2 control-label">结算</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" readonly="readonly" id="articleTitleId">
                                    <input type="hidden" class="form-control" name="id" id="settlementId">
                                    <input type="hidden" class="form-control" name="type" id="typeId">
                                    <input type="hidden" class="form-control" name="formUrl" value="${formUrl}?bussinnessType=${mediaSettleMent.bussinnessType}" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="priceId" class="col-sm-2 control-label">应付价格</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" readonly="readonly" id="priceId">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="realPayPrice" class="col-sm-2 control-label">实付价格</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  id="realPayPrice"
                                           name="price">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="remark">备注</label>
                                <div class="col-sm-8">
										<textarea name="remark"  maxlength="255" rows="3" id="remark"></textarea>
                                    <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                                </div>
                            </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    require(['jquery', 'bootstrap', 'jqueryValidateMessages'], function ($) {
        require(['Chosen', 'toastr', 'sys'], function () {
            //选择框赋值
            $("select").chosen();

            $("a[name='type']").on("click", function () {
                $('#mediaForm')[0].reset();
                $("#pageNum").val("1");
                $("select[name='type']").val($(this).attr("value"));
                $('#mediaForm').submit();
            })

            $('[data-toggle="tooltip"]').tooltip();//提示框启用

            $("a[name='pages']").on("click", function () {
                $("input[name='pageNum']").val($(this).attr("value"));
                $("#mediaForm").submit();
            })

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
                toastr.success("${message}");
            }
        })
    })

	function settleView(id,type,title,price,customPrice) {
		$("#settlementId").val(id);
		$("#articleTitleId").val(title);
		$("#typeId").val(type);
		//编辑价格和客户价格
		if(type=="3"){
			$("#priceId").val(price);
		}else{
			$("#priceId").val(customPrice);
		}

        $("#verifyModule").modal('show');
    }
</script>

</html>