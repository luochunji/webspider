<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function addStoreInfoTr(formId){
        var str = '';
        str+='<tr class="store">';
        str+='<td align="right">网店名称:</td>';
        str+='<td class="inwrap">';
        str+='<input type="text" name="storeName" class="form-control" placeholder="请输入分销商网店名称"/>';
        str+='</td>';
        str+='<td align="right">网址:</td>';
        str+='<td class="inwrap">';
        str+='<input type="text" name="storeUrl" class="form-control" placeholder="请输入分销商网店网址"/>';
        str+='</td>';
        str+='<td onclick="getDel(this)"><a href="#">-</a></td>';
        str+='</tr>';
        $("#"+formId).find("[name='infoTable']").append(str);
    }
    function getStoreInfo(objForm) {
        var formId = objForm.id;
        var storeInfo = [];
        $("#"+formId).find(".store").each(function(){
            var json = {};
            json.storeName = $(this).find("[name='storeName']").val();
            json.storeUrl = $(this).find("[name='storeUrl']").val();
            storeInfo.push(json);
            $("#"+formId).find("[name='storeInfo']").val(JSON.stringify(storeInfo));
        });
        reSubmit(objForm);
    }
    function verifyForm(objForm){
        var regs=/^[-\D_a-zA-Z\u4e00-\u9fa5]+$/;
        var urlReg=/[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/;
        var list  = new Array(new Formfield("userName", "分销商用户名"),new Formfield("name", "分销商全称"),
                new Formfield("platFormId", "平台名称"));
        for(var i=0;i<list.length;i++){
            var objfield = eval("objForm."+ list[i].name);
            if($.trim(objfield.value)==""){
                layer.alert(list[i].label+ "不能为空");
                if(objfield.type!="hidden" && objfield.focus()) objfield.focus();
                return false;
            }
        }
        var flag = true;
        var formId = objForm.id;
        $("#"+formId).find(".store").each(function(){
            var storeName = $(this).find("[name='storeName']").val();
            var storeUrl = $(this).find("[name='storeUrl']").val();
            if(''==storeName || ''==storeUrl){
                layer.alert("网店名称、商户网址不能为空");
                flag = false;
                return false;
            }else{
                if(!urlReg.test(storeUrl)){
                    flag = false;
                    layer.alert("不符合网店网址规则，请重新输入!");
                }
            }

        });
        if(flag){
            getStoreInfo(objForm);
        }
    }
    function validateUsername(username){
        var regUseName=/^[\w-\s]+$/;
        if(!regs.test(username)){
            layer.alert("不符合规则,请重新输入");
            return;
        }
    }
</script>
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog FXdialog">
        <form action="<%=request.getContextPath()%>/agency/addAgency" id="addAgencyForm" method="post">
            <input name="storeInfo"  type="hidden">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">分销商网店信息录入</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%" name="infoTable">
                        <tr>
                            <td align="right">分销商用户：</td>
                            <td><input type="text" class="form-control" onblur="javascript:validateUsername(this.value)" name="userName"
                                       placeholder="请输入分销商用户名" ></td>
                            <td><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td align="right">分销商名称：</td>
                            <td><input type="text" class="form-control" name="name" placeholder="请输入分销商全称"/></td>
                            <td><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td align="right">平台名称：</td>
                            <td colspan="2">
                                <select name="platFormId" class="form-control">
                                    <option value ="" selected>请选择</option>
                                    <c:forEach items="${pfMap}" var ="pf">
                                        <option value ="${pf.key}">${pf.value.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><font color="red">*</font></td>
                        </tr>
                        <tr class="store">
                            <td align="right">网店名称：</td>
                            <td class="inwrap">
                                <input type="text" name="storeName" class="form-control" placeholder="请输入分销商网店名称"/>
                            </td>
                            <td align="right">网址：</td>
                            <td class="inwrap">
                                <input type="text" name="storeUrl" class="form-control" placeholder="请输入分销商网店网址"/>
                            </td>
                            <td onclick="addStoreInfoTr('addAgencyForm')"><a href="#">+</a></td>
                            <td><font color="red">*</font></td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="javascript:verifyForm(this.form);">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
            <!--end modal-content-->
        </form>

    </div>
</div>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</html>--%>
