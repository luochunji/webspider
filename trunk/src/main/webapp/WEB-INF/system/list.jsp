<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>系统参数列表</title>
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
</head>
<script>
    $(document).ready(function () {
        $("#paramValue").live('focus',function() {
            $("input[datatype='TIME']").datetimepicker({
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
            });
            $("input[datatype!='TIME']").datetimepicker('remove');
        })
    });
    function modifyCheck() {
        var size = $("input[name='ids']:checked").length;
        if (size == 0) {
            layer.alert("请选择一个修改参数！");
            return false;
        } else if (size > 1) {
            layer.alert("只能选择一个参数进行修改！");
            return false;
        } else {
            var id = $("input[name='ids']:checked").val();
            var url = '<%=request.getContextPath()%>/system/modifyParamsUI?id='+id;
            $.ajax( {
                type : "POST",
                url : url,
                dataType: "json",
                success : function(data) {
                    if('success' == data.result){
                        $("#paramId").val(data.param.id);
                        $("#description").val(data.param.description);
                        $("#paramValue").val(data.param.paramValue);
                        $("#paramValue").attr("datatype",data.param.dataType);
                        if('TIME' == data.param.dataType){
                            $("#paramValue").attr("readonly","readonly");
                        }else{
                            $("#paramValue").removeAttr("readonly");
                        }
                        $('#myModal2').modal({
                            backdrop:true,
                            keyboard:true,
                            show:true
                        });
                    }
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
</script>
<body class="skin-blue systemparamlist">
<form action="<%=request.getContextPath()%>/system/list" id="paramsForm" method="post">
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <input type="hidden" name="id" id="id"/>
    <div class="wrapper row-offcanvas row-offcanvas-left">
        <!-- Main content -->
        <section class="content">
            <div class="box box-primary">
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
                                </div>
                            </div>
                        </div>
                        <!--end searchbox-->
                    </div>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th><input type="checkbox" id="chk_all"/>全选</th>
                            <th>功能描述</th>
                            <th>参数值</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageView.records}" var="params" varStatus="index">
                            <tr>
                                <td><input type="checkbox" name="ids" value="${params.id}"></td>
                                <td><c:out value="${params.description}"/></td>
                                <td><c:out value="${params.paramValue}"/></td>
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
<!--新增参数-->
<!-- Modal -->
<%--<%@ include file="/WEB-INF/system/add.jsp" %>--%>
<!--end add-->
<!--修改参数-->
<!-- Modal -->
<%@ include file="/WEB-INF/system/modify.jsp" %>
</body>

</body>
</html>
