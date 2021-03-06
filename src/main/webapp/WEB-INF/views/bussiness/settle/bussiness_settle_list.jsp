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
        <div id="settleParamCopy">
            <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
            <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
            <input type="hidden" name="formUrl" id="formUrl" value="${formUrl}">
            <input type="hidden" name="bussinnessType" id="bussinnessType" value="${mediaSettleMent.bussinnessType}">
            <div class="form-group">
                <label for="status">结算状态</label>
                <form:select id="status" path="status" class="form-control">
                    <form:option value="" label="请选择"/>
                    <form:options items="${articleStatusList}" itemLabel="name" itemValue="value"/>
                </form:select>
            </div>
            <c:if test="${mediaSettleMent.bussinnessType=='2'||mediaSettleMent.bussinnessType=='3'}">
                <c:if test="${formUrl=='custom'&&mediaSettleMent.bussinnessType!='3'}">
                    <div class="form-group">
                        <label for="userName">
                            客户信息
                        </label>
                        <form:select id="userName" path="article.customId" class="form-control">
                            <form:option value="" label="请选择"/>
                            <form:options items="${customUserInfoList}" itemValue="userId"
                                          itemLabel="userName"/>
                        </form:select>
                    </div>
                </c:if>
                <c:if test="${formUrl=='editor'||formUrl=='user'||mediaSettleMent.bussinnessType=='3'}">
                    <div class="form-group">
                        <label for="userName">
                                ${formUrl=='editor'?'编辑信息':'员工信息'}
                        </label>
                        <form:select id="userName" path="article.userId" class="form-control">
                            <form:option value="" label="请选择"/>
                            <form:options items="${userInfoList}" itemValue="id"
                                          itemLabel="userName"/>
                        </form:select>
                    </div>
                </c:if>
            </c:if>
            <div class="form-group">
                <label for="status">日期类型</label>
                <form:select id="timeType" path="article.timeType" class="form-control">
                    <form:option value="" label="请选择"/>
                    <form:options items="${timeTypeList}" itemLabel="name" itemValue="value"/>
                </form:select>
            </div>
            <div class="form-group input-append date form_datetime">
                <label for="beginTime">发布时间</label>
                <form:input type="text" class="form-control" path="article.beginTimeStr" id="beginTime"/>
                <span class="add-on"><i class="icon-remove"></i></span>
                <span class="add-on"><i class="icon-calendar"></i></span>
            </div>
            <div class="form-group input-append date form_datetime">
                <label for="beginTime">-</label>
                <form:input type="text" class="form-control" path="article.endTimeStr" id="endTime"/>
                <span class="add-on"><i class="icon-remove"></i></span>
                <span class="add-on"><i class="icon-calendar"></i></span>
            </div>
            <button type="submit" class="btn btn-xs btn-info">查询</button>
            <a type="button" onclick="location.reload();" class="btn btn-xs btn-info">刷新</a>
            <shiro:hasPermission name="bussiness:settle:form">
                <c:if test="${mediaSettleMent.bussinnessType==2}">
                    <a type="button" onclick="settleMore()" class="btn  btn-xs btn-danger">批量结算</a>
                </c:if>
            </shiro:hasPermission>
            <button id="exportExel" class="btn btn-xs btn-info">导出</button>
        </div>
    </form:form>
    <div class="panel panel-default">
        <div class="panel-heading">结算列表
            <div class="pull-right">
                <button class="btn btn-xs btn-info" type="button">
                    总数量 <span class="badge">${totalSize}</span>
                </button>
                <button class="btn btn-xs btn-info" type="button">
                    已结算 <span class="badge">${settleSize}</span>
                </button>
                <button class="btn btn-xs btn-danger" type="button">
                    未结算 <span class="badge">${totalSize-settleSize}</span>
                </button>
                <button class="btn btn-xs btn-info" type="button">
                    总金额 <span class="badge">${totalPrice}</span>
                </button>
                <button class="btn btn-xs btn-info" type="button">
                    已结算金额 <span class="badge">${settlePrice}</span>
                </button>
                <button class="btn btn-xs btn-danger" type="button">
                    未结算金额 <span class="badge">${costPrice}</span>
                </button>
            </div>
        </div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover  table-striped table-bordered">
                    <tr class="info">
                        <th></th>
                        <c:if test="${mediaSettleMent.bussinnessType==2}">
                            <th><input type="checkbox" name="settleRadio" id="choseAll"></th>
                        </c:if>
                        <th>标题</th>
                        <th>媒体</th>
                        <th>状态</th>
                        <c:if test="${mediaSettleMent.bussinnessType=='2'&&formUrl=='user'}">
                            <th>员工</th>
                            <th>报价</th>
                        </c:if>
                        <c:if test="${(mediaSettleMent.bussinnessType=='2'||mediaSettleMent.bussinnessType=='1')&&formUrl=='custom'}">
                            <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                                <th>顾客</th>
                            </c:if>
                            <th>报价</th>
                        </c:if>
                        <c:if test="${(mediaSettleMent.bussinnessType=='2'||mediaSettleMent.bussinnessType=='1')&&formUrl=='editor'}">
                            <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                                <th>编辑</th>
                            </c:if>
                            <th>成本价格</th>
                        </c:if>
                        <th>结算价格</th>
                        <th>发布时间</th>
                        <th>审核时间</th>
                        <th>备注</th>
                        <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                            <th>操作</th>
                        </c:if>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="entity" varStatus="status">
                        <tr
                                <c:if test="${entity.status=='0'}">class="success"</c:if>
                                <c:if test="${entity.status=='1'}">class="danger"</c:if>  >
                            <td>${status.count}</td>
                            <c:if test="${mediaSettleMent.bussinnessType==2}">
                                <c:if test="${entity.status=='0'}">
                                    <td entityId="${entity.id}" type="${entity.type}"><input type="checkbox"
                                                                                             name="settleRadio-${status.count}">
                                    </td>
                                </c:if>
                                <c:if test="${entity.status=='1'}">
                                    <td></td>
                                </c:if>
                            </c:if>
                            <td title="${entity.article.title}">${entity.article.title}</td>
                            <td title="${entity.article.mediaName}">${entity.article.mediaName}</td>
                            <td>
                                <c:if test="${entity.status=='0'}">
                                    未结算
                                </c:if>
                                <c:if test="${entity.status=='1'}">
                                    已结算
                                </c:if>
                            </td>
                            <c:if test="${mediaSettleMent.bussinnessType=='2'&&formUrl=='user'}">
                                <td>${entity.article.userName}</td>
                                <td class="settleCustomPrice">${entity.article.customPrice}</td>
                            </c:if>
                            <c:if test="${(mediaSettleMent.bussinnessType=='2'||mediaSettleMent.bussinnessType=='1')&&formUrl=='custom'}">
                                <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                                    <td>${entity.article.customName}</td>
                                </c:if>
                                <td class="settleCustomPrice">${entity.article.customPrice}</td>
                            </c:if>
                            <c:if test="${(mediaSettleMent.bussinnessType=='2'||mediaSettleMent.bussinnessType=='1')&&formUrl=='editor'}">
                                <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                                    <td>${entity.article.editorName}</td>
                                </c:if>
                                <td class="settleCostPrice">${entity.article.costPrice}</td>
                            </c:if>
                            <td>${entity.price}</td>
                            <td><fmt:formatDate value="${entity.article.createDate}"
                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${entity.article.verifyDate}"
                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td title="${entity.remark}">${fn:substring(entity.remark, 0, 10)}</td>
                            <c:if test="${mediaSettleMent.bussinnessType=='2'}">
                                <td>
                                    <shiro:hasPermission name="bussiness:settle:form">
                                        <c:if test="${entity.status=='0'}">
                                            <a href="javaScript:settleView('${entity.id}','${entity.type}','${entity.article.title}','${entity.article.costPrice}','${entity.article.customPrice}')"
                                            >结算</a>
                                        </c:if>
                                        <c:if test="${mediaSettleMent.bussinnessType=='2'&&formUrl=='user'}">
                                            <a href="javascript:;"
                                               url="${path}/settle/delete?id=${entity.id}&bussinnessType=${mediaSettleMent.bussinnessType}"
                                               name="delete" type="button" class="btn-xs btn-info">删除</a>
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
                        <form action="${path}/settle/save" method="post" id="settleParamForm">
                            <div style="display: none" id="settleParam">

                            </div>
                            <div class="form-group">
                                <label for="articleTitleId" class="col-sm-2 control-label">文章标题</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" readonly="readonly" id="articleTitleId">
                                    <input type="hidden" class="form-control" name="id" id="settlementId">
                                    <input type="hidden" class="form-control" name="type" id="typeId">
                                    <input type="hidden" class="form-control" name="settleFormUrl"
                                           value="${formUrl}?bussinnessType=${mediaSettleMent.bussinnessType}">
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
                                    <input type="text" class="form-control required" id="realPayPrice"
                                           name="price">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="remark">备注</label>
                                <div class="col-sm-8">
                                    <textarea name="remark" class="form-control" maxlength="255" rows="3"
                                              id="remark"></textarea>
                                    <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                                </div>
                            </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary" onclick="addSettleParam()">保存</button>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="moreSettleModule" tabindex="-1"
     aria-labelledby="uploadModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">批量结算</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal">
                        <form action="${path}/settle/moresave" method="post">
                            <div style="display: none" id="settleParam2">

                            </div>
                            <div class="form-group">
                                <label for="articleTitleId" class="col-sm-2 control-label">稿件数量</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" readonly="readonly" id="articleNum">
                                    <input type="hidden" id="settleArticleIds" name="settleArticleIds">
                                    <input type="hidden" class="form-control" name="type" id="settleType">
                                    <input type="hidden" class="form-control" name="settleFormUrl"
                                           value="${formUrl}?bussinnessType=${mediaSettleMent.bussinnessType}">
                                    <input type="hidden" class="form-control" id="priceStr" name="priceStr">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="priceId" class="col-sm-2 control-label">应付价格</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" readonly="readonly" id="morePrice">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="realPayPrice" class="col-sm-2 control-label">实付价格</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control required" id="moreRealPayPrice"
                                           name="price" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="remark">备注</label>
                                <div class="col-sm-8">
                                    <textarea name="remark" class="form-control" maxlength="255" rows="3"></textarea>
                                    <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                                </div>
                            </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary" onclick="addSettleParam2()">保存</button>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    require(['jquery', 'bootstrap', 'datetimepicker', 'jqueryValidateMessages', 'sweetalert'], function ($) {
        require(['Chosen', 'toastr', 'datetimepickerzh', 'sys'], function () {
            //选择框赋值
            $("select").chosen();

            $("a[name='type']").on("click", function () {
                $('#settleForm')[0].reset();
                $("#pageNum").val("1");
                $("select[name='type']").val($(this).attr("value"));
                $('#settleForm').submit();
            })

            $("#settleParamForm").validate();

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

            $('[data-toggle="tooltip"]').tooltip();//提示框启用

            $("a[name='pages']").on("click", function () {
                $("input[name='pageNum']").val($(this).attr("value"));
                $("#settleForm").submit();
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

            $("#exportExel").on("click", function () {
                $("#settleForm").attr("action", "${path}/settle/export");
                $("#settleForm").submit();
                setTimeout(function () {
                    $("#settleForm").attr("action", "${path}/settle/${formUrl}");
                }, 1000)
            })

            $("#choseAll").on("click", function () {
                var checked = $(this).prop("checked")
                if (checked == true) {
                    $("input[type='checkbox']").each(function () {
                        $(this).prop("checked", true);
                    })
                } else {
                    $("input[type='checkbox']").each(function () {
                        $(this).prop("checked", false);
                    })
                }
            })
        })
    })

    function addSettleParam() {
        $("#settleParam").html($("#settleParamCopy").html());
        return true;
    }

    function addSettleParam2() {
        $("#settleParam2").html($("#settleParamCopy").html());
        return true;
    }

    //结算多个
    function settleMore() {
        var sum = 0;
        var sumPrice = 0;
        var priceStr = "";
        var settlePrice = 0;
        var ids = "";
        var type = ""
        $("input[type='checkbox']").each(function () {
            var checked = $(this).prop("checked")
            if (checked == true) {
                if (${formUrl=='editor'}) {
                    settlePrice = $(this).parent().parent().children(".settleCostPrice").html();
                } else {
                    settlePrice = $(this).parent().parent().children(".settleCustomPrice").html();
                }
                if (settlePrice != null) {
                    sum += 1;
                    ids = $(this).parent().attr("entityId") + "," + ids;
                    type = $(this).parent().attr("type");
                    priceStr = settlePrice + "," + priceStr;
                    sumPrice += parseInt(settlePrice);
                }
            }
        })
        if (sumPrice == 0) {
            swal("请勾选需要结算的稿件!");
        } else {
            $("#articleNum").val(sum);
            $("#morePrice").val(sumPrice);
            $("#settleArticleIds").val(ids);
            $("#settleType").val(type);
            $("#priceStr").val(priceStr);
            $("#moreRealPayPrice").val(sumPrice);
            $("#moreSettleModule").modal('show');
        }
    }


    function settleView(id, type, title, price, customPrice) {
        $("#settlementId").val(id);
        $("#articleTitleId").val(title);
        $("#typeId").val(type);
        //编辑价格和客户价格
        if (type == "3") {
            $("#priceId").val(price);
        } else {
            $("#priceId").val(customPrice);
        }
        $("#verifyModule").modal('show');
    }

    function deleteArticle(id) {
        $("#" + id).parent().remove();
    }
</script>

</html>