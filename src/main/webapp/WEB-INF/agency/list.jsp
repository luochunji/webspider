<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>分销商列表</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
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
        };

    </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/agency/list" id="agencyForm" method="post">
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
</form>

</body>
</html>
