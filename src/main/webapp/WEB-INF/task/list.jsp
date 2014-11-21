<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>景区任务列表</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/ionicons.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/tipswindown.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/plugins/daterangepicker/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/plugins/daterangepicker/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/app.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/task.js"></script>
</head>
<script>
    $(document).ready(function () {
        $("input[name^='runtime']").live('focus',function() {
            $("input[name^='runtime']").datetimepicker({
                format: 'hh:ii:ss',
                language: 'zh-CN',
                weekStart: 1,
                todayBtn: 1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 1,
                minView: 0,
                maxView: 1,
                minuteStep:1,
                forceParse: 0
            })
        });
    });
    function reSubmit(objForm) {
        objForm.submit();

    }
    function topage(objForm,page) {
        $("#page").val(page);
        reSubmit(objForm);
    }
    function modifyCheck() {
        var size = $("input[name='ids']:checked").length;
        if (size == 0) {
            alert("请选择一个修改的景区任务！");
            return false;
        } else if (size > 1) {
            alert("只能选择一个景区任务进行修改！");
            return false;
        } else {
            var id = $("input[name='ids']:checked").val();
            var url = '<%=request.getContextPath()%>/task/modifyTaskUI?id='+id;
            $.ajax( {
                type : "POST",
                url : url,
                dataType: "json",
                success : function(data) {
                    if('success' == data.result){
                        $("#scenicName").val(data.scenic.scenicName);
                        $("#taskId").val(data.task.id);
                        $("#keyword").val(data.task.keyword);
                        $("#price").val(data.task.price);
                    }
                    $('#myModal2').modal({
                        backdrop:true,
                        keyboard:true,
                        show:true
                    });
                },
                error :function(){
                    alert("网络连接出错！");
                }
            });
        }
        return;
    }
    function delCheck(objForm) {
        var size = $("input[name='ids']:checked").length;
        if (size == 0) {
            alert("请至少选择一个要删除的任务！");
            return;
        } else {
            if (window.confirm('你确定要删除选中的任务吗？')) {
                var action = '<%=request.getContextPath()%>/task/delTask';
                $("#taskForm").attr("action", action);
                reSubmit(objForm);
            } else {
                return false;
            }
        }
    }
    function getCondition(objForm){
        var conditions = [];
        $(".condition").each(function(){
            var json = {};
            json.keyword = $(this).find("[name='keyword']").val();
            json.price = $(this).find("[name='price']").val();
            conditions.push(json);
        })
        $("#conditions").val(JSON.stringify(conditions));
        reSubmit(objForm);
    }
</script>
<body class="skin-blue tasklist">

