<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>编辑运行参数</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        function sureSubmit(objForm) {
            objForm.submit();
        }
        $(function () {
            $("#getAtr").click(function () {
                var size = $(".runtimeInput").length;
                if (size == 3) {
                    layer.alert('抱歉，最多只能设置三个运行时间!');
                    return;
                }
                var str = '';
                str += '<tr bgcolor="f5f5f5" class="runtimeInput">'
                str += '<td width="15%"> <div align="right">运行时间 ：</div></td>'
                str += '<td width="45%">'
                str += '<input type="text" name="runtime" class="Wdate" readonly/>'
                str += '</td>'
                str += '<td onclick="getDel(this)"><a href="#"><font color="red">-</font></a></td>'
                str += '</tr>'
                $("#addTr").append(str);
            });
        });

    </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/task/modifyParams" id="runtimeForm" method="post">
    <input type="hidden" name="paramKey" value="email">
    <input type="hidden" name="id"  value="${paramsMap['email'].id}">
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td>运行时间设置</td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td>
                <table align="center" id="addTr">
                    <c:forEach items="${runtimeMap}" var="rt" varStatus="index">
                        <tr class="runtimeInput">
                            <td width="20%">
                                <div align="right">运行时间 ：</div>
                            </td>
                            <td width="35%">
                                <input type="text" name="runtime" class="Wdate" value="${rt.value.runtime}" readonly/>
                            </td>
                            <c:if test="${index.count == 1}">
                                <td width="5%"><a href="#" id="getAtr"><font color="#ff1121ff">+</font></a></td>
                            </c:if>
                            <c:if test="${index.count >1}">
                                <td onclick="getDel(this)"><a href="#"><font color="red">-</font></a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
        <tr bgcolor="6f8ac4">
            <td>邮箱设置</td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td>
                <table align="center" >
                    <tr class="emailInput">
                        <td width="20%">
                            <div align="right">邮件地址 ：</div>
                        </td>
                        <td width="35%">
                            <input type="text" size="30" name="paramValue" value="${paramsMap['email'].paramValue}" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <font color="red">填写多个邮箱地址，请使用“;”分割</font>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td colspan="5">
                <div align="center">
                    <input type="button" name="Add" value=" 保 存 " class="frm_btn"
                           onClick="javascript:sureSubmit(this.form)">
                    &nbsp;&nbsp;<input type="button" name="Button" value=" 关 闭 " class="frm_btn"
                                       onclick="javascript:closeWindown();">
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
