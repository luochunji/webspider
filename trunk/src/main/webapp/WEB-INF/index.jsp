<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8"/>
	<title>后台首页</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles/style.css" type="text/css"/>
	<script src="<%=request.getContextPath()%>/js/scripts/libs/modernizr/modernizr.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/scripts/config.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-1.7.2.js" type="text/javascript" ></script>
    <script>
        $(document).ready(function() {
            var main_width = document.body.clientWidth - $("#sidebar").width();
            var main_height = $(document).height() - $("#secondary_bar").height();
            $("#content").attr("width",main_width);
            $("#content").attr("height",main_height);
            $("#content").attr("src",'<%=request.getContextPath()%>/product/showResult');
        });
        function turnPage(url){
            $("#content").attr("src",'<%=request.getContextPath()%>'+url);
        }
    </script>
</head>
<body>
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
		<%--<h3>用户</h3>
		<ul class="toggle">
			<li><font class="ficomoon icon-users"></font><a href="#">会员管理</a></li>
			<li><font class="ficomoon icon-user-module"></font><a href="#">会员模块设置</a></li>
			<li><font class="ficomoon icon-user-group"></font><a href="#">会员组管理</a></li>
		</ul>
		<h3>设置</h3>
		<ul class="toggle">
			<li><font class="ficomoon icon-admin"></font><a href="#">管理员设置</a>
			<menu class="children dn">
				<dl>
					<dd><a href="#">添加管理员</a></dd>
				</dl>
			</menu>
			</li>
			<li><font class="ficomoon icon-admin-role"></font><a href="#">角色管理</a>
			<menu class="children dn">
				<dl>
					<dd><a href="#">添加角色</a></dd>
				</dl>
			</menu>
			</li>
			<li><font class="ficomoon icon-browser"></font><a href="#">静态化管理</a>
			<menu class="children dn">
				<dl>
					<dd><a href="#">生成首页</a></dd>
				</dl>
			</menu>
			</li>
		</ul>
		<h3>扩展</h3>
		<ul class="toggle">
			<li><font class="ficomoon icon-menu"></font><a href="extend_menu.html">菜单管理</a>
			<menu class="children dn">
				<dl>
					<dd><a href="extend_menu_add.html">添加菜单</a></dd>
				</dl>
			</menu>
			</li>
			<li><font class="ficomoon icon-log"></font><a href="#">操作日志</a></li>
			<li><font class="ficomoon icon-word"></font><a href="#">敏感词管理</a></li>
		</ul>--%>
		<footer>
			<hr />
			<p><strong>Copyright &copy; 2014 Pwstrick</strong></p>
		</footer>
	</aside><!-- end of sidebar -->
	<section id="main" class="column main_shadow">
		<iframe id="content" src="">

		</iframe>
	</section>
	<script src="<%=request.getContextPath()%>/js/scripts/libs/require/require.js" type="text/javascript" data-main="scripts/app/index/main"></script>
</body>
</html>