<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
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
               action="${path}/article?typeParam=${article.typeParam}" method="post" class="form-inline well">
        <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
        <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
        <form:hidden path="typeParam"/>
        <c:if test="${article.typeParam=='1'|| article.typeParam=='2' || article.typeParam==null }">
        <div class="form-group">
            <label for="customName">客户信息</label>
            <form:select id="customName" path="customId" class="form-control">
                <form:option value="" label="请选择"/>
                <form:options items="${customUserInfoList}" itemValue="id"
                              itemLabel="userName"/>
            </form:select>
        </div>
        </c:if>
        <div class="form-group">
            <label for="status">状态</label>
            <form:select id="status" path="status" class="form-control">
                <form:option value="" label="请选择"/>
                <form:options items="${articleStatusList}" itemLabel="name" itemValue="value"/>
            </form:select>
        </div>
        <div class="form-group">
            <label for="title">标题</label>
            <form:input type="text" class="form-control" path="title" id="title"/>
        </div>
        <div class="form-group">
            <label for="status">日期类型</label>
            <form:select id="timeType" path="timeType" class="form-control">
                <form:option value="" label="请选择"/>
                <form:options items="${timeTypeList}" itemLabel="name" itemValue="value"/>
            </form:select>
        </div>
        <div class="form-group input-append date form_datetime">
            <label for="beginTime">发布时间</label>
                <form:input type="text" class="form-control" path="beginTimeStr" id="beginTime"/>
                <span class="add-on"><i class="icon-remove"></i></span>
                <span class="add-on"><i class="icon-calendar"></i></span>
        </div>
        <div class="form-group input-append date form_datetime">
            <label for="beginTime">-</label>
            <form:input type="text" class="form-control" path="endTimeStr" id="endTime"/>
            <span class="add-on"><i class="icon-remove"></i></span>
            <span class="add-on"><i class="icon-calendar"></i></span>
        </div>
        <button type="submit" class="btn btn-info">查询</button>
        <a type="button" id="exportExel" class="btn btn-info">导出</a>
        <a type="button" onclick="location.reload();" class="btn btn-info">刷新</a>
    </form:form>
    <div class="panel panel-default">
        <div class="panel-heading">文章</div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover  table-striped table-bordered">
                    <tr class="info">
                        <th>标题</th>
                        <th>媒体</th>
                        <th>状态</th>
                        <th>客户</th>
                        <th>报价</th>
                        <th>发布时间</th>
                        <th>审核时间</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="entity">
                        <tr class="
                        <c:choose>
                                    <c:when test="${entity.status==0}">
                                        warning
                                    </c:when>
                                    <c:when test="${entity.status==1}">
                                       success
                                    </c:when>
                                    <c:when test="${entity.status==2}">
                                       success
                                    </c:when>
                                    <c:when test="${entity.status==3}">
                                        danger
                                    </c:when>
                                </c:choose>
                        "
                        >
                            <td title="${entity.title}"><a href="<c:choose><c:when test="${entity.status==2}">${entity.verifyUrl}</c:when><c:otherwise>${entity.url}</c:otherwise>
</c:choose>"                                            <shiro:hasPermission name="bussiness:mediaEditor:edit">
                                                           <c:if test="${entity.status==0}">onclick="verifying(${entity.id})"</c:if>
                                </shiro:hasPermission>

                                                           target="_blank">${fn:substring(entity.title, 0, 10)}</a></td>
                            <td title="${entity.mediaName}">${fn:substring(entity.mediaName, 0, 10)}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${entity.status==0}">
                                        待审核
                                    </c:when>
                                    <c:when test="${entity.status==1}">
                                        审核中
                                    </c:when>
                                    <c:when test="${entity.status==2}">
                                        已审核
                                    </c:when>
                                    <c:when test="${entity.status==3}">
                                        已退稿
                                    </c:when>
                                    <c:when test="${entity.status==4}">
                                        已删除
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>${entity.customName}</td>
                            <c:choose>
                                <c:when test="${article.typeParam=='2'}">
                                    <td>${entity.costPrice}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${entity.customPrice}</td>
                                </c:otherwise>
                            </c:choose>
                            <td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${entity.verifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td title="${entity.remark}">${fn:substring(entity.remark, 0, 10)}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${entity.status==0||entity.status==1}">
                                        <shiro:hasPermission name="bussiness:mediaEditor:edit">
                                        <a class="btn btn-info"
                                           href="javaScript:verify('${entity.title}','${entity.id}')"
                                           data-toggle="tooltip" data-placement="top" title="审核"><span
                                                class="glyphicon glyphicon-user"></span> </a>

                                        <a class="btn  btn-info"
                                           href="javaScript:back('${entity.id}')"
                                           data-toggle="tooltip" data-placement="top" title="退稿"
                                           ><span
                                                class="glyphicon glyphicon-step-backward"></span></a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="bussiness:media:delete">
                                        <a class="btn  btn-info" url="${path}/article/delete?id=${entity.id}&typeParam=${article.typeParam}"
                                           data-toggle="tooltip" data-placement="top" title="删除"
                                           name="delete"><span
                                                class="glyphicon glyphicon-trash"></span></a>
                                        </shiro:hasPermission>
                                    </c:when>
                                    <c:when test="${entity.status==3}">
                                        <shiro:hasPermission name="bussiness:media:delete">
                                        <a class="btn  btn-info" url="${path}/article/delete?id=${entity.id}&typeParam=${article.typeParam}"
                                           data-toggle="tooltip" data-placement="top" title="删除"
                                           name="delete"><span
                                                class="glyphicon glyphicon-trash"></span></a>
                                        </shiro:hasPermission>
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


<div class="modal fade" id="verifyModule" tabindex="-1"
     aria-labelledby="uploadModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">审核稿件</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal">
                        <form action="${path}/article/verifysave?typeParam=${article.typeParam}" method="post" id="verifyForm">
                            <div class="form-group">
                                <label for="title" class="col-sm-2 control-label">稿件标题</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" readonly="readonly" id="articleTitleId">
                                    <input type="hidden" class="form-control" name="id" id="articleId">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="verifyUrl" class="col-sm-2 control-label">稿件地址</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control required" name="verifyUrl" id="verifyUrl">
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

            $("#exportExel").on("click",function(){
                $("#articleForm").attr("action","${path}/article/export");
                $("#articleForm").submit();
                $("#articleForm").attr("action","${path}/article/");
            })

            $(".form_datetime").datetimepicker({
                language: 'zh-CN',
                weekStart: 1,
                todayBtn: 1,
                autoclose:true,
                todayHighlight: 1,
                timepicker:false,
                minView:2,
                maxView:4,
                startView: 2,
                showMeridian: 1,
                format:'yyyy-mm-dd',
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

    function verifying(id) {
        location.href = "${path}/article/verifying?id=" + id+"&typeParam=${article.typeParam}";
    }
    function back(id) {
        location.href = "${path}/article/back?id=" + id+"&typeParam=${article.typeParam}";
    }
    function verify(name, id) {
        $("#articleTitleId").val(name);
        $("#articleId").val(id);
        $("#verifyModule").modal('show');
    }
</script>

</html>