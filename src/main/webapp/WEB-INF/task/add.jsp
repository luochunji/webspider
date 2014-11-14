<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>景区任务录入</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/calendar4.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/taskCommon.js"></script>
    <script type="text/javascript" language="JavaScript">
        function sureSubmit(objForm){
            getCondition();
            objForm.submit();
        }

        $(function () {
            $("#getAtr").click(function () {
                var size = $(".condition").length
                if(size == 3){
                    alert('抱歉，最多只能输入三个关键字!');
                    return;
                }
                var str = '';
                str += '<tr bgcolor="f5f5f5" class="condition">'
                str += '<td width="10%"> <div align="right">关键字 ：</div></td>'
                str += '<td width="15%"> <input name="keyword" size="30" maxlength="30"/></td>'
                str += '<td width="10%"> <div align="right">价格 ：</div></td>'
                str += '<td width="15%"> <input name="price" size="10" maxlength="10"/>（元）</td>'
                str += '<td onclick="getDel(this)"><a href="#"><font color="red">-</font></a></td>'
                str += '</tr>'
                $("#addTr").append(str);
            });
        });
        function getCondition(){
            var conditions = [];
            $(".condition").each(function(){
                var json = {};
                json.keyword = $(this).find("[name='keyword']").val();
                json.price = $(this).find("[name='price']").val();
                conditions.push(json);
            })
            $("#conditions").val(JSON.stringify(conditions));
        }

    </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/task/addTask" id="taskForm" method="post">
    <input type="hidden" name="conditions" id="conditions"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="f5f5f5">
            <table align="center" id="addTr">
                <tr bgcolor="6f8ac4">
                    <td colspan="5" ><font color="#FFFFFF">请填写景区任务信息</font></td>
                </tr>
                <tr bgcolor="f5f5f5">
                    <td width="20%"> <div align="right">景区名称 ：</div></td>
                    <td width="80%" colspan="4">
                        <select name="scenicId" style="width: 200px">
                            <option value ="" selected>请选择...</option>
                            <c:forEach items="${scenicMap}" var ="s">
                                <option value ="${s.key}">${s.value}</option>
                            </c:forEach>
                            <>
                        </select>
                        <a href="<%=request.getContextPath()%>/scenic/addScenicUI">添加景区</a>
                    </td>
                </tr>
                <tr bgcolor="f5f5f5" class="condition">
                    <td width="10%"> <div align="right">关键字 ：</div></td>
                    <td width="15%"> <input name="keyword" size="30" maxlength="30"/></td>
                    <td width="10%"> <div align="right">价格 ：</div></td>
                    <td width="15%"> <input name="price" size="10" maxlength="10"/>（元）</td>
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