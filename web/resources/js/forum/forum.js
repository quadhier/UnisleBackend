$(document).unbind("ready").bind("ready",function () {
    forumFirst = true;
    //$('.board_div').die();
    $('.board_div').live('click',function () {
        var boardcount = $(this).attr('title');
        var newURL = 'board.html?board='+ boardcount;
        //window.location.replace(newURL);
        simURLReplace(newURL);
    });
});