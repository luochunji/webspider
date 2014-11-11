<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加分销商</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#getAtr").click(function () {
                var str = '';
                var str = '<tr bgcolor="f5f5f5" class="content">'
                var str = '<td width="10%"> <div align="right">分销商编号 ：</div></td>'
                var str = '<td width="15%"> <input name="platFormId" size="30" maxlength="30"/></td>'
                var str = '<td width="10%"> <div align="right">所属平台 ：</div></td>'
                var str = '<td width="10%">'
                var str = '<select name="platFormId">'
                var str = '<option value ="" selected>请选择</option>'
                var str = '<c:forEach items="${pfMap}" var ="pf">'
                var str = '<option value ="${pf.key}">${pf.value}</option>'
                var str = '</c:forEach>'
                var str = '</select>'
                var str = '</td>'
                var str = '<td width="10%"> <div align="right">店铺名称 ：</div></td>'
                var str = '<td width="15%"> <input name="storeName" size="30" maxlength="30"/></td>'
                var str = '</tr>'
                $("#addTr").append(str);
            });
        });
    </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/agency/addAgency" id="agencyForm" method="post">
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
<tr bgcolor="f5f5f5">
    <table align="center" id="addTr">
        <tr bgcolor="f5f5f5" class="content">
            <td width="10%"> <div align="right">分销商编号 ：</div></td>
            <td width="15%"> <input name="platFormId" size="30" maxlength="30"/></td>
            <td width="10%"> <div align="right">所属平台 ：</div></td>
            <td width="10%">
                <select name="platFormId">
                    <option value ="" selected>请选择</option>
                    <c:forEach items="${pfMap}" var ="pf">
                        <option value ="${pf.key}">${pf.value}</option>
                    </c:forEach>
                </select>
            </td>
            <td width="10%"> <div align="right">店铺名称 ：</div></td>
            <td width="15%"> <input name="storeName" size="30" maxlength="30"/></td>
        </tr>
    </table>
</tr>
<tr>
    <td align="center"><a href="#" id="getAtr">追加内容</a></td>
</tr>
<tr bgcolor="f5f5f5">
    <td colspan="2"> <div align="center">
        <input type="button" name="Add" value=" 保 存 " class="frm_btn" onClick="javascript:sureSubmit(this.form)">
        &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
    </div></td>
</tr>
</table>
</form>
</body>
</html>
