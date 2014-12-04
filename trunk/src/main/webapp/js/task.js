$(document).ready(function () {
    $("#addSearchTime").click(function () {
        var size = $(".runtimeInput").length;
        if (size == 3) {
            alert('抱歉，最多只能设置三个运行时间!');
            return;
        }
        var str = '';
        str +='<tr class="runtimeInput">';
        str +='<td align="right">搜索时间：</td>';
        str +='<td><input type="text"  name="runtime" class="form-control" placeholder="请选择发送时间..." required readonly></td>';
        str +='<td onclick="getDel(this)"><a href="#">-</a></td>';
        str +='</tr>';
        $("#paramTr").append(str);
    });

    $("#addTask").click(function () {
        var size = $(".condition").length
        if(size == 3){
            alert('抱歉，最多只能输入三个关键字!');
            return;
        }
        var str = '';
        str +='<tr class="condition">'
        str +='<td align="right">关键字:</td>'
        str +='<td class="inwrap"><input name="keyword" type="text" class="form-control"/></td>'
        str +='<td align="right">价格:</td>'
        str +='<td class="inwrap"><input name="price" type="text" class="form-control price" value="00.00"/></td>'
        str +='<td onclick="getDel(this)"><a href="#">-</a></td>'
        str +='</tr>'
        $("#taskTr").append(str);
    });
});
function topage(objForm,page) {
    $("#page").val(page);
    reSubmit(objForm);
}
function exportToExcel(){
    var ids = '';
    var arrChk=$("input[name='ids'][checked]");
    $(arrChk).each(function(){
        ids +=this.value+',';
    });
    $("#ids").val(ids);
    $("#keyword").val($("#activityKeyw").val());
    $("#exportExcel").submit();
}