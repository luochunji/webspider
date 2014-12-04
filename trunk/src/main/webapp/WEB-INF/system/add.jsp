<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="<%=request.getContextPath()%>/task/addTask" id="addTaskForm" method="post">
            <input type="hidden" name="conditions" id="conditions"/>
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">新增系统参数</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%" id="taskTr">
                        <tr>
                            <td align="right">景区名称：</td>
                            <td colspan="2">
                                <select name="scenicId" class="form-control">
                                    <option value ="" selected>请选择...</option>
                                    <c:forEach items="${scenicMap}" var ="s">
                                        <option value ="${s.key}">${s.value}</option>
                                    </c:forEach>
                                    <>
                                </select>
                            </td>
                            <td>
                                <a href="<%=request.getContextPath()%>/scenic/addScenicUI">添加景区</a>
                            </td>
                        </tr>
                        <tr class="condition">
                            <td align="right">关键字:</td>
                            <td class="inwrap"><input name="keyword" type="text" class="form-control"/></td>
                            <td align="right">价格:</td>
                            <td class="inwrap"><input name="price" type="text" class="form-control price" value="00.00"/></td>
                            <td><a href="#" id="addTask">+</a></td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="javascript:getCondition(this.form)">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
            <!--end modal-content-->
        </form>

    </div>
</div>