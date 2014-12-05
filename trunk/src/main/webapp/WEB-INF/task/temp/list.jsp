<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>临时任务列表</title>
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
</head>
<script>
    $(document).ready(function() {
        $("input[name^='runtime']").live('focus',function() {
            $("input[name^='runtime']").datetimepicker({
                format: 'yyyy-mm-dd hh:ii:ss',
                language: 'zh-CN',
                weekStart: 1,
                todayBtn:  1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 1,
                forceParse: 0,
                showMeridian: 1,
                minuteStep:1
            })
        });
        $("#addTaskTemp").click(function() {
            tipsWindown("添加临时任务","url:get?<%=request.getContextPath()%>/task/temp/addTaskTempUI","800","300","true","","true","text");
        });
        $("#editTaskTemp").click(function(){
            if(modifyCheck()){
                var id = $("#id").val();
                tipsWindown("修改临时任务","url:get?<%=request.getContextPath()%>/task/temp/modifyTaskTempUI?id="+id,"800","300","true","","true","text");
            }
        });
    })
    function modifyCheck(){
        var size = $("input[name='ids']:checked").length;
        if(size == 0 ){
            layer.alert("请选择一个修改的临时任务！");
            return false;
        }else if(size >1){
            layer.alert("只能选择一个临时任务进行修改！");
            return false;
        }else{
            var readonly = $("input[name='ids']:checked").attr("readonly");
            if(readonly){
                layer.alert("已运行任务无法修改！");
                return false;
            }
            var id = $("input[name='ids']:checked").val();
            var url = '<%=request.getContextPath()%>/task/temp/modifyTaskTempUI?id='+id;
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
                        $("#runtime").val(data.taskRt.runtime);
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
    function delCheck(objForm){
        var size = $("input[name='ids']:checked").length;
        if(size == 0){
            layer.alert("请选择一个任务！");
            return false;
        }else{
            var readonly =false;
            $("input[name='ids']:checked").each(function(){
                readonly = $(this).attr("readonly");
                if(readonly){
                    return false;
                }
            });
            if(readonly){
                layer.alert("删除的任务中包含已运行任务，请重新选择！");
                return;
            }
            $.layer({
                shade: [0],
                area: ['auto','auto'],
                dialog: {
                    msg: '你确定要删除选中的任务吗？',
                    btns: 2,
                    type: 9,
                    btn: ['确定','取消'],
                    yes: function(){
                        var action = '<%=request.getContextPath()%>/task/temp/delTaskTemp';
                        objForm.action = action;
                        reSubmit(objForm);
                    }, no: function(){
                        return false;
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

<form action="<%=request.getContextPath()%>/task/temp/list" id="taskForm" method="post">
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <input type="hidden" name="id" id="id"/>

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
                            <th>限价</th>
                            <th>运行时间</th>
                            <th>当前状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageView.records}" var="dto" varStatus="index">
                            <tr bgcolor="f5f5f5" id="<c:out value='${dto.id}'/>">
                                <td><input type="checkbox" name="ids" value="${dto.id}" <c:if test="${dto.status == '已运行'}">readonly="true" </c:if> ></td>
                                <td><c:out value="${index.count}"/></td>
                                <td><c:out value="${dto.scenicName}"/></td>
                                <td><c:out value="${dto.keyword}"/></td>
                                <td><c:out value="${dto.lowprice}"/></td>
                                <td><c:out value="${dto.runtime}"/></td>
                                <td><c:out value="${dto.status}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav class="clearfix">
                        <ul class="pagination">
                            <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                                <c:if test="${pageView.currentpage==wp}"><li class="active"><a href="#" >${wp}</a></li></c:if>
                                <c:if test="${pageView.currentpage!=wp}"><li><a href="javascript:topage('taskForm',${wp})">${wp}</a></li></c:if>
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
<!--新增任务-->
<!-- Modal -->
<%@ include file="/WEB-INF/task/temp/add.jsp" %>
<!--end add-->
<!--修改-->
<!-- Modal -->
<%@ include file="/WEB-INF/task/temp/modify.jsp" %>
<%--<form action="<%=request.getContextPath()%>/task/temp/list" id="taskForm" method="post">
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <input type="hidden" name="id" id="id"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td style="color: #FFFFFF;">
                <div>临时任务列表</div>
            </td>
        </tr>
        <tr>
            <td>
                <div style="float: left;margin-right: 10px;"><a href="#" id="addTaskTemp">新增</a></div>
                <div style="float: left;margin-right: 10px;"><a href="#" id="editTaskTemp">修改</a></div>
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
            <td style="color: #FFFFFF;">限价</td>
            <td style="color: #FFFFFF;">运行时间</td>
            <td style="color: #FFFFFF;">当前状态</td>
        </tr>
        <c:forEach items="${pageView.records}" var="dto" varStatus="index">
            <tr bgcolor="f5f5f5" id="<c:out value='${dto.task.id}'/>">
                <td><input type="checkbox" name="ids" value="${dto.task.id}" <c:if test="${dto.task.status == 'RUNNED'}">disabled="true" </c:if> ></td>
                <td><c:out value="${index.count}"/></td>
                <td><c:out value="${dto.scenic.scenicName}"/></td>
                <td><c:out value="${dto.task.keyword}"/></td>
                <td><c:out value="${dto.task.price}"/></td>
                <td><c:out value="${dto.taskRuntime.runtime}"/></td>
                <td>
                    <c:if test="${dto.task.status == 'WATTING'}">等待中</c:if>
                    <c:if test="${dto.task.status == 'RUNNED'}">已运行</c:if>
                </td>
            </tr>
        </c:forEach>
        <!--LOOP END-->
        <tr>
            <td>
                <div style="float: left;">
                    <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                        <c:if test="${pageView.currentpage==wp}"><div class='red' style="float:left">${wp}</div></c:if>
                        <c:if test="${pageView.currentpage!=wp}"><div class="page" style="float:left"><a href="<%=request.getContextPath()%>/task/temp/list?page=${wp}">[${wp}]</a></div></c:if>
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
<form id="exportExcel" action="<%=request.getContextPath()%>/task/exportExcel" method="post" target="_blank">
    <input type="hidden" name="ids" id="ids">
    <input type="hidden" name="taskType" id="taskType" value="TEMP">
    <input type="hidden" name="keyword" id="keyword">
</form>
</body>
</html>
