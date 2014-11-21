/**
 * Created by Luocj on 2014/11/5.
 */
function changeStatus(str) {
    $(".timeDiv").css("display", "none");
    $("div[id^=" + str+"]").css("display", "block");
}
$(function () {
    $("#chk_all").click(function(){
        if('checked' == $(this).attr("checked")){
            $("input[name='ids']").attr("checked",$(this).attr("checked"));
        }else{
            $("input[name='ids']").attr("checked",false);
        }
    });
});

function getDel(k) {
    $(k).parent().remove();
}

