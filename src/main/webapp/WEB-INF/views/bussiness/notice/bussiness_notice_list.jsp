<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html zh-CN>
<head>
    <title>通告列表</title>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>

<div class="container-fluent">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="#">通告列表</a>
        </li>
        <li>
            <a href="${path}/notice/form">通告${not empty
                    entity.id?'修改':'添加'}</a>
        </li>
    </ul>
    <form:form id="noticeForm" modelAttribute="notice"
               action="${path}/sys/notice/" method="post" class="form-inline well">
        <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
        <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
        <div class="form-group">
            <label for="标题">标题</label>
            <form:input type="text" class="form-control" path="title"/>
        </div>
        <button type="submit" class="btn btn-info">查询</button>
    </form:form>
    <div class="panel panel-default">
        <div class="panel-heading">字典列表</div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover  table-striped table-bordered">
                    <tr class="info">
                        <th>标题</th>
                        <th>内容</th>
                        <th>创建日期</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="entity">
                        <tr>
                            <td>${entity.title}</td>
                            <td>${entity.content}</td>
                            <td><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <a class="btn btn-info" href="${path}/notice/form?id=${entity.id}" data-toggle="tooltip"
                                   data-placement="top" title="修改"><span class="glyphicon glyphicon-edit"></span> </a>
                                <a class="btn  btn-info" url="${path}/notice/delete?id=${entity.id}"
                                   data-toggle="tooltip" data-placement="top" title="删除" name="delete"><span
                                        class="glyphicon glyphicon-trash"></span></a>
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
    require(['jquery', 'bootstrap', 'jqueryValidateMessages', 'sweetalert'], function ($) {
        require(['Chosen', 'toastr', 'sys'], function () {
            //选择框赋值
            $("select").chosen();

            $("a[name='type']").on("click", function () {
                $('#noticeForm')[0].reset();
                $("#pageNum").val("1");
                $("select[name='type']").val($(this).attr("value"));
                $('#noticeForm').submit();
            })

            $('[data-toggle="tooltip"]').tooltip();//提示框启用

            $("a[name='pages']").on("click", function () {
                $("input[name='pageNum']").val($(this).attr("value"));
                $("#noticeForm").submit();
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
</script>

</html>