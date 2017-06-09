<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统内部错误</title>

</head>
<body>
<%= request.getAttribute("exception") %>
</body>
</html>
