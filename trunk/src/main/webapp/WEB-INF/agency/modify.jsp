<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function buildUI(data) {
        var agency = data.agency;
        var pfMap = data.pfMap;
        $("#modifyAgencyForm").find("input[name='userName']").val(agency.userName);
        $("#modifyAgencyForm").find("input[name='name']").val(agency.name);
        $("#modifyAgencyForm").find("input[name='agencyId']").val(agency.id);
        var platFormObj = $("#modifyAgencyForm").find("select[name='platFormId']");
        var storeInfoObj = $("#modifyAgencyForm").find("#addStoreTr");
        var store = $("#modifyAgencyForm").find(".store");
        platFormObj.empty();
        store.empty();
        $.each(pfMap,function(n,value) {
            if(value.id == agency.platFormId){
                platFormObj.append("<option value ="+value.id+" selected>"+value.name+"</option>");
            }else{
                platFormObj.append("<option value ="+value.id+">"+value.name+"</option>");
            }

        });
        if(agency.agencyStores.length == 0){
            var str ='';
            str +='<tr class="store">';
            str +='<td align="right">网店名称:</td>';
            str +='<td class="inwrap">';
            str +='<input type="text" name="storeName" class="form-control"/>';
            str +='</td>';
            str +='<td align="right">网址:</td>';
            str +='<td class="inwrap">';
            str +='<input type="text" name="storeUrl" class="form-control" />';
            str +='</td>';
            str +='<td onclick="javascript:addStoreInfoTr(modifyAgencyForm);"><a href="#">+</a></td>';
            str +='</tr>';
            storeInfoObj.append(str);
        }else{
            $.each(agency.agencyStores,function(n,value) {
                var str ='';
                str +='<tr class="store">';
                str +='<td align="right">网店名称:</td>';
                str +='<td class="inwrap">';
                str +='<input type="text" name="storeName" value='+value.storeName+' class="form-control"/>';
                str +='</td>';
                str +='<td align="right">网址:</td>';
                str +='<td class="inwrap">';
                str +='<input type="text" name="storeUrl" value='+value.storeUrl+' class="form-control" />';
                str +='</td>';
                if(0==n){
                    str +='<td onclick="javascript:addStoreInfoTr(modifyAgencyForm);"><a href="#">+</a></td>';
                }else{
                    str +='<td onclick="javascript:getDel(this);"><a href="#">-</a></td>';
                }
                str +='</tr>';
                storeInfoObj.append(str);

            });
        }

    }
</script>
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog FXdialog">
        <form action="<%=request.getContextPath()%>/agency/modifyAgency" id="modifyAgencyForm" method="post">
            <input type="hidden" name="agencyId"/>
            <input name="storeInfo"  type="hidden">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">分销商网店信息录入</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%" id="addStoreTr" name="infoTable">
                        <tr>
                            <td align="right">分销商用户名:</td>
                            <td><input type="text" class="form-control" name = "userName"
                                       value="${agency.userName}" required></td>
                            <td><font>*</font></td>
                        </tr>
                        <tr>
                            <td align="right">分销商名称:</td>
                            <td><input type="text" class="form-control" value="${agency.name}"  name="name" /></td>
                            <td><font>*</font></td>
                        </tr>
                        <tr>
                            <td align="right">平台名称:</td>
                            <td colspan="2">
                                <select name="platFormId" class="form-control">
                                </select>
                            </td>
                            <td><font color="red">*</font></td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="javascript:getStoreInfo(this.form);">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
            <!--end modal-content-->
        </form>

    </div>
</div>