<form action="<%=request.getContextPath()%>/task/list" id="taskForm" method="post">
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <input type="hidden" name="id" id="id"/>

    <div class="wrapper row-offcanvas row-offcanvas-left">
        <!-- Main content -->
        <section class="content">
            <div class="box box-primary">
                <div class="box-header setbox">
                    <div class="col-xs-3">
                        <span>搜索时间：</span>
                        <c:if test="${!empty runtimeMap}">
                            <c:forEach items="${runtimeMap}" var="rt">
                                <span>${rt.value.runtime}</span>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="col-xs-9">
                        <span>接收邮箱：</span>
                        <c:if test="${!empty bean.params['email']}">
                            <c:if test="${fn:length(bean.params['email'].paramValue)>25}">
                                <span title="${bean.emailTitle}">${fn:substring(bean.params['email'].paramValue, 0,25)}...</span>
                            </c:if>
                            <c:if test="${fn:length(bean.params['email'].paramValue)<=25}">
                                <span title="${bean.emailTitle}">${bean.params['email'].paramValue}</span>
                            </c:if>
                        </c:if>
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal">设置
                        </button>
                    </div>
                </div>
                <!--end box-header-->
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-6 pull-left">
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal1">
                                新增
                            </button>
                            <%--<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal2" onclick="javascript:modifyCheck();">--%>
                            <button type="button" class="btn btn-default" data-toggle="modal" onclick="javascript:modifyCheck();">
                                修改
                            </button>
                            <button type="button" class="btn btn-default" onclick="javascript:delCheck(this.form);">删除</button>
                        </div>
                        <!--end keybox-->
                        <div class="col-xs-6">
                            <div class="searchbox">
                                <div class="input-group">
                                    <input type="text" class="form-control " placeholder="请输入关键字" id="activityKeyw" name="keyword" value="${bean.keyword}">
                                    <span class="input-group-btn">
                                        <button class="btn btn-primary" type="button" role="button" onclick="javascript:topage(this.form,1);"
                                                aria-disabled="false"><span class="ui-button-text">搜索</span></button>
                                    </span>
                                    <button class="btn btn-primary" onclick="javascript:exportExcel();">导出</button>
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
                            <th>景区名称</th>
                            <th>关键字</th>
                            <th>价格</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageView.records}" var="dto" varStatus="index">
                            <tr>
                                <td><input type="checkbox" name="ids" value="${dto.task.id}"></td>
                                <td><c:out value="${index.count}"/></td>
                                <td><c:out value="${dto.scenic.scenicName}"/></td>
                                <td><c:out value="${dto.task.keyword}"/></td>
                                <td><c:out value="${dto.task.price}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav>
                        <ul class="pagination">
                            <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                                <c:if test="${pageView.currentpage==wp}"><li class="active"><a href="#" >${wp}</a></li></c:if>
                                <c:if test="${pageView.currentpage!=wp}"><li><a href="javascript:topage(this.form,${wp})">${wp}</a></li></c:if>
                            </c:forEach>
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
</form>
    <!-- ./wrapper -->
    <!--搜索设置-->
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
        <div class="modal-dialog">
            <form action="<%=request.getContextPath()%>/task/modifyParams" id="paramForm" method="post">
                <input type="hidden" name="paramKey" value="email">
                <input type="hidden" name="id"  value="${paramsMap['email'].id}">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">搜索设置</h4>
                    </div>
                    <div class="modal-body">
                        <table class="tables" width="100%" id="paramTr">
                            <c:if test="${empty runtimeMap}">
                                <tr>
                                    <td align="right">搜索时间：</td>
                                    <td><input type="text" name="runtime" class="form-control"
                                               placeholder="请选择发送时间..." required readonly></td>
                                    <td><a href="#" id="addSearchTime">+</a></td>
                                </tr>
                            </c:if>
                            <c:forEach items="${runtimeMap}" var="rt" varStatus="index">
                                <tr class="runtimeInput">
                                    <td align="right">搜索时间：</td>
                                    <td><input type="text" name="runtime" class="form-control"
                                               placeholder="请选择发送时间..." value="${rt.value.runtime}" required readonly></td>
                                    <c:if test="${index.count == 1}">
                                        <td><a href="#" id="addSearchTime">+</a></td>
                                    </c:if>
                                    <c:if test="${index.count >1}">
                                        <td onclick="getDel(this)"><a href="#">-</a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </table>
                        <table class="tables" width="100%">
                            <tr>
                                <td align="right">接收邮箱:</td>
                                <td><input type="text" class="form-control" name="paramValue" value="${paramsMap['email'].paramValue}" /></td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center" class="tips">
                                    邮箱可添加多个，请以“；”隔开
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onClick="javascript:reSubmit(this.form)">保存</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:location.reload();">关闭</button>
                    </div>
                </div>
                <!--end modal-content-->
            </form>

        </div>
    </div>
    <!--新增任务-->
    <!-- Modal -->
    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <form action="<%=request.getContextPath()%>/task/addTask" id="addTaskForm" method="post">
                <input type="hidden" name="conditions" id="conditions"/>
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">景区任务录入</h4>
                    </div>
                    <div class="modal-body">
                        <table class="tables" width="100%" id="taskTr">
                            <tr>
                                <td align="right">景区名称：</td>
                                <td colspan="2">
                                        <select name="scenicId" class="form-control">
                                            <option value ="" selected>请选择...</option>
                                            <c:forEach items="${scenicMap}" var ="s">
                                                <option value ="${s.key}">${s.value}</option>
                                            </c:forEach>
                                            <>
                                        </select>
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/scenic/addScenicUI">添加景区</a>
                                </td>
                            </tr>
                            <tr class="condition">
                                <td align="right">关键字:</td>
                                <td class="inwrap"><input name="keyword" type="text" class="form-control"/></td>
                                <td align="right">价格:</td>
                                <td class="inwrap"><input name="price" type="text" class="form-control price" value="00.00"/></td>
                                <td><a href="#" id="addTask">+</a></td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="javascript:getCondition(this.form)">保存</button>
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
        <div class="modal-dialog">
            <form action="<%=request.getContextPath()%>/task/modifyTask" id="modifyTaskForm" method="post">
                <input type="hidden" name="id" id="taskId">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">修改</h4>
                    </div>
                    <div class="modal-body">
                        <table class="tables" width="100%">
                            <tr>
                                <td align="right">景区名称：</td>
                                <td colspan="2">
                                    <input type="text" class="form-control" id="scenicName" disabled/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">关键字:</td>
                                <td class="inwrap">
                                    <input type="text" id="keyword" name="keyword" class="form-control" />
                                </td>
                                <td>
                                    <input type="text" id="price" name="price" class="form-control price" value="00.00"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onClick="javascript:reSubmit(this.form)">保存</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div>
                <!--end modal-content-->
            </form>

        </div>
    </div>
