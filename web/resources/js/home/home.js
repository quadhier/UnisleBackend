$(document).ready(function () {
    function loadAjax(pagename) {
        selfRst=$.ajax({url:pagename,async:false});
        $("#contentLoadContainer").html(selfRst.responseText);
    }


    $(".navFormat").click(function () {
        var pagename = $(this).attr('id') + ".html";
        loadAjax(pagename);
    });

    $("#nav_searchImg").click(function () {
        loadAjax("search.html");
    });
    
    $("#nav_search_input").keydown(function (e) {
        if (e.keyCode == 13) {
            loadAjax("search.html");
        }
    });

    $("#floatMessage").click(function () {
        $("#hiddenMessage").slideToggle(300);
    })
});