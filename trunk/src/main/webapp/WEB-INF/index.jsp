<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8"/>
	<title>后台首页</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
    <script src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/app.js" type="text/javascript"></script>
    <script>
        $(document).ready(function() {
            var main_width = document.body.clientWidth - 220;
            var main_height = $(document).height() - 50;
            $("#content").attr("width",main_width);
            $("#content").attr("height",main_height);
            $("#content").attr("src",'<%=request.getContextPath()%>/product/showResult');

            $('.sidebar-menu li').click(function(){
                $(this).addClass('active').siblings('li').removeClass('active');
            })
        });
        function turnPage(url){
//            alert($(k).parent());
            $("#content").attr("src",'<%=request.getContextPath()%>'+url);
        }
    </script>
</head>
<body class="skin-blue home">
    <header class="header">
        <div class="user-panel logo">
            <div class="pull-left image">
            </div>
            <div class="pull-left info">
                <p>欢迎您, 陈玲</p>
            </div>
        </div>
        <nav class="navbar navbar-static-top" role="navigation">
            <a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
        </nav>
    </header>
    <div class="wrapper row-offcanvas row-offcanvas-left">
        <aside class="left-side sidebar-offcanvas">
            <section class="sidebar">
                <ul class="sidebar-menu">
                    <li>
                        <a href="javascript:turnPage('/product/showResult');">
                            <span>网警首页</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/task/list');">
                            <span>常规任务列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/task/temp/list');">
                            <span>临时任务列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/product/showTempResult');">
                            <span>临时查询结果</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/agency/list');">
                            <span>分销网店列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/product/showHistory');">
                            <span>历史搜索数据</span>
                        </a>
                    </li>
                </ul>
            </section>
            <!-- /.sidebar -->
        </aside>
        <aside class="right-side">
            <iframe id="content" src="">

            </iframe>
        </aside>
        </div>

<%--
	<header id="header">
		<hgroup>
			<h1 class="site_title"><a href="index.html">Website Admin</a></h1>
			<h2 class="section_title">Dashboard</h2><div class="btn_view_site"><a href="http://www.cnblogs.com/strick/">View Site</a></div>
		</hgroup>
	</header> <!-- end of header bar -->
	<section id="secondary_bar">
		<div class="user">
			<p>Pwstrick (<a href="#">3 Messages</a>)</p>
			<!-- <a class="logout_user" href="#" title="Logout">Logout</a> -->
		</div>
		<div class="breadcrumbs_container">
			<article class="breadcrumbs"><a href="index.html">后台首页</a><div class="breadcrumb_divider"></div><a class="current">首页信息</a></article>
		</div>
	</section><!-- end of secondary bar -->
	<aside id="sidebar" class="column">
		<form class="quick_search">
			<input type="text" value="Quick Search" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
		</form>
		<hr/>
		<h3>我的面板</h3>
		<ul class="toggle">
			<li><font class="ficomoon icon-profile"></font><a href="#">修改个人信息</a></li>
			<li><font class="ficomoon icon-pwd"></font><a href="#">修改密码</a></li>
			<li><font class="ficomoon icon-logout"></font><a href="#">退出</a></li>
		</ul>
		<h3>模块</h3>
		<ul class="toggle">
			<li><font class="ficomoon icon-bbs"></font><a href="javascript:turnPage('/product/showResult')">网警首页</a></li>
			<li><font class="ficomoon icon-circle"></font><a href="javascript:turnPage('/task/list')">景区任务列表</a></li>
			<li><font class="ficomoon icon-question"></font><a href="javascript:turnPage('/task/temp/list')">临时任务列表</a></li>
            <li><font class="ficomoon icon-question"></font><a href="javascript:turnPage('/product/showTempResult')">临时任务结果</a></li>
			<li><font class="ficomoon icon-private-msg"></font><a href="javascript:turnPage('/agency/list')">分销商列表</a></li>
			<li><font class="ficomoon icon-comment"></font><a href="javascript:turnPage('/product/showHistory')">历史数据</a></li>
		</ul>
		<footer>
			<hr />
			<p><strong>Copyright &copy; 2014 Pwstrick</strong></p>
		</footer>
	</aside><!-- end of sidebar -->
	<section id="main" class="column main_shadow">
		<iframe id="content" src="">

		</iframe>
	</section>
	<script src="<%=request.getContextPath()%>/js/scripts/libs/require/require.js" type="text/javascript" data-main="scripts/app/index/main"></script>--%>
</body>
</html>