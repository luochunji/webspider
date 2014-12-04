<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>分销商列表</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/ionicons.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/plugins/daterangepicker/bootstrap-datetimepicker.min.js"
            type="text/javascript" charset="UTF-8"></script>
    <script src="<%=request.getContextPath()%>/js/plugins/daterangepicker/bootstrap-datetimepicker.zh-CN.js"
            type="text/javascript" charset="UTF-8"></script>
    <script src="<%=request.getContextPath()%>/js/app.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" language="JavaScript">
//        function reSubmit(objForm) {
//            objForm.submit();
//
//        }
        function modifyCheck() {
            var size = $("input[name='ids']:checked").length;
            if (size == 0) {
                layer.alert("请选择一个修改的景区任务！");
                return false;
            } else if (size > 1) {
                layer.alert("只能选择一个景区任务进行修改！");
                return false;
            } else {
                var id = $("input[name='ids']:checked").val();
                var url = '<%=request.getContextPath()%>/agency/modifyAgencyUI?id='+id;
                $.ajax( {
                    type : "POST",
                    url : url,
                    dataType: "json",
                    success : function(data) {
                        if('success' == data.result){
                            buildUI(data);
                        }
                        $('#myModal2').modal({
                            backdrop:true,
                            keyboard:true,
                            show:true
                        });
                    },
                    error :function(){
                        layer.alert("网络连接出错！");
                    }
                });
            }
            return;
        }
        function delCheck(objForm) {
            var size = $("input[name='ids']:checked").length;
            if (size == 0) {
                layer.alert("请至少选择一个要删除的分销商！");
                return;
            } else {
                $.layer({
                    shade: [0],
                    area: ['auto','auto'],
                    dialog: {
                        msg: '你确定要删除选中的分销商连同网店数据一起删除吗？',
                        btns: 2,
                        type: 9,
                        btn: ['确定','取消'],
                        yes: function(){
                            var action = '<%=request.getContextPath()%>/agency/delAgency';
                            objForm.action = action;
                            reSubmit(objForm);
                        }, no: function(){
                            return false
                        }
                    }
                });
            }
        }
        function topage(objForm,page) {
            if(typeof(objForm)=="string"){
                objForm = $("#"+objForm);
            }
            $("#page").val(page);
            reSubmit(objForm);
        }
        function exportExcel(){
            var ids = '';
            var arrChk=$("input[name='ids'][checked]");
            $(arrChk).each(function(){
                ids +=this.value+',';
            });
            $("#ids").val(ids);
            $("#keyword").val($("#activityKeyw").val());
            $("#exportExcel").submit();
        }
    </script>
</head>
<body class="skin-blue tasklist">
<form action="<%=request.getContextPath()%>/agency/list" id="agencyForm" method="post">
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <div class="wrapper row-offcanvas row-offcanvas-left">
        <!-- Main content -->
        <section class="content">
            <div class="box box-primary">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-6 pull-left">
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal1">
                                新增
                            </button>
                            <button type="button" class="btn btn-default" data-toggle="modal" onclick="javascript:modifyCheck();">
                                修改
                            </button>
                            <button type="button" class="btn btn-default" onclick="javascript:delCheck(this.form);">删除</button>
                        </div>
                    </div>
                    <!--end keybox-->
                    <div class="row">
                        <div class="col-xs-12 searchbox">
                            <span>关键字:</span>
                            <input type="text" name="keyword" class="form-control keyinput" placeholder="请输入关键字"  id="activityKeyw" value="${bean.keyword}">
                            <button class="btn btn-primary" onclick="javascript:topage(this.form,1);">搜索</button>
                            <input type="button" class="btn btn-default" value="导出" onclick="javascript:exportExcel();"/>
                        </div>
                    </div>
                    <!--end searchbox-->
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th><input type="checkbox" id="chk_all"/>全选</th>
                            <th>序号</th>
                            <th>分销商用户名</th>
                            <th>分销商名称</th>
                            <th>网店名称</th>
                            <th>网店网址</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageView.records}" var="agency" varStatus="index">
                            <tr>
                                <td><input type="checkbox" name="ids" value="${agency.id}"></td>
                                <td><c:out value="${index.count}"/></td>
                                <td><c:out value="${agency.userName}"/></td>
                                <td><c:out value="${agency.name}"/></td>
                                <td>
                                    <table>
                                        <c:forEach items="${agency.agencyStores}" var="store">
                                            <tr>
                                                <td><c:out value="${store.storeName}"/></td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <c:forEach items="${agency.agencyStores}" var="store">
                                            <tr>
                                                <td>
                                                    <a href="<c:out value="${store.storeUrl}"/>" target="_blank">店铺链接</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav class="clearfix">
                        <ul class="pagination">
                            <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                                <c:if test="${pageView.currentpage==wp}"><li class="active"><a href="#">${wp}</a></li></c:if>
                                <c:if test="${pageView.currentpage!=wp}"><li><a href="#" onclick="javaScript:topage('agencyForm',${wp});">${wp}</a></li></c:if>
                            </c:forEach>
                        </ul>
                        <%@ include file="/WEB-INF/common/fenye.jsp" %>
                    </nav>
                    <!--end pagination-->
                </div>
                <!--end box-body-->
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- ./wrapper -->
</form>
<!--新增-->
<!-- Modal -->
<%@ include file="/WEB-INF/agency/add.jsp" %>
<!--end add-->
<!--修改-->
<!-- Modal -->
<%@ include file="/WEB-INF/agency/modify.jsp" %>

