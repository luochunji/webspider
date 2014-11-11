/**
 * Created by Luocj on 2014/11/5.
 */

function changeStatus(str) {
    $(".timeDiv").css("display", "none");
//    $("id^=" + str).css("display", "block");
    $("div[id^=" + str+"]").css("display", "block");
}


function getDel(k) {
    $(k).parent().remove();
}
