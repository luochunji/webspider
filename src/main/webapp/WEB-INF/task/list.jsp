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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/FoshanRen.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAndText.js"></script>
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
        if(${0==pageView.totalrecord}){
            $('#myModal1').modal({
                backdrop:true,
                keyboard:true,
                show:true
            });
        }
    });
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
                    layer.alert("网络连接出错！");
                }
            });
        }
        return;
    }
    function delCheck(objForm) {
        var size = $("input[name='ids']:checked").length;
        if (size == 0) {
            layer.alert("请至少选择一个要删除的任务！");
            return;
        } else {
            $.layer({
                shade: [0],
                area: ['auto','auto'],
                dialog: {
                    msg: '你确定要删除选中的任务吗？',
                    btns: 2,
                    type: 9,
                    btn: ['确定','取消'],
                    yes: function(){
                        var action = '<%=request.getContextPath()%>/task/delTask';
                        objForm.action = action;
                        reSubmit(objForm);
                    }, no: function(){
                        return false
                    }
                }
            });
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
    <input type="hidden" name="id" />

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
                        <c:if test="${!empty bean.params['EMAIL']}">
                            <c:if test="${fn:length(bean.params['EMAIL'].paramValue)>25}">
                                <span title="${bean.emailTitle}">${fn:substring(bean.params['EMAIL'].paramValue, 0,25)}...</span>
                            </c:if>
                            <c:if test="${fn:length(bean.params['EMAIL'].paramValue)<=25}">
                                <span title="${bean.emailTitle}">${bean.params['EMAIL'].paramValue}</span>
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
                    </div>
                    <div class="row">
                        <div class="col-xs-12 searchbox">
                            <span>关键字:</span>
                            <input type="text" class="form-control keyinput" placeholder="请输入关键字" id="activityKeyw" name="keyword" value="${bean.keyword}">
                            <button class="btn btn-primary" onclick="javascript:topage(this.form,1);">搜索</button>
                            <input type="button" class="btn btn-default" value="导出" onclick="javascript:exportToExcel();"/>
                        </div>
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
                                <td><input type="checkbox" name="ids" value="${dto.id}"></td>
                                <td><c:out value="${index.count}"/></td>
                                <td><c:out value="${dto.scenicName}"/></td>
                                <td><c:out value="${dto.keyword}"/></td>
                                <td><c:out value="${dto.lowprice}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav class="clearfix">
                        <ul class="pagination">
                            <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                                <c:if test="${pageView.currentpage==wp}"><li class="active"><a href="#" >${wp}</a></li></c:if>
                                <c:if test="${pageView.currentpage!=wp}"><li><a href="javascript:topage(this.form,${wp})">${wp}</a></li></c:if>
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
</form>
    <!-- ./wrapper -->
    <!--搜索设置-->
    <!-- Modal -->
    <%@ include file="/WEB-INF/task/addParams.jsp" %>
    <!--新增任务-->
    <!-- Modal -->
    <%@ include file="/WEB-INF/task/add.jsp" %>
    <!--end add-->
    <!--修改-->
    <!-- Modal -->
    <%@ include file="/WEB-INF/task/modify.jsp" %>
</body>

<form id="exportExcel" action="<%=request.getContextPath()%>/task/exportExcel" method="post" target="_blank">
    <input type="hidden" name="ids" id="ids">
    <input type="hidden" name="keyword" id="keyword">
</form>
</body>
</html>
