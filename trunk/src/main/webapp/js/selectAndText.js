//关键字提示	
function keyCheck(){
    var $this = $(this),
        $txtInput = $(".keys"),
        thisText = $this.text();
    $txtInput.val(thisText);
    $(".selectbox").slideToggle(200);
}
//function selectTrigger(){
//    $(".selectbox").slideToggle(200);
//}
$(function(){
    $(".options").live("click",keyCheck);
    $('.selectBtn').click(function(){
        $(".selectPlan").slideToggle(200);
    });

})
function selectCheck(){
    var $this = $(this),
        $txtInput = $(".keys"),
        thisText = $this.text();
    $txtInput.val(thisText);
    $(".selectPlan").slideToggle(200);
}
//function selectTrigger(){
//    $(".selectPlan").slideToggle(200);
//}
$(function(){
    $(".selectOption").on("click",selectCheck);

})
