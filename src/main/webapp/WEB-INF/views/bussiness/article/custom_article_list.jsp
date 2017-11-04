<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<%@page import="com.ggj.article.module.business.bean.enums.ArticleStatusEnum" %>
<html zh-CN>
<head>
    <title>文章列表</title>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>

<div class="container-fluent">
    <ul class="nav nav-tabs">
        <shiro:hasPermission name="bussiness:article:view">
            <li class="active">
                <a href="#">文章列表</a>
            </li>
        </shiro:hasPermission>
    </ul>
    <form:form id="articleForm" modelAttribute="article"
               action="${path}/article" method="post" class="form-inline well">
        <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
        <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
        <form:hidden path="typeParam"/>
        <div class="form-group">
            <label for="status">稿件状态</label>
            <form:select id="status" path="status" class="">
                <form:option value="" label="请选择"/>
                <form:options items="${articleStatusList}" itemLabel="name" itemValue="value"/>
            </form:select>
        </div>
        <div class="form-group">
            <label for="title">标题</label>
            <form:input type="text" class="" path="title" id="title"/>
        </div>
        <div class="form-group">
            <label for="mediaName">媒体名称</label>
            <form:input type="text" class="" path="mediaName" id="mediaName"/>
        </div>
        <div class="form-group">
            <label for="status">日期类型</label>
            <form:select id="timeType" path="timeType" class="">
                <form:option value="" label="请选择"/>
                <form:options items="${timeTypeList}" itemLabel="name" itemValue="value"/>
            </form:select>
        </div>
        <div class="form-group input-append date form_datetime">
            <label for="beginTime">发布时间</label>
            <form:input type="text" class="" path="beginTimeStr" id="beginTime"/>
            <span class="add-on"><i class="icon-remove"></i></span>
            <span class="add-on"><i class="icon-calendar"></i></span>
        </div>
        <div class="form-group input-append date form_datetime">
            <label for="beginTime">-</label>
            <form:input type="text" class="" path="endTimeStr" id="endTime"/>
            <span class="add-on"><i class="icon-remove"></i></span>
            <span class="add-on"><i class="icon-calendar"></i></span>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-xs btn-primary">查询</button>
            <button id="exportExel" class="btn btn-xs btn-info">导出</button>
            <button type="button" onclick="location.reload();" class="btn btn-xs btn-danger">刷新</button>
        </div>
    </form:form>
    <div class="panel panel-default">
        <div class="panel-heading">文章</div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover  table-striped table-bordered">
                    <tr class="info">
                        <th>序号</th>
                        <th>标题</th>
                        <th>媒体</th>
                        <th>状态</th>
                        <th>报价</th>
                        <th>发布时间</th>
                        <th>审核时间</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="entity" varStatus="status">
                        <tr class="
                        <c:choose>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVE_ING.status}">
                                        info
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVE_ING.status}">
                                       info
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVED.status&&entity.approveStatus=='2'}">
                                        danger
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVED.status&&entity.verifyStatus!='1'}">
                                        success
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVED.status&&entity.verifyStatus=='1'}">
                                        warning
                                    </c:when>
                                   <c:when test="${entity.status==ArticleStatusEnum.REJECTED.status}">
                                        danger
                                    </c:when>
                                </c:choose>
                        "
                        >
                            <td>${status.index + 1}</td>
                            <td title="${entity.title}">
                                <a href="${entity.url}" target="_blank">${entity.title}
                                    <c:if test="${entity.status==ArticleStatusEnum.REJECTED.status}">
                                        -(${entity.refundRemark})
                                    </c:if>
                                </a>
                            </td>
                            <td title="${entity.mediaName}">${entity.mediaName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${entity.status==ArticleStatusEnum.NEED_APPROVE.status}">
                                        待审核
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVE_ING.status}">
                                        审核中
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVED.status&&entity.verifyStatus!='1'}">
                                        未结算 <c:if test="${entity.approveStatus==2}">（已提取）</c:if>
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.APPROVED.status&&entity.verifyStatus=='1'}">
                                        已结算 <c:if test="${entity.approveStatus==2}">（已提取）</c:if>
                                    </c:when>
                                    <c:when test="${entity.status==ArticleStatusEnum.REJECTED.status}">
                                        已退稿
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>${entity.customPrice}</td>
                            <td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${entity.verifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td title="${entity.remark}">${fn:substring(entity.remark, 0, 10)}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${entity.status==0||entity.status==3}">
                                        <shiro:hasPermission name="bussiness:article:delete">
                                            <a url="${path}/article/delete?id=${entity.id}&typeParam=${article.typeParam}"
                                               data-toggle="tooltip" data-placement="top" title="删除"
                                               name="delete">删除</a>
                                        </shiro:hasPermission>
                                    </c:when>
                                    <c:when test="${entity.status==2}">
                                        <a href="${entity.verifyUrl}"
                                                <c:if test="${entity.approveStatus!=2}">
                                                    onclick="review(${entity.id})"
                                                </c:if>data-toggle="tooltip" data-placement="top" title="查看链接"
                                           target="_blank">查看链接</a>
                                    </c:when>
                                </c:choose>
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
    require(['jquery', 'bootstrap', 'jqueryValidateMessages', 'sweetalert', 'datetimepicker'], function ($) {
        require(['Chosen', 'toastr', 'datetimepickerzh', 'sys'], function () {
            //选择框赋值
            $("select").chosen();
            $("#verifyForm").validate({
                submitHandler: function (form) {
                    form.submit();
                }
            });
            $("a[name='type']").on("click", function () {
                $('#articleForm')[0].reset();
                $("#pageNum").val("1");
                $("select[name='type']").val($(this).attr("value"));
                $('#articleForm').submit();
            })

            $('[data-toggle="tooltip"]').tooltip();//提示框启用

            $("a[name='pages']").on("click", function () {
                $("input[name='pageNum']").val($(this).attr("value"));
                $("#articleForm").submit();
            })

            $("#exportExel").on("click", function () {
                $("#articleForm").attr("action", "${path}/article/export");
                $("#articleForm").submit();
                setTimeout(function () {
                    $("#articleForm").attr("action", "${path}/article/");
                }, 1000)
            })

            $(".form_datetime").datetimepicker({
                language: 'zh-CN',
                weekStart: 1,
                todayBtn: 1,
                autoclose: true,
                todayHighlight: 1,
                timepicker: false,
                minView: 2,
                maxView: 4,
                startView: 2,
                showMeridian: 1,
                format: 'yyyy-mm-dd',
                formatDate: "yyyy-mm-dd",
                clearBtn: true
            });

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
                if (${not empty level }) {
                    toastr.error("${message}");
                } else {
                    toastr.success("${message}");
                }
            }
        })
    })

    function review(id) {
        location.href = "${path}/article/review?id=" + id + "&typeParam=${article.typeParam}";
    }
</script>

</html>