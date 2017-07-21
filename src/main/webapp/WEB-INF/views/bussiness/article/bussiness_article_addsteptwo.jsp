<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html zh-CN>
<head>
    <title>添加稿件</title>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>
<div class="container-fluent">
    <form:form id="entityForm" modelAttribute="article" action="${path}/article/save" method="post"
               cssClass="form-horizontal" enctype="multipart/form-data">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="heading1">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1"
                       aria-expanded="true" aria-controls="collapse1">
                        稿件（点击隐藏）
                    </a>
                </h4>
            </div>
            <div id="collapse1" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading1">
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-8">
                            <input class="form-control required" maxlength="255" autocomplete="off" name="title"/>
                            <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    <c:if test="${principal.userType==0}">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">客户信息</label>
                        <div class="col-sm-8">
                            <label class="radio-inline">
                                <input type="radio" class="required" maxlength="255" autocomplete="off" name="custom" value="old" checked="true" />老客户
                            </label>
                            <label class="radio-inline">
                                <input type="radio" class="required" maxlength="255" autocomplete="off" name="custom"  value="new"  />新客户
                            </label>
                            <form:select id="customInfo" path="customId" class="form-control">
                                <form:option value="" label="请选择"/>
                                <form:options items="${customUserInfoList}" itemValue="id"
                                              itemLabel="userName" />
                            </form:select>
                            <input class="form-control" maxlength="255" autocomplete="off" id="customName"  type="hidden"/>
                            <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    </c:if>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">稿件类型</label>
                        <div class="col-sm-8">
                            <label class="radio-inline">
                                <input type="radio" class="required" maxlength="255" autocomplete="off" name="type1" value="1" seq="1" />原稿
                            </label>
                            <label class="radio-inline">
                                <input type="radio" class="required" maxlength="255" autocomplete="off" name="type1"  value="2" checked="true"  seq="1" />连接
                            </label>
                            <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">来源网址</label>
                        <div class="col-sm-8">
                            <input class="form-control" maxlength="255" autocomplete="off" name="url1" id="url1"/>
                            <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">原稿文件</label>
                        <div class="col-sm-8">
                            <input type="file" class="form-control" name="file1" readonly id="file1"/>
                            <span class="glyphicon  form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-info" type="submit">提交</button>
            <input type="hidden" id="articleInfo" name="articleInfo">
            <input type="hidden" id="mediaInfo" name="mediaInfo">
            <a class="btn btn-danger" href="javaScript:addMore()">继续添加</a>
            <a class="btn btn-default" href="javaScript:history.go(-1)">返回</a>
        </div>
    </div>
    </form:form>
</div>
</body>

