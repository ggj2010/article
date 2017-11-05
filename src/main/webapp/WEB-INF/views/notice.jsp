<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="Bookmark" href="/image/favicon.ico">
    <link rel="Shortcut Icon" href="/image/favicon.ico">

    <link type="text/css" rel="stylesheet"
          href="/css/bootstrap-3.3.5/css/bootstrap.min.css">
    <!--字体  -->
    <link type="text/css"
          href="/css/font-awesome/css/font-awesome.css"
          rel="stylesheet">
    <link type="text/css" href="/css/style.css"
          rel="stylesheet">
</head>
<body class="gray-bg">
<div class="row">
    <div class="col-sm-12">
        <div class="wrapper wrapper-content">
            <div class="row animated fadeInRight">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="" id="ibox-content">
                            <div id="vertical-timeline" class="vertical-container light-timeline">
                                <div class="vertical-timeline-block">
                                    <div class="vertical-timeline-icon navy-bg">
                                        <i class="fa fa-briefcase"></i>
                                    </div>
                                    <div class="vertical-timeline-content">
                                        <h2>公告</h2>
                                        <p>公告功能即将上线，尽情期待~~~。 </p>
                                       <%-- <a href="#" class="btn btn-sm btn-primary"> 更多信息</a>--%>
                                        <span class="vertical-date">
                                        <%--今天 <br>--%>
                                        <small>2017年11月05日</small>
                                    </span>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="js/content.min.js?v=1.0.0"></script>
<!-- 浏览器下载js脚本文件时候，不会启动其他下载任务，放在底部有助于页面加载速度 -->
<script type="text/javascript" src="/js/require.js"></script>
<!-- defre js的加载不会阻塞页面的渲染和资源的加载 -->
<script type="text/javascript" src="/js/main.js"></script>

<script type="text/javascript">
    /*js的amd写法*/
    require(['jquery'], function ($) {
        require(['bootstrap'], function () {
            require(['base'], function () {
                $(function () {
                    Pace.start({});
                });
            });
        });
    });
</script>
</body>

</html>
