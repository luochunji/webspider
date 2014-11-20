<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>景区任务列表</title>
    <link   type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/tipswindown.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/tipswindown.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<script>
    $(document).ready(function() {
        $("#setParams").click(function() {
            tipsWindown("设置参数","url:get?<%=request.getContextPath()%>/task/addParamsUI","350","160","true","","true","text");
        });
        $("#editParams").click(function() {
            tipsWindown("编辑参数","url:get?<%=request.getContextPath()%>/task/modifyParamsUI","400","300","true","","true","text");
        });
        /*$("#set_email").click(function() {
            tipsWindown("设置邮箱地址","url:get?<%=request.getContextPath()%>/task/addEmailUI","350","160","true","","true","text");
        });
        $("#edit_email").click(function() {
            tipsWindown("编辑邮箱地址","url:get?<%=request.getContextPath()%>/task/addEmailUI","350","160","true","","true","text");
        });*/
    });
    function reSubmit(){
        $("#taskForm").submit();

    }
    function topage(page){
        $("#page").val(page);
        reSubmit();
    }
    function modifyCheck(){
        var size = $("input[name='ids']:checked").length;
        if(size == 0 ){
            alert("请选择一个修改的景区任务！");
            return;
        }else if(size >1){
            alert("只能选择一个景区任务进行修改！");
        }else{
            var id = $("input[name='ids']:checked").val();
            $("#id").val(id)
            var action = '<%=request.getContextPath()%>/task/modifyTaskUI';
            $("#taskForm").attr("action",action);
            reSubmit();
        }
        return;
    }
    function delCheck(){
        var size = $("input[name='ids']:checked").length;
        if(size == 0){
            alert("请选择一个任务！");
            return;
        }else{
            if(window.confirm('你确定要删除选中的任务吗？')){
                var action = '<%=request.getContextPath()%>/task/delTask';
                $("#taskForm").attr("action",action);
                reSubmit();
            }else{
                return false;
            }
        }
    }
</script>
<body>

<form action="<%=request.getContextPath()%>/task/list" id="taskForm" method="post">
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <input type="hidden" name="id" id="id"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
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
    </table>
</form>
</body>
</html>
