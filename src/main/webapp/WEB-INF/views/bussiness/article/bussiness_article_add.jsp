<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html zh-CN>
<head>
    <title>员工添加</title>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>
<div class="container-fluent">
    <div class="row">
        <div class="col-sm-9">
            <div class="panel panel-info" id="chooseMedia">
                <div class="panel-heading">①选择发布媒体</div>
                <div class="panel-body">
                    <div class="embed-responsive embed-responsive-4by3">
                        <iframe class="embed-responsive-item" src="${path}/media/select" id="iframeSrc"></iframe>
                    </div>
                </div>
            </div>
            <div class="panel panel-info" id="addArticle" style="display: none">
                <div class="panel-heading">输入稿件内容</div>
                <div class="panel-body">
                    <div class="embed-responsive embed-responsive-4by3">
                        <iframe class="embed-responsive-item" src="${path}/article/addsteptwo"></iframe>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="panel panel-info">
                <div class="panel-heading">②已选媒体</div>
                <div class="panel-body">
                    <table class="table table-hover  table-striped table-bordered table-condensed table-responsive" id="appendTable" style="font-size:5px">
                        <th>名称</th>
                        <th>报价</th>
                        <th>操作</th>
                    </table>
                    <div class="alert alert-warning">
                        <a href="#" class="close" data-dismiss="alert">
                            &times;
                        </a>
                        合计：<strong id="countPrice"></strong>
                    </div>
                    <a class="btn btn-danger btn-block" id="nexStep">③下一步</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    require(['jquery', 'bootstrap'], function () {
        require(['jqueryValidateMessages', 'suggest', 'jquerytree', 'sweetalert'], function () {
            require(['sys'], function () {
                var treeObj;
                var setting = {
                    check: {
                        nocheckInherit: true,
                        enable: true
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "pId"
                        }
                    }
                };

                $("#nexStep").on("click",function () {
                    var html= $("#nexStep").html();
                    if(html=="③下一步"){
                        var length=$("#appendTable").children("tbody").children("tr").length;
                        if(length==1){
                            swal("请先选择发布媒体");
                        }else{
                            $("#chooseMedia").hide();
                            $("#addArticle").show();
                            $("#nexStep").html("③上一步")
                        }
                    }else{
                        $("#chooseMedia").show();
                        $("#addArticle").hide();
                        $("#nexStep").html("③下一步")
                    }

                })
            })
        })
    })
    function  appendChoose(id,html) {
        $("#"+id).remove();
        $("#appendTable").append(html);
        getArticleInfo();
    }
    function  getArticleInfo() {
           var media=$("tr");
           var mediaInfos = [];
            var countPrice=0;
           for (var i = 0; i < media.length; i++) {
               var mediaInfo = {};
               if(i==0){
                   continue;
               }
               mediaInfo.id=$(media[i]).attr("id");
               mediaInfo.price=$(media[i]).find("input[type='text']").val();
               mediaInfos.push(mediaInfo);
               countPrice=countPrice+parseInt(mediaInfo.price);
           }
        $("#countPrice").html(countPrice);
     return JSON.stringify(mediaInfos);
    }
    function  deleteChoose(id) {
        $("#"+id).remove();
        getArticleInfo();
    }

</script>
</html>