<script type="text/javascript">
    require(['jquery', 'bootstrap', 'jqueryValidateMessages'], function ($) {
        require(['Chosen', 'toastr', 'sys'], function () {
            //选择框赋值
            $("#customInfo").chosen();

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
            $("#accordion").on("click",'input[type=\'radio\']', function () {
                //1就是稿件
              if($(this).val()==1){
                  $("#file"+$(this).attr("seq")).removeAttr("readonly");
                  $("#url"+$(this).attr("seq")).attr("readonly","true");
              }else if($(this).val()==2){
                  $("#file"+$(this).attr("seq")).attr("readonly","true");
                  $("#url"+$(this).attr("seq")).removeAttr("readonly");
              }else if($(this).val()=='new'){
                  $("#customInfo_chosen").hide();
                  $("#customName").attr("type","text");
              }else if($(this).val()=='old'){
                  $("#customName").attr("type","hidden");
                  $("#customInfo_chosen").show();
              }
            })

            $("#entityForm").validate({
                submitHandler: function (form) {
                    var articleInfos = [];
                    var article=$(".panel-default");
                    for (var i = 0; i < article.length; i++) {
                        var articleInfo = {};
                        articleInfo.title=$(article[i]).find("input[name='title']").val();
                        if(i==0){
                            var customType=$("input[name='custom']:checked").val();
                            if(customType=="old"){
                                articleInfo.customId=$(article[i]).find("select[name='customId']").val();
                                articleInfo.customName=$(article[i]).find("option:selected").text();
                            }else{
                                articleInfo.customId="0";
                                articleInfo.customName=$("#customName").val();
                            }
                        }
                        articleInfo.type=$(article[i]).find("input[name='type"+(i+1)+"']:checked").val();
                        articleInfo.url=$(article[i]).find("input[name='url"+(i+1)+"']").val();
                        articleInfo.fileName="file"+(i+1);
                        articleInfos.push(articleInfo);
                    }
                    var jsonArticleInfo=JSON.stringify(articleInfos);
                    var jsonMediaInfo=window.parent.getArticleInfo();
                    $("#articleInfo").val(jsonArticleInfo);
                    $("#mediaInfo").val(jsonMediaInfo);
                    form.submit();
                }
            });

            $("a[name='choose']").on("click", function () {
                var id = $(this).parent().parent().children('td:eq(0)').html();
                var name = $(this).parent().parent().children('td:eq(1)').html();
                var price = $(this).parent().parent().children('td:eq(2)').html();
                var html = "";
                html += "<tr id=\"" + id + "\">";
                html += "<td>" + name + "</td>";
                <c:if test="${principal.userType==0}">
                html += "<td><input type=\"text\" value=\"" + price + "\"><input type=\"hidden\" value=\"" + id + "\"></td>";
                </c:if>
                <c:if test="${principal.userType==1}">
                html += "<td><input type=\"text\" value=\"" + price + "\" readonly><input type=\"hidden\" value=\"" + id + "\"></td>";
                </c:if>
                html += "<td><a class=\"btn btn-info\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"删除\" onclick='deleteChoose(\"" + id + "\")'><span class=\"glyphicon glyphicon-trash\"></span></td>";
                html += "</tr>";
                window.parent.appendChoose(id, html);
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

    function addMore() {
        var length=$(".panel-heading").length+1;
        var html="";
        var html=" <div class=\"panel panel-default\">";
        html+="<div class=\"panel-heading\" role=\"tab\" id=\"heading"+length+"\">";
        html+="<h4 class=\"panel-title\">";
        html+="<a role=\"button\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse"+length+"\"aria-expanded=\"true\" aria-controls=\"collapse"+length+"\"> 稿件（点击隐藏）</a>";
        html+=" <a class=\"btn  btn-info\" onclick='deleteArticle(\"collapse"+length+"\")' data-toggle=\"tooltip\" data-placement=\"top\" title=\"删除\"name=\"delete\"><span class=\"glyphicon glyphicon-trash\"></span></a>";
        html+="</h4>";
        html+="</div>";
        html+="<div id=\"collapse"+length+"\" class=\"panel-collapse collapse in\" role=\"tabpanel\" aria-labelledby=\"heading"+length+"\">";
        html+="<div class=\"panel-body\">";
        html+=" <div class=\"form-group\">";
        html+="<label class=\"col-sm-2 control-label\">标题</label>";
        html+="<div class=\"col-sm-8\">";
        html+="<input class=\"form-control required\" maxlength=\"255\" autocomplete=\"off\" name=\"title\"/>";
        html+="<span class=\"glyphicon  form-control-feedback\" aria-hidden=\"true\"></span>";
        html+=" </div>";
        html+="  </div>";
        html+="<div class=\"form-group\">";
        html+="<label class=\"col-sm-2 control-label\">稿件类型</label>";
        html+="<div class=\"col-sm-8\">";
        html+="<label class=\"radio-inline\">";
        html+="<input type=\"radio\" class=\"required\" maxlength=\"255\" autocomplete=\"off\" name=\"type"+length+"\" value=\"1\" seq=\""+length+"\"/>原稿";
        html+="</label>";
        html+="<label class=\"radio-inline\">";
        html+="<input type=\"radio\" class=\"required\" maxlength=\"255\" autocomplete=\"off\" name=\"type"+length+"\" checked=\"true\" value=\"2\" seq=\""+length+"\"/>连接";
        html+="</label>";
        html+="<span class=\"glyphicon  form-control-feedback\" aria-hidden=\"true\"></span>";
        html+="</div>";
        html+="</div>";
        html+="<div class=\"form-group\">";
        html+="<label class=\"col-sm-2 control-label\">来源网址</label>";
        html+=" <div class=\"col-sm-8\">";
        html+="<input class=\"form-control\" maxlength=\"255\" autocomplete=\"off\" name=\"url"+length+"\" id=\"url"+length+"\"/>";
        html+="<span class=\"glyphicon  form-control-feedback\" aria-hidden=\"true\"></span>";
        html+="</div>";
        html+="</div>";
        html+="<div class=\"form-group\">";
        html+="<label class=\"col-sm-2 control-label\">原稿文件</label>";
        html+="<div class=\"col-sm-8\">";
        html+="<input type=\"file\" class=\"form-control\" name=\"file"+length+"\"readonly id=\"file"+length+"\" />";
        html+="<span class=\"glyphicon  form-control-feedback\" aria-hidden=\"true\"></span>";
        html+="</div>";
        html+="</div>";
        html+="</div>";
        html+="</div>";
        html+="</div>";
        $("#accordion").append(html);
    }

    function deleteArticle(id){
        $("#"+id).parent().remove();
    }
</script>

</html>