</body>

<%--<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
        <td style="color: #FFFFFF;">
            <div style="float: left;">运行参数：</div>
                <c:if test="${empty runtimeMap}">
                    <input type="button" id="setParams" value="设置"/>
                </c:if>
                <c:if test="${!empty runtimeMap}">
                    <c:forEach items="${runtimeMap}" var="rt">
                        <div style="float: left;margin-right: 10px">${rt.value.runtime}</div>
                    </c:forEach>
                </c:if>
        </td>
        <td style="color: #FFFFFF;">
            <div style="float: left;">邮件地址：</div>
            <c:if test="${!empty bean.params['email']}">
                    <div style="float: left;margin-right: 10px" title="${bean.emailTitle}">
                        <c:if test="${fn:length(bean.params['email'].paramValue)>25}">
                            ${fn:substring(bean.params['email'].paramValue, 0,25)}...
                        </c:if>
                        <c:if test="${fn:length(bean.params['email'].paramValue)<=25}">
                            ${bean.params['email'].paramValue}
                        </c:if>
                    </div>
            </c:if>
            <a href="#" id="editParams">编辑</a>
        </td>
    </tr>
</table>
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr>
        <td>
            <div style="float: left;margin-right: 10px;"><a href="<%=request.getContextPath()%>/task/addTaskUI">新增</a></div>
            <div style="float: left;margin-right: 10px;"><a href="javascript:modifyCheck();">修改</a></div>
            <div style="float: left;margin-right: 10px;"><a href="javascript:delCheck();">删除</a></div>
            <div style="float: right;margin-right: 20px;">
                关键字：<input type="text" name="keyword" maxlength="20" size="20" value="${bean.keyword}"/>
                <input type="submit" value="搜索" />
            </div>
        </td>
    </tr>
</table>
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
        <td style="color: #FFFFFF;"><input type="checkbox" id="chk_all"/>全选</td>
        <td style="color: #FFFFFF;">序号</td>
        <td style="color: #FFFFFF;">景区名称</td>
        <td style="color: #FFFFFF;">关键字</td>
        <td style="color: #FFFFFF;">价格</td>
    </tr>
    <c:forEach items="${pageView.records}" var="dto" varStatus="index">
        <tr bgcolor="f5f5f5" id="<c:out value='${dto.task.id}'/>">
            <td><input type="checkbox" name="ids" value="${dto.task.id}"></td>
            <td><c:out value="${index.count}"/></td>
            <td><c:out value="${dto.scenic.scenicName}"/></td>
            <td><c:out value="${dto.task.keyword}"/></td>
            <td><c:out value="${dto.task.price}"/></td>
        </tr>
    </c:forEach>
    <!--LOOP END-->
    <tr>
        <td>
            <div style="float: left;">
                <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                    <c:if test="${pageView.currentpage==wp}"><div class='red' style="float:left">${wp}</div></c:if>
                    <c:if test="${pageView.currentpage!=wp}"><div class="page" style="float:left"><a href="<%=request.getContextPath()%>/task/list?page=${wp}">[${wp}]</a></div></c:if>
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
</table>--%>
</body>
</html>
