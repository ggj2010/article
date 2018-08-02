<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html zh-CN>
<head>
    <title>媒体列表</title>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>

<div class="container-fluent">
    <ul class="nav nav-tabs">
        <shiro:hasPermission name="bussiness:media:view">
            <li class="active">
                <a href="#">媒体列表</a>
            </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="bussiness:media:edit">
            <li>
                <a href="${path}/media/form?typeParam=${media.typeParam}">媒体${not empty
                        entity.id?'修改':'添加'}</a>
            </li>
        </shiro:hasPermission>
    </ul>
    <div class="panel panel-default">


    </div>
    <form:form id="mediaForm" modelAttribute="media" action="${path}/media" method="post" class="form-inline well"
               enctype="multipart/form-data">
        <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
        <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
        <form:hidden path="typeParam"/>
        <div class="form-group">
            <label>渠道</label>
            <form:select id="mediaChannel" path="mediaChannel" class="form-control">
                <form:option value="" label="请选择"/>
                <form:options items="${mediaChannelList}"/>
            </form:select>
        </div>
        <div class="form-group">
            <label>媒体类型</label>
            <form:select id="mediaType" path="mediaType" class="form-control">
                <form:option value="" label="请选择"/>
                <form:options items="${mediaTypeList}"/>
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
            <label>媒体区域</label>
            <form:select id="mediaRegion" path="mediaRegion" class="form-control">
                <form:option value="" label="请选择"/>
                <form:options items="${mediaRegionList}"/>
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
<%--
        <div class="form-group">
            <shiro:hasPermission name="bussiness:media:import">
                <input type="file" class="form-control" name="meidaExelFile" id="meidaExelFile">
                <a type="button" id="importExel" class="btn btn-info">导入</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="bussiness:media:export">
                <a type="button" id="exportExel" class="btn btn-info">导出</a>
            </shiro:hasPermission>
        </div>--%>
    </form:form>
    <div class="panel panel-default">
        <div class="panel-heading">媒体</div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover  table-striped table-bordered">
                    <tr class="info">
                        <th>渠道</th>
                        <th>名称</th>
                        <c:if test="${principal.userType==0}">
                            <c:if test="${media.typeParam==1}">
                                <th>金/银/铜价格</th>
                            </c:if>
                            <th>成本价格</th>
                        </c:if>
                        <c:if test="${principal.userType==1}">
                            <th>价格</th>
                        </c:if>
                        <th>收录类型</th>
                        <th>媒体类型</th>
                        <th>媒体区域</th>
                        <th>发布速度</th>
                        <th>参考粉丝数</th>
                        <th>参考阅读数</th>
                        <th>百度权重</th>
                        <th>案例网址</th>
                        <th>审核状态</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="entity">
                        <tr>
                            <td>${entity.mediaChannel==null?'自媒体':entity.mediaChannel}</td>
                            <td title="${entity.remark}">${entity.name}</td>
                            <c:if test="${principal.userType==0}">
                                <c:if test="${media.typeParam==1}">
                                    <td>${entity.goldPrice}/${entity.silverPrice}/${entity.bronzePrice}</td>
                                </c:if>
                                <td>${entity.costPrice}</td>
                            </c:if>
                            <c:if test="${principal.userType==1}">
                                <c:if test="${principal.level=='金牌'}">
                                    <td>${entity.goldPrice}</td>
                                </c:if>
                                <c:if test="${principal.level=='铜牌'}">
                                    <td>${entity.bronzePrice}</td>
                                </c:if>
                                <c:if test="${principal.level=='银牌'}">
                                    <td>${entity.silverPrice}</td>
                                </c:if>
                            </c:if>

                            <td>${entity.collectionType}</td>
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
                            <td>${entity.status==1?'未审核':'审核通过'}</td>
                            <td>${entity.remark}
                            </td>
                            <td>
                                <c:if test="${entity.status==2}">
                                <c:if test="${media.typeParam==4}">
                                    <a class="btn btn-info"
                                       href="${path}/media/uncollect?id=${entity.id}&typeParam=${media.typeParam}"
                                       data-toggle="tooltip" data-placement="top" title="取消收藏"><span
                                            class="glyphicon glyphicon glyphicon-star-empty"></span> </a>
                                </c:if>
                                <c:if test="${media.typeParam!=4}">
                                    <a class="btn btn-info"
                                       href="${path}/media/collect?id=${entity.id}&typeParam=${media.typeParam}&mediaChannel=${entity.mediaChannel}"
                                       data-toggle="tooltip" data-placement="top" title="收藏"><span
                                            class="glyphicon glyphicon-star"></span> </a>
                                </c:if>
                                </c:if>
                                <c:choose>
                                    <c:when test="${isRecycle}">
                                        <a class="btn btn-info"
                                           href="${path}/media/restore?id=${entity.id}&typeParam=${media.typeParam}"
                                           data-toggle="tooltip" data-placement="top" title="还原"><span
                                                class="glyphicon glyphicon-repeat"></span> </a>
                                    </c:when>
                                    <c:otherwise>
                                        <shiro:hasPermission name="bussiness:media:approve">
                                            <c:if test="${entity.status==1}">
                                                <a class="btn btn-info"
                                                   href="${path}/media/approve?id=${entity.id}&typeParam=${media.typeParam}"
                                                   data-toggle="tooltip" data-placement="top" title="审核"><span
                                                        class="glyphicon  glyphicon-user"></span> </a>
                                            </c:if>
                                            <c:if test="${entity.status==2}">
                                                <a class="btn btn-info"
                                                   href="${path}/media/settop?id=${entity.id}&typeParam=${media.typeParam}&topType=2"
                                                   data-toggle="tooltip" data-placement="top" title="置顶"><span
                                                        class="glyphicon glyphicon-triangle-top"></span> </a>

                                                <a class="btn btn-info"
                                                   href="${path}/media/form?id=${entity.id}&typeParam=${media.typeParam}"
                                                   data-toggle="tooltip" data-placement="top" title="修改"><span
                                                        class="glyphicon glyphicon-edit"></span> </a>
                                                <a class="btn  btn-info"
                                                   url="${path}/media/delete?id=${entity.id}&typeParam=${media.typeParam}"
                                                   data-toggle="tooltip" data-placement="top" title="删除"
                                                   name="delete"><span
                                                        class="glyphicon glyphicon-trash"></span></a>
                                            </c:if>
                                        </shiro:hasPermission>
                                        <c:if test="${entity.status==1}">
                                            <a class="btn btn-info"
                                               href="${path}/media/form?id=${entity.id}&typeParam=${media.typeParam}"
                                               data-toggle="tooltip" data-placement="top" title="修改"><span
                                                    class="glyphicon glyphicon-edit"></span> </a>
                                            <shiro:hasPermission name="bussiness:media:delete">
                                            <a class="btn  btn-info"
                                               url="${path}/media/delete?id=${entity.id}&typeParam=${media.typeParam}"
                                               data-toggle="tooltip" data-placement="top" title="删除" name="delete"><span
                                                    class="glyphicon glyphicon-trash"></span></a>
                                            </shiro:hasPermission>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
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

            $("#importExel").on("click", function () {
                if ($("#meidaExelFile").val() == "") {
                    swal("导入文件不可以为空");
                } else {
                    $("#mediaForm").attr("action", "${path}/media/import");
                    $("#mediaForm").submit();
                }
            })

            $("#exportExel").on("click", function () {
                $("#mediaForm").attr("action", "${path}/media/export");
                $("#mediaForm").submit();
                $("#mediaForm").attr("action", "${path}/media/");
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