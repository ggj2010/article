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
                            <div class="input-group">
                                <input class="form-control required" id="customNameId" maxlength="255" />
                                <input class="form-control required" id="customId" maxlength="255" type="hidden" />
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
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="remark1">备注</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" maxlength="255"
                                           id="remark1" rows="3" name="remark1">
                                </textarea>
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
        require(['Chosen', 'toastr', 'suggest','sys'], function () {
            //选择框赋值
            $("select").chosen();
            $("#customInfo_chosen").css("width","100px");
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


            var taobaoBsSuggest = $("#customNameId").bsSuggest({
                indexId: 1,             //data.value 的第几个数据，作为input输入框的内容
                indexKey: 0,            //data.value 的第几个数据，作为input输入框的内容
                allowNoKeyword: false,  //是否允许无关键字时请求数据。为 false 则无输入时不执行过滤请求
                multiWord: true,        //以分隔符号分割的多关键字支持
                separator: ",",         //多关键字支持时的分隔符，默认为空格
                getDataMethod: "url",   //获取数据的方式，总是从 URL 获取
                showHeader: true,       //显示多个字段的表头
                showBtn: false,
                effectiveFieldsAlias:{userName: "客户名称"},
                url: '${path}/article/ajax/getCustomInfoList?name=', /*优先从url ajax 请求 json 帮助数据，注意最后一个参数为关键字请求参数*/
                //jsonp: 'callback',               //如果从 url 获取数据，并且需要跨域，则该参数必须设置
                processData: function(json){     // url 获取数据时，对数据的处理，作为 getData 的回调函数
                    var i, len, data = {value: []};
                    len = json.length;
                    if(len>0) {
                        for (i = 0; i < len; i++) {
                            data.value.push({
                                "userName": json[i].userName,
                                "userId": json[i].userId
                            });
                        }
                    }
                    $("#customId").val("0");
                    console.log(data);
                    return data;
                }


            }).on('onSetSelectValue', function (e, keyword) {
                $("#customNameId").val(keyword.key);
                $("#customId").val(keyword.id);
            });



            $("#entityForm").validate({
                submitHandler: function (form) {
                    var articleInfos = [];
                    var article=$(".panel-default");
                    for (var i = 0; i < article.length; i++) {
                        var articleInfo = {};
                        articleInfo.title=$(article[i]).find("input[name='title']").val();
                        if(i==0){
                            var userType="${principal.userType}";
                            if(userType=="0") {
                                articleInfo.customId = $("#customId").val();
                                articleInfo.customName = $("#customNameId").val();
                            }else {
                                articleInfo.customId = "${principal.id}";
                                articleInfo.customName = "(客发)-${principal.name}";
                            }
                        }
                        articleInfo.type=$(article[i]).find("input[name='type"+(i+1)+"']:checked").val();
                        articleInfo.url=$(article[i]).find("input[name='url"+(i+1)+"']").val();
                        articleInfo.fileName="file"+(i+1);
                        articleInfo.remark=$(article[i]).find("textarea[name='remark"+(i+1)+"']").val();
                        articleInfos.push(articleInfo);
                    }
                    var jsonArticleInfo=JSON.stringify(articleInfos);
                    var jsonMediaInfo=window.parent.getArticleInfo();
                    $("#articleInfo").val(jsonArticleInfo);
                    $("#mediaInfo").val(jsonMediaInfo);
                    form.submit();
                }
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
        html+="<div class=\"form-group\">";
        html+="<label class=\"col-sm-2 control-label\" for=\"remark"+length+"\">备注</label>";
        html+="<div class=\"col-sm-8\">";
        html+="<textarea class=\"form-control\" maxlength=\"255\" id=\"remark"+length+"\"  name=\"remark"+length+"\" rows=\"3\"></textarea>";
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