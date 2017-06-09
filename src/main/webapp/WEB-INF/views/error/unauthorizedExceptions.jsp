<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE HTML>
<html zh-CN>
<head>
<meta charset="utf-8">
<title>权限不足面</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<link rel="Bookmark" href="${path}/image/favicon.ico">
<link rel="Shortcut Icon" href="${path}/image/favicon.ico">

<!--[if lt IE 8]>
    <script>
        alert('不支持IE6-8，请使用谷歌、火狐等浏览器\n或360、QQ等国产浏览器的极速模式浏览本页面！');
    </script>
    <![endif]-->
<link type="text/css" rel="stylesheet"
	href="${path}/css/bootstrap-3.3.5/css/bootstrap.min.css">
<!--字体  -->
<link type="text/css"
	href="${path}/css/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<!--动画库 animated bounceOutUp shake  -->
<link type="text/css"
	href="${path}/css/plugins/animate/animate.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="jumbotron animated shake">
			<h1>权限不足!</h1>
			<p>请切换角色</p>
			<p>
				<a class="btn btn-primary btn-lg" href="javaScript:history.go(-1)"
					role="button">返回</a>
			</p>
		</div>
	</div>
</body>
<script type="text/javascript">
if (self != top) {  
    alert('在iframe中');
}
</script>
</html>