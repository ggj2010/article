<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html zh-CN>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <link rel="Bookmark" href="/image/favicon.ico">
    <link rel="Shortcut Icon" href="/image/favicon.ico">

    <!--[if lt IE 8]>
    <script>
        alert('不支持IE6-8，请使用谷歌、火狐等浏览器\n或360、QQ等国产浏览器的极速模式浏览本页面！');
    </script>
    <![endif]-->

    <!-- rtlbootstrap -->

    <link type="text/css" rel="stylesheet"
          href="/css/bootstrap-3.3.5/css/bootstrap.min.css">
    <!--字体  -->
    <link type="text/css"
          href="/css/font-awesome/css/font-awesome.css"
          rel="stylesheet">
    <!--动画库 animated bounceOutUp shake  -->
    <link type="text/css"
          href="/css/plugins/animate/animate.min.css"
          rel="stylesheet">

    <!-- layer弹出框css -->
    <link type="text/css"
          href="/js/lib/plugins/layer/skin/layer.css"
          rel="stylesheet">
    <!-- sweetalert -->
    <link type="text/css"
          href="/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">

    <!-- pace css/pace-theme-flash.css/pace-theme-corner-indicator.css/pace-theme-big-counter.css/pace-theme-minimal.css -->
    <link type="text/css"
          href="/css/plugins/pace/themes/pace-theme-flash.css"
          rel="stylesheet">

    <link type="text/css" href="/css/style.css"
          rel="stylesheet">


</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle"
                                       src="/image/profile_small.jpg"/></span> <a
                            data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
                            class="clear"> <span class="block m-t-xs"><strong
                            class="font-bold">欢迎您，${userName}</strong></span>
							</span>
                    </a>
                    </div>
                    <div class="logo-element">RCJH
                    </div>
                </li>
        <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
            <c:if test="${menu.parentId eq '1'&&menu.isShow eq '0'}">
                <li>
                    <a href="">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">${menu.name}</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                    <c:forEach items="${menuList}" var="childMenu" >
                        <c:if test="${childMenu.parentId==menu.id&&childMenu.isShow eq '0'}">
                            <li><a class="J_menuItem" href="${path}${childMenu.href}">${childMenu.name}</a></li>
                        </c:if>
                    </c:forEach>
                    </ul>
                </li>
            </c:if>
        </c:forEach>
              <%--  <li>
                    <a href="">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">系统设置</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li><a class="J_menuItem" href="${path}/sys/dict">数据字典</a></li>
                        <li><a class="J_menuItem" href="${path}/sys/menu">菜单管理</a></li>
                        <li><a class="J_menuItem" href="${path}/sys/role">角色管理</a></li>
                        <li><a class="J_menuItem" href="${path}/userInfo">员工管理</a></li>
                        <li><a class="J_menuItem" href="${path}/customInfo/">客户管理</a></li>
                        <li><a class="J_menuItem" href="${path}/customInfo/user">员工客户管理</a></li>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">媒体</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" href="${path}/media">媒体管理</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/article">稿件管理</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/article/add">在线发稿</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/media/editor/list">我的媒体</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">财务</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" href="${path}/settle/custom?bussinnessType=2">客户结算</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/settle/custom?bussinnessType=1">结算</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/settle/user?bussinnessType=1">员工结算</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/settle/user?bussinnessType=2">系统员工结算</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/settle/editor?bussinnessType=1">编辑结算</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${path}/settle/editor?bussinnessType=2">系统编辑结算</a>
                        </li>
                    </ul>
                </li>--%>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i
                        class="fa fa-bars"></i> </a>
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="${path}/media" class="active J_menuTab" data-id="index_v1.html">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${path}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" seamless src="${path}/media"></iframe>
        </div>
        <div class="footer">
            <div class="pull-right"><strong>Copyright</strong> 私人定制 © 2017
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
        <div class="sidebar-container">
            <ul class="nav nav-tabs navs-3">
                <li class="active">
                    <a data-toggle="tab" href="tab-1">
                        <i class="fa fa-gear"></i> 主题
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="sidebar-title">
                        <h3><i class="fa fa-comments-o"></i> 主题设置</h3>
                        <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                    </div>
                    <div class="skin-setttings">
                        <div class="title">主题设置</div>
                        <div class="setings-item">
                            <span>收起左侧菜单</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox"
                                           id="collapsemenu">
                                    <label class="onoffswitch-label" for="collapsemenu">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定顶部</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox"
                                           id="fixednavbar">
                                    <label class="onoffswitch-label" for="fixednavbar">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                                <span>固定宽度</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox"
                                           id="boxedlayout">
                                    <label class="onoffswitch-label" for="boxedlayout">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="" class="s-skin-0">
                           	  默认皮肤
                         </a>
                    </span>
                        </div>
                        <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="" class="s-skin-1">
                          	  蓝色主题
                        </a>
                    </span>
                        </div>
                        <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="" class="s-skin-3">
                           	 黄色/紫色主题
                        </a>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--右侧边栏结束-->
</body>


<!-- 浏览器下载js脚本文件时候，不会启动其他下载任务，放在底部有助于页面加载速度 -->
<script type="text/javascript" src="/js/require.js"></script>
<!-- defre js的加载不会阻塞页面的渲染和资源的加载 -->
<script type="text/javascript" src="/js/main.js" defer
        async="true"></script>

<script type="text/javascript">
    /*js的amd写法*/
    require(['jquery'], function ($) {
        require(['bootstrap', 'sweetalert', 'contabs', 'metisMenus', 'layer', 'Pace', 'slimscroll'], function () {
            require(['base'], function () {
                $(function () {
                    Pace.start({});
                });
            });
        });
    });
</script>
</html>