$(document).ready(function () {
    $("input").focus(function(){
        $(this).parent().css("border-color","#bbbbff");
        $(this).parent().css("box-shadow","0 0 4px #bbbbff");
    });
    $("input").blur(function(){
        $(this).parent().css("border-color","#cccccc");
        $(this).parent().css("box-shadow","none");
    });
});