<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="<%=request.getContextPath()%>/task/temp/modifyTaskTemp" id="modifyTaskTempForm" method="post">
            <input type="hidden" name="id" id="taskId">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%">
                        <tr>
                            <td align="right">景区名称：</td>
                            <td colspan="2">
                                <input type="text" class="form-control" id="scenicName" disabled/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">运行时间:</td>
                            <td class="inwrap">
                                <input type="text" id="runtime" name="runtime" class="form-control" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">关键字:</td>
                            <td class="inwrap">
                                <input type="text" id="keyword" name="keyword" class="form-control" />
                            </td>
                            <td>
                                <input type="text" id="price" name="price" class="form-control price" value="00.00"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onClick="javascript:reSubmit(this.form)">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
            <!--end modal-content-->
        </form>

    </div>
</div>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>临时任务修改</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script type="text/javascript" language="JavaScript">
        $(function () {
            $('.Wdate').live('focus', function() {
                WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});
            });
        });
        function sureSubmit(objForm){
            objForm.submit();
        }
    </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/task/temp/modifyTaskTemp" id="taskForm" method="post">
    <input type="hidden" name="jsonArray" id="jsonArray"/>
    <input type="hidden" name="id" value="${task.id}">
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="f5f5f5">
            <table align="center" id="addTr">
                <tr bgcolor="6f8ac4">
                    <td colspan="5" ><font color="#FFFFFF">请填写临时任务信息</font></td>
                </tr>
                <tr bgcolor="f5f5f5">
                    <td width="15%"> <div align="right">景区名称 ：</div></td>
                    <td width="15%" colspan="3" align="left">
                        <select name="scenicId" style="width: 200px" disabled>
                            <option value ="" >请选择...</option>
                            <c:forEach items="${scenicMap}" var ="s">
                                <option value ="${s.key}"
                                        <c:if test="${s.key == task.scenicId}">
                                            selected
                                        </c:if>
                                 >${s.value}</option>
                            </c:forEach>
                            <>
                        </select>
                    </td>
                </tr>
                <tr bgcolor="f5f5f5">
                    <td width="15%"> <div align="right">运行时间 ：</div></td>
                    <td width="85%" colspan="4" align="left">
                        <input type="text" name="runtime" class="Wdate" disabled value="${taskRt.runtime}"/>
                    </td>
                </tr>
                <tr bgcolor="f5f5f5" class="condition">
                    <td width="15%" align="right"> <div align="right">关键字 ：</div></td>
                    <td width="15%" align="left"> <input name="keyword" size="30" maxlength="30" value="${task.keyword}"/></td>
                    <td width="15%"> <div align="right">价格 ：</div></td>
                    <td width="20%"> <input name="price" size="10" maxlength="10" value="${task.price}"/>（元）</td>
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
</html>--%>
