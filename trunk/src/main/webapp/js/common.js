/**
 * Created by Luocj on 2014/11/5.
 */
$(function () {
    $("#chk_all").click(function(){
        if('checked' == $(this).attr("checked")){
            $("input[name='ids']").attr("checked",$(this).attr("checked"));
            $("input:disabled").attr("checked",false);
        }else{
            $("input[name='ids']").attr("checked",false);
        }
    });
});

function getDel(k) {
    $(k).parent().remove();
}

function Formfield(name, label){
    this.name=name;
    this.label=label;
}
function reSubmit(objForm) {
    objForm.submit();
}