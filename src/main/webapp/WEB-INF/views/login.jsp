<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html lang="en" class="no-js">
<head>
    <title>郑州佳辰景行文化传播管理系统</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- CSS -->
    <link rel="stylesheet" href="login2/css/login.css">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
<div class="container demo-1">
    <div class="content">
        <div id="large-header" class="large-header" style="height: 667px;">
            <canvas id="demo-canvas" width="375" height="667"></canvas>
            <div class="logo_box">
                <h3>欢迎你</h3>
                <div style="color:red">${error}</div>
                <form action="/login" id="form1" method="post">
                    <div class="input_outer">
                        <span class="u_user"></span>
                        <input name="userName" class="text" style="color: #FFFFFF !important" type="text"
                               placeholder="请输入账户" autocomplete="off">
                    </div>
                    <input style="display:none">
                    <div class="input_outer">
                        <span class="us_uer"></span>
                        <input name="password" autocomplete="off" class="text"
                               style="color: #FFFFFF !important; position:absolute; z-index:100;" value=""
                               type="password" placeholder="请输入密码">
                    </div>
                    <div class="mb2"><a class="act-but submit" onclick="document.getElementById('form1').submit(); "
                                        style="color: #FFFFFF">登录</a></div>
                </form>
            </div>
        </div>
    </div>
</div><!-- /container -->
<script src="login2/js/login.js"></script>
</body>
</html>