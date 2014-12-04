<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="<%=request.getContextPath()%>/system/modifyParams" id="modifyParamsForm" method="post">
            <input type="hidden" name="id" id="paramId">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改系统参数</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%">
                        <tr>
                            <td align="right">Description:</td>
                            <td colspan="2">
                                <input type="text" class="form-control" id="description" name="description" disabled/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">Value:</td>
                            <td class="inwrap">
                                <input type="text" id="paramValue" name="paramValue" class="form-control" datatype=""/>
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