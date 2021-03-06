<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <title>郑州佳辰景行文化传播管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- CSS -->
    <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
    <link rel="stylesheet" href="login/assets/css/reset.css">
    <link rel="stylesheet" href="login/assets/css/supersized.css">
    <link rel="stylesheet" href="login/assets/css/style.css">
    <style>
        .footer{
            height: 10px;
            width: 100%;
            position: fixed;
            bottom: 0;
        }
    </style>

</head>
<body>
<div class="page-container">
    <h1></h1>
    <form action="/login" method="post">
        <div style="color:red">${error}</div>
        <div>佳辰景行文化传播</div>
        <input type="text" name="userName" class="username" placeholder="用户名">
        <input type="password" name="password" class="password" placeholder="密码">
        <button type="submit">登录</button>
        <div class="error"><span>+</span></div>
    </form>
    <div class="footer">
        <a href="http://www.miitbeian.gov.cn">豫ICP备17027010号</a>
    </div>
</div>
<!-- Javascript -->
<script src="login/assets/js/jquery-1.8.2.min.js"></script>
<script src="login/assets/js/supersized.3.2.7.min.js"></script>
<script src="login/assets/js/supersized-init.js"></script>
<script src="login/assets/js/scripts.js"></script>
</body>
</html>

