<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>分销商信息录入</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script type="text/javascript">
        function sureSubmit(objForm){
            getStoreInfo();
            objForm.submit();
        }
        $(function () {
            $("#getAtr").click(function () {
                var str = '';
                str += '<tr bgcolor="f5f5f5" class="store">'
                str += '<td width="10%"> <div align="right">店铺名称 ：</div></td>'
                str += '<td width="15%"> <input name="storeName" size="18" maxlength="18"/></td>'
                str += '<td width="10%"> <div align="right">店铺地址 ：</div></td>'
                str += '<td width="15%"> <input name="storeUrl" size="50" maxlength="4000"/></td>'
                str += '<td onclick="getDel(this)"><a href="#"><font color="red">-</font></a></td>'
                str += '</tr>'
                $("#addTr").append(str);
            });
        });
        function getStoreInfo() {
            var storeInfo = [];
            $(".store").each(function(){
                var json = {};
                json.storeName = $(this).find("[name='storeName']").val();
                json.storeUrl = $(this).find("[name='storeUrl']").val();
                storeInfo.push(json);
            })
            $("#storeInfo").val(JSON.stringify(storeInfo));
        }

    </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/agency/addAgency" id="agencyForm" method="post">
    <input name="storeInfo" id="storeInfo" type="hidden">
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="f5f5f5">
        <table align="center" id="addTr">
            <tr bgcolor="6f8ac4">
                <td colspan="5" ><font color="#FFFFFF">请填写分销商信息</font></td>
            </tr>
            <tr bgcolor="f5f5f5">
                <td width="20%"> <div align="right">分销商用户名 ：</div></td>
                <td width="80%" colspan="4"> <input name="userName" size="16" maxlength="16"/></td>
            </tr>
            <tr bgcolor="f5f5f5">
                <td width="20%"> <div align="right">分销商全称 ：</div></td>
                <td width="80%" colspan="4"> <input name="name" size="30" maxlength="30"/></td>
            </tr>
            <tr bgcolor="f5f5f5">
                <td width="20%"> <div align="right">所属平台 ：</div></td>
                <td width="80%" colspan="4">
                    <select name="platFormId">
                        <option value ="" selected>请选择</option>
                        <c:forEach items="${pfMap}" var ="pf">
                            <option value ="${pf.key}">${pf.value.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr bgcolor="f5f5f5" class="store">
                <td width="10%"> <div align="right">店铺名称 ：</div></td>
                <td width="15%"> <input name="storeName" size="18" maxlength="18"/></td>
                <td width="10%"> <div align="right">店铺地址 ：</div></td>
                <td width="15%"> <input name="storeUrl" size="50" maxlength="4000"/></td>
                <td><a href="#" id="getAtr"><font color="#ff1121ff">+</font></a></td>
            </tr>
        </table>
    </tr>
<tr bgcolor="f5f5f5">
    <td colspan="5"> <div align="center">
        <input type="button" name="Add" value=" 保 存 " class="frm_btn" onClick="javascript:sureSubmit(this.form)">
        &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
    </div></td>
</tr>
</table>
</form>
</body>
</html>