<%--<form action="<%=request.getContextPath()%>/agency/list" id="agencyForm" method="post">
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td style="color: #FFFFFF;">
                <div>分销商列表</div>
            </td>
        </tr>
        <tr>
            <td>
                <div style="float: left;margin-right: 10px;"><a href="<%=request.getContextPath()%>/agency/addAgencyUI">新增</a></div>
                <div style="float: left;margin-right: 10px;"><a href="javascript:modifyCheck();">修改</a></div>
                <div style="float: left;margin-right: 10px;"><a href="javascript:delCheck();">删除</a></div>
                <div align="right" style="margin-right: 20px">关键字 ：<input type="text" maxlength="20" size="20" name="keyword"/>
                    <select name = "condition" lang="20">
                        <option value="" selected>请选择查询类别</option>
                        <option value="agencyId">分销商编号</option>
                        <option value="username">用户名</option>
                        <option value="name">全称</option>
                        <option value="storeName">网店名称</option>
                    </select>
                    <input type="submit"  value="搜索">
                </div>
            </td>
        </tr>
    </table>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td style="color: #FFFFFF;"><input type="checkbox" id="chk_all"/>全选
            </td>
            <td style="color: #FFFFFF;">序号</td>
            <td style="color: #FFFFFF;">用户名</td>
            <td style="color: #FFFFFF;">全称</td>
            <td style="color: #FFFFFF;">所属平台</td>
            <td style="color: #FFFFFF;">网店名称</td>
            <td style="color: #FFFFFF;">网店地址</td>
            <td style="color: #FFFFFF;">创建人</td>
            <td style="color: #FFFFFF;">创建时间</td>
            <td style="color: #FFFFFF;">当前状态</td>
            <td style="color: #FFFFFF;">操作</td>
        </tr>
        <c:forEach items="${pageView.records}" var="agency" varStatus="index">
            <tr bgcolor="f5f5f5" id="<c:out value='${agency.id}'/>">
                <td><input type="checkbox" name="ids" value="${agency.id}"></td>
                <td><c:out value="${index.count}"/></td>
                <td><c:out value="${agency.userName}"/></td>
                <td><c:out value="${agency.name}"/></td>
                <td><c:out value="${pfMap[agency.platFormId].name}"/></td>
                <td>
                    <table width="100%" bgcolor="white">
                        <c:forEach items="${agency.agencyStores}" var="store">
                            <tr bgcolor="f5f5f5">
                                <td><c:out value="${store.storeName}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td>
                    <table width="100%" bgcolor="white">
                        <c:forEach items="${agency.agencyStores}" var="store">
                            <tr bgcolor="f5f5f5">
                                <td>
                                    <a href="<c:out value="${store.storeUrl}"/>">店铺链接</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td><c:out value="${agency.creator}"/></td>
                <td><fmt:formatDate value="${agency.createTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></td>
                <td><c:out value="${agency.status}"/></td>
                <td>
                    <c:if test="${task.taskScheduler.taskStatus == 'RUNNING'}">
                        <input type="button" onclick="ActionEvent('pause','<c:out value="${task.taskScheduler.id}"/>')" value="暂停"/>
                    </c:if>
                    <c:if test="${task.taskScheduler.taskStatus == 'PAUSE'}">
                        <input type="button" onclick="ActionEvent('resume','<c:out value="${task.taskScheduler.id}"/>')" value="恢复"/>
                    </c:if>
                    <input type="button" onclick="ActionEvent('remove','<c:out value="${task.taskScheduler.id}"/>')" value="删除"/>
                </td>
            </tr>
        </c:forEach>
        <!--LOOP END-->
        <tr>
            <td>
                <div style="float: left;">
                    <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                        <c:if test="${pageView.currentpage==wp}"><div class='red' style="float:left">${wp}</div></c:if>
                        <c:if test="${pageView.currentpage!=wp}"><div class="page" style="float:left"><a href="<%=request.getContextPath()%>/agency/list?page=${wp}">[${wp}]</a></div></c:if>
                    </c:forEach>
                    <div style="float:left;">&nbsp;&nbsp;</div>跳转到第
                    <select name="selectPage" class="kuang" onChange="javaScript:topage(this.value)">
                        <c:forEach begin="1" end="${pageView.totalpage}" var="wp">
                            <option value="${wp}" <c:if test="${pageView.currentpage==wp}">selected</c:if>> ${wp} </option></c:forEach>
                    </select>页
                </div>
            </td>
            <td colspan="10">
                <%@ include file="/WEB-INF/common/fenye.jsp" %>
            </td>
        </tr>
    </table>
</form>--%>
<div style="display: none">
    <form id="exportExcel" action="<%=request.getContextPath()%>/agency/exportExcel" method="post" target="_blank">
        <input type="hidden" name="ids" id="ids">
        <input type="hidden" name="keyword" id="keyword">
    </form>
</div>
</body>
</html>
