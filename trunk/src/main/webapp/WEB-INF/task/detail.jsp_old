<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${task.taskName}-明细</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" language="JavaScript">
    </script>
</head>
<body>

<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
        <td>关键字</td>
        <td>平台名称</td>
        <td>过滤条件</td>
        <td>过滤值</td>
        <%--<td>操作</td>--%>
    </tr>
    <c:forEach items="${list}" var="execute">
        <tr bgcolor="f5f5f5" id="<c:out value='${execute.id}'/>">
            <td><c:out value="${execute.keyword}"/></td>
            <td><c:out value="${execute.platform.name}"/></td>
            <td><c:out value="${execute.filterCondition}"/></td>
            <td><c:out value="${execute.filterValue}"/></td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
