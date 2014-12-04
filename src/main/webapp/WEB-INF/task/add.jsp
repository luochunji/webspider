<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function verifyForm(objForm){
        var flag = true;
        var formId = objForm.id;
        if($.trim($("#scenicName_search").val()) == ''){
            layer.alert("请输入或选择景区");
            return false;
        }
        $("#"+formId).find(".condition").each(function(){
            var keyword = $(this).find("[name='keyword']").val();
            var price = $(this).find("[name='price']").val();
            if(''==keyword || ''==price){
                layer.alert("关键字或价格不能为空！");
                flag = false;
                return false;
            }else{
                if(!verifyNumberCheck(price)){
                    flag = false;
                    layer.alert("价格只能输入数字并且大于0！");
                }
            }

        });
        if(flag){
            getCondition(objForm);
        }
    }
    function inputSuggest(keyword){
        if(keyword==''){
            $('.selectbox').hide();
        }else{
            var url = '<%=request.getContextPath()%>/task/getScenic';
            $.ajax( {
                type : "POST",
                url : url,
                data : {keyword:encodeURIComponent(keyword)},
                dataType: "json",
                success : function(data) {
                    if(null!=data && 'success' == data.result) {
                        var result = "";
                        var info = data.scenic;
                        $(".selectbox").empty();
                        if(info.length>0){
                            jQuery.each(info, function (i, n) {
                                result += '<p class="options">'+n+'</p>'
                            });
                            $(".selectbox").append(result);
                            $('.selectbox').show();
                        }else{
                            $('.selectbox').hide();
                        }
                    }
                },
                error :function(){
//                    layer.alert("网络连接出错！");
                }
            });
        }
    }
//    function selectCheck(){
//        var $this = $(this),
//            $txtInput = $(".keys"),
//            thisText = $this.text();
//        $txtInput.val(thisText);
//        $(".selectbox").slideToggle(200);
//    }
//    $(function(){
//        $(".options").live("click",selectCheck);
//    })

</script>
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="<%=request.getContextPath()%>/task/addTask" id="addTaskForm" method="post">
            <input type="hidden" name="conditions" id="conditions"/>
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">景区任务录入</h4>
                </div>
                <div class="modal-body">
                    <table class="tables" width="100%" id="taskTr">
                        <tr>
                            <td align="right">景区名称：</td>
                            <td colspan="2">
                                <%--<select name="scenicId" id="scenicId" class="form-control">--%>
                                    <%--<option value ="" selected>请选择...</option>--%>
                                    <%--<c:forEach items="${scenicMap}" var ="s">--%>
                                        <%--<option value ="${s.key}">${s.value}</option>--%>
                                    <%--</c:forEach>--%>
                                    <%--<>--%>
                                <%--</select>--%>
                                    <div class="keywordbox">
                                        <input type="text" name="scenicName" id="scenicName_search" class="keys" onkeyup="javascript:inputSuggest(this.value);" autocomplete="off">
                                        <input type="button" class="selectBtn">
                                        <div class="selectbox">
                                        </div>
                                        <div class="selectPlan">
                                            <c:forEach items="${scenicMap}" var ="s">
                                                <p class="selectOption">${s.value}</p>
                                            </c:forEach>
                                        </div>
                                    </div>
                            </td>
                            <%--<td>--%>
                                <%--<a href="<%=request.getContextPath()%>/scenic/addScenicUI">添加景区</a>--%>
                            <%--</td>--%>
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
                    <button type="button" class="btn btn-primary" onclick="javascript:verifyForm(this.form)">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:location.reload();">关闭</button>
                </div>
            </div>
            <!--end modal-content-->
        </form>

    </div>
</div>