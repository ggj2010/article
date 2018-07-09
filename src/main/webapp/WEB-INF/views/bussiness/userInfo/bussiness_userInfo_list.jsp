<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html zh-CN>
<head>
    <title>员工列表</title>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>

<div class="container-fluent">
    <ul class="nav nav-tabs">
        <shiro:hasPermission name="bussiness:userInfo:view">
            <li class="active">
                <a href="#">员工列表</a>
            </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="bussiness:userInfo:edit">
            <li>
                <a href="${path}/userInfo/form">员工${not empty
                        entity.id?'修改':'添加'}</a>
            </li>
        </shiro:hasPermission>
    </ul>
    <form:form id="userInfoForm" modelAttribute="userInfo"
               action="${path}/userInfo/" method="post" class="form-inline well">
        <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
        <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
        <div class="form-group">
            <label for="userName">员工名称</label>
            <form:input type="text" class="form-control" path="userName" id="userName"/>
        </div>
        <button type="submit" class="btn btn-info">查询</button>
    </form:form>
    <div class="panel panel-default">
        <div class="panel-heading">员工</div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover  table-striped table-bordered">
                    <tr class="info">
                        <th>姓名</th>
                        <th>登录名称</th>
                        <th>手机</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="entity">
                        <tr <c:if test="${entity.status==2}">class="danger" </c:if> >
                            <td>${entity.userName}</td>
                            <td>${entity.loginName}</td>
                            <td>${entity.phoneNumber}</td>
                            <td>${entity.status==1?'审核通过':'未审核'}</td>
                            <td>
                                <a class="btn btn-info" href="${path}/userInfo/addcustom?id=${entity.id}"
                                   data-toggle="tooltip" data-placement="top" title="添加客户"><span
                                        class="glyphicon glyphicon-plus"></span> </a>
                                <a class="btn btn-info" href="${path}/userInfo/addmedia?id=${entity.id}"
                                   data-toggle="tooltip" data-placement="top" title="添加文章"><span
                                        class="glyphicon glyphicon-book"></span> </a>
                                <a class="btn btn-info" href="${path}/userInfo/form?id=${entity.id}"
                                   data-toggle="tooltip" data-placement="top" title="修改"><span
                                        class="glyphicon glyphicon-edit"></span> </a>
                                <a class="btn  btn-info" url="${path}/userInfo/delete?id=${entity.id}"
                                   data-toggle="tooltip" data-placement="top" title="删除"
                                   name="delete"><span
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
                $('#userInfoForm')[0].reset();
                $("#pageNum").val("1");
                $("select[name='type']").val($(this).attr("value"));
                $('#userInfoForm').submit();
            })

            $('[data-toggle="tooltip"]').tooltip();//提示框启用

            $("a[name='pages']").on("click", function () {
                $("input[name='pageNum']").val($(this).attr("value"));
                $("#userInfoForm").submit();
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
                if (${not empty level }){
                    toastr.error("${message}");
                }else{
                    toastr.success("${message}");
                }
            }
        })
    })
</script>

</html>