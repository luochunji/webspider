<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>任务列表</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" language="JavaScript">
        function ActionEvent(operate,id) {
            var url = "<%=request.getContextPath()%>/task/"+operate+"?taskId="+id;
            if('detail' == operate || 'showResult' == operate){
                window.open(url);
            }else{
                $.ajax( {
                    type : "POST",
                    url : url,
                    dataType: "json",
                    success : function(data) {
                        if('success' == data.result){
                            alert(data.message);
                        }
                        window.location.reload();
                    },
                    error :function(){
                        alert("网络连接出错！");
                    }
                });
            }
        }
    </script>
</head>
<body>
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
        <td style="color: #FFFFFF;">任务名称</td>
        <td style="color: #FFFFFF;">上次执行时间</td>
        <td style="color: #FFFFFF;">下次执行时间</td>
        <td style="color: #FFFFFF;">创建人</td>
        <td style="color: #FFFFFF;">创建时间</td>
        <td style="color: #FFFFFF;">失效日期</td>
        <td style="color: #FFFFFF;">任务状态</td>
        <td style="color: #FFFFFF;">操作</td>
    </tr>
    <c:forEach items="${pageView.records}" var="task">
        <tr bgcolor="f5f5f5" id="<c:out value='${task.taskScheduler.id}'/>">
            <td><c:out value="${task.taskScheduler.taskName}"/></td>
            <td><c:out value="${task.prev_fire_time}"/></td>
            <td><c:out value="${task.next_fire_time}"/></td>
            <td><c:out value="${task.taskScheduler.creator}"/></td>
            <td><fmt:formatDate value="${task.taskScheduler.createTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></td>
            <td><fmt:formatDate value="${task.taskScheduler.endTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long" /></td>
            <td><c:out value="${task.taskScheduler.taskStatus}"/></td>
            <td>
                <input type="button" onclick="ActionEvent('detail','<c:out value="${task.taskScheduler.id}"/>')" value="查看明细"/>
                <c:if test="${task.taskScheduler.taskStatus == 'RUNNING'}">
                    <input type="button" onclick="ActionEvent('pause','<c:out value="${task.taskScheduler.id}"/>')" value="暂停"/>
                </c:if>
                <c:if test="${task.taskScheduler.taskStatus == 'PAUSE'}">
                    <input type="button" onclick="ActionEvent('resume','<c:out value="${task.taskScheduler.id}"/>')" value="恢复"/>
                </c:if>
                <input type="button" onclick="ActionEvent('remove','<c:out value="${task.taskScheduler.id}"/>')" value="删除"/>
                <input type="button" onclick="ActionEvent('showResult','<c:out value="${task.taskScheduler.id}"/>')" value="查看结果"/>
            </td>
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
</body>
</html>
