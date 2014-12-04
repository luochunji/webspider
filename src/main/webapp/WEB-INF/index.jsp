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
//            var main_height = document.body.clientHeight - 50 - 45;
            $("#content").attr("width",main_width);
            $("#content").attr("height",900);
            $("#content").attr("src",'<%=request.getContextPath()%>/product/showResult');

            $('.sidebar-menu li').click(function(){
                $(this).addClass('active').siblings('li').removeClass('active');
            })
        });
        function turnPage(url,name){
            url = '<%=request.getContextPath()%>'+url;
            $("#content").attr("src",url);
            $("#navBar").attr("href",url);
            $("#navBar").find("span").text(name);
        }
    </script>
</head>
<body class="skin-blue home">
    <header class="header">
        <div class="user-panel logo">
            <div class="pull-left image">
            </div>
            <div class="pull-left info">
                <p>欢迎您,XXXX</p>
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
                        <a href="javascript:turnPage('/product/showResult','网警首页');">
                            <span>网警首页</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/task/list','常规任务列表');">
                            <span>常规任务列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/task/temp/list','临时任务列表');">
                            <span>临时任务列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/product/showTempResult','临时查询结果');">
                            <span>临时查询结果</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/agency/list','分销网店列表');">
                            <span>分销网店列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:turnPage('/product/showHistory','历史搜索数据');">
                            <span>历史搜索数据</span>
                        </a>
                    </li>
                </ul>
            </section>
            <!-- /.sidebar -->
        </aside>
        <aside class="right-side">
            <section class="content-header">
                <ol class="breadcrumb">
                    <li><a href="#" id="navBar"><i class="fa fa-dashboard"></i><span>网警首页</span></a></li>
                </ol>
            </section>
            <iframe id="content" src="" >

            </iframe>
        </aside>
        </div>
</body>
</html>