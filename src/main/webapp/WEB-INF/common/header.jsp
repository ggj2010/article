<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 如果$el表达式不管用加这个 %@ page isELIgnored="false" % -->
<% 
	request.setAttribute("path",request.getContextPath());
%>
<!DOCTYPE HTML>
<!-- Bootstrap 是移动设备优先的 ,确保适当的绘制和触屏缩放。user-scalable=no是禁止屏幕缩放的 -->
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!--表示强制使用IE最新内核-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<!-- 国产浏览器告诉模式 -->
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<!--Bookmark是用来显示收藏夹本网页的图标的  -->
<link rel="Bookmark" href="static/image/favicon.ico">
<!--Bookmark是用来显示浏览器导航栏本网页的图标的  -->
<link rel="Shortcut Icon" href="static/image/favicon.ico">

<!-- 把CSS放在头部，可以让页面根据CSS样式逐步渲染-->
<link type="text/css"
	href="${path}/css/bootstrap-3.3.5/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jquyer通知插件 -->	
<link href="${path}/css/plugins/toastr/toastr.min.css" rel="stylesheet">	
	
<!-- 下拉框选择器 -->	
<link href="${path}/css/plugins/chosen/chosen.css" rel="stylesheet">
<!-- metisMenu 菜单样式 -->	
<link href="${path}/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">

<!-- switchery 切换开关 http://abpetkov.github.io/switchery -->	
<link href="${path}/css/plugins/switchery/switchery.css" rel="stylesheet">
<!-- sweetalert 弹出框-->
<link href="${path}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

<!--webuploader 上传文件前端框架http://fex.baidu.com/webuploader/-->
<link href="${path}/css/plugins/webuploader/webuploader.css" rel="stylesheet">


<!-- layer弹出框css -->
<link type="text/css"
	href="${path}/js/lib/plugins/layer/skin/layer.css"
	rel="stylesheet">

<!-- treetable样式-->	
<link href="${path}/css/plugins/treetable/jquery.treetable.css" rel="stylesheet">
<link href="${path}/css/plugins/treetable/jquery.treetable.theme.default.css" rel="stylesheet"> 

<link  href="${path}/css/plugins/jquerytress/zTreeStyle.css" rel="stylesheet">
<link  href="${path}/css/plugins/date/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link  href="${path}/css/basestyle.css" rel="stylesheet">

<script type="text/javascript">
	var path = "${path}"
</script>
<!-- 浏览器下载js脚本文件时候，不会启动其他下载任务，放在底部有助于页面加载速度 -->
<script type="text/javascript" src="${path}/js/require.js"></script>
<!-- defre js的加载不会阻塞页面的渲染和资源的加载 -->
<script type="text/javascript" src="${path}/js/main.js" defer
	async="true"></script>

