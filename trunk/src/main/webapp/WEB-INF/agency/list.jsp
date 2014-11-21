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
    <script type="text/javascript" language="JavaScript">
        function reSubmit(objForm) {
            objForm.submit();

        }
        $(function () {
            $("#addStoreInfo").click(function () {
                var str = '';
                str+='<tr class="store">';
                str+='<td align="right">网店名称:</td>';
                str+='<td class="inwrap">';
                str+='<input type="text" name="storeName" class="form-control" placeholder="请输入分销商网店名称"/>';
                str+='</td>';
                str+='<td align="right">网址:</td>';
                str+='<td class="inwrap">';
                str+='<input type="text" name="storeUrl" class="form-control" placeholder="请输入分销商网店网址"/>';
                str+='</td>';
                str+='<td onclick="getDel(this)"><a href="#">-</a></td>';
                str+='</tr>';
                $("#storeInfoTr").append(str);
            });
        });
        function getStoreInfo(objForm) {
            var storeInfo = [];
            $(".store").each(function(){
                var json = {};
                json.storeName = $(this).find("[name='storeName']").val();
                json.storeUrl = $(this).find("[name='storeUrl']").val();
                storeInfo.push(json);
            })
            $("#storeInfo").val(JSON.stringify(storeInfo));
            reSubmit(objForm);
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
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal2">
                                修改
                            </button>
                            <button type="button" class="btn btn-default">删除</button>
                        </div>
                        <!--end keybox-->
                        <div class="col-xs-6">
                            <div class="searchbox">
                                <div class="input-group">
                                    <input type="text" name="keyword" class="form-control " placeholder="请输入关键字"
                                           id="activityKeyw">
                                    <span class="input-group-btn">
                                        <button class="btn btn-primary" type="button" role="button"
                                                aria-disabled="false" onclick="javascript:reSubmit(this.form);"><span
                                                class="ui-button-text">搜索</span></button>
                                    </span>
                                    <button class="btn btn-primary">导出</button>
                                </div>
                            </div>
                        </div>
                        <!--end searchbox-->
                    </div>
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
                                                    <a href="<c:out value="${store.storeUrl}"/>">店铺链接</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav>
                        <ul class="pagination">
                            <li><a href="#">&laquo;</a></li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">&raquo;</a></li>
                        </ul>
                        <div class="pull-right"><span>共21页</span><span>每页10条记录</span></div>
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
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog FXdialog">
        <form action="<%=request.getContextPath()%>/agency/addAgency" id="agencyForm" method="post">
            <input name="storeInfo" id="storeInfo" type="hidden">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">分销商网店信息录入</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%" id="storeInfoTr">
                        <tr>
                            <td align="right">分销商用户：</td>
                            <td><input type="text" class="form-control" name="userName"
                                       placeholder="请输入景区名称" required></td>
                            <td><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td align="right">分销商名称:</td>
                            <td><input type="text" class="form-control" name="name" placeholder="请输入分销平台分销商名称"/></td>
                            <td><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td align="right">平台名称:</td>
                            <td colspan="2">
                                <select name="platFormId" class="form-control">
                                    <option value ="" selected>请选择</option>
                                    <c:forEach items="${pfMap}" var ="pf">
                                        <option value ="${pf.key}">${pf.value.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><font color="red">*</font></td>
                        </tr>
                        <tr class="store">
                            <td align="right">网店名称:</td>
                            <td class="inwrap">
                                <input type="text" name="storeName" class="form-control" placeholder="请输入分销商网店名称"/>
                            </td>
                            <td align="right">网址:</td>
                            <td class="inwrap">
                                <input type="text" name="storeUrl" class="form-control" placeholder="请输入分销商网店网址"/>
                            </td>
                            <td><a href="#" id="addStoreInfo">+</a></td>
                            <td><font color="red">*</font></td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="javascript:getStoreInfo(this.form)">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
            <!--end modal-content-->
        </form>

    </div>
</div>
<!--end add-->
<!--修改-->
<!-- Modal -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog FXdialog">
        <form>
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">分销商网店信息录入</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%">
                        <tr>
                            <td align="right">分销商用户：</td>
                            <td><input type="text" class="form-control"
                                       placeholder="请输入景区名称" required></td>
                            <td><font>*</font></td>
                        </tr>
                        <tr>
                            <td align="right">分销商名称:</td>
                            <td><input type="text" class="form-control" placeholder="请输入分销平台分销商名称"/></td>
                            <td><font>*</font></td>
                        </tr>
                        <tr>
                            <td align="right">平台名称:</td>
                            <td colspan="2"><input type="text" class="form-control" placeholder="请输入分销商网店平台名称"/></td>
                        </tr>
                        <tr>
                            <td align="right">网店信息:</td>
                            <td class="inwrap"><input type="text" class="form-control" placeholder="请输入分销商网店名称"/><input
                                    type="text" class="form-control" placeholder="请输入分销商网店网址"/></td>
                            <td><font>*</font></td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="javascript:sureSubmit(this.form);">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
            <!--end modal-content-->
        </form>

    </div>
</div>
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

</body>
</html>
