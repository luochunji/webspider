<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>景区信息录入</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <%--<script type="text/javascript">--%>
        <%--function reSubmit(objForm){--%>
            <%--objForm.submit();--%>
        <%--}--%>
    <%--</script>--%>
</head>
<body>
<form action="<%=request.getContextPath()%>/scenic/addScenic" id="scenicForm" method="post">
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <input type="hidden" name="directUrl" value="${directUrl}" />
    <tr bgcolor="f5f5f5">
        <table align="center" id="addTr">
            <tr bgcolor="6f8ac4">
                <td colspan="5" ><font color="#FFFFFF">请填写景区信息</font></td>
            </tr>
            <tr bgcolor="f5f5f5">
                <td width="20%"> <div align="right">景区名称 ：</div></td>
                <td width="80%" colspan="4"> <input name="scenicName" size="50" maxlength="50"/></td>
            </tr>
        </table>
    </tr>
<tr bgcolor="f5f5f5">
    <td colspan="5"> <div align="center">
        <input type="button" name="Add" value=" 保 存 " class="frm_btn" onClick="javascript:reSubmit(this.form)">
        &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
    </div></td>
</tr>
</table>
</form>
</body>
</html>
