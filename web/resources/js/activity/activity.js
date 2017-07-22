$(document).ready(function () {
    function appendCard() {
        $("#actContainer").append(
            "<div id='ActivityId' class='container'>"+
                "<hr style='top: 30px'/>"+
                "<div class='subLeft'><p class='pTitle'>武汉大学</p></div>"+
                "<div class='subRight'><p class='pSmall'>X年X月X日 00:00</p></div>"+
                "<div class='divcenter'>"+
                    "<p class='pContent'>dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd</p>"+
                "</div>"+
                "<hr/>"+
                "<div class='btnDiv'>"+
                    "<div class='comment'><img src='images/activity/comment.png'></div>"+
                    "<div class='good'><img class='goodImg' src='images/activity/good.png'></div>"+
                    "<p class='pGoodNum'>XX人觉得很赞</p>"+
                "</div>"+
                "<div class='commentDiv'>"+
                    "<p class='pCommenter'>1111:</p>"+
                    "<p class='pComment'>11111111111111111111111111111111111111111111111111</p>"+
                    "<p class='pCommenter'>1111:</p>"+
                    "<p class='pComment'>11111111111111111111111111</p>"+
                "</div>"+
                "<div class='writeCom'>"+
                    "<div class='inputcontainer'><input type='text' class='input_comment'></div>"+
                    "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>"+
                "</div>"+
            "</div>");
    }
    appendCard();
    appendCard();
    appendCard();

    $(window).scroll(function () {
        var scrollTop = $(this).scrollTop();
        var scrollHeight = document.body.scrollHeight;
        var windowHeight = $(this).height();
        if (scrollTop/(scrollHeight - windowHeight) >= 0.8) {
            //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
            appendCard();

        }

    });

    $(".good").live("click",function () {
        if($(this).find("img").attr('src') === 'images/activity/good.png'){
            $(this).find("img").attr('src',"images/activity/good_selected.png");
        }
        else {
            $(this).find("img").attr('src',"images/activity/good.png");
        }
    });
    $(".comment").live("click",function () {
        if($(this).find("img").attr('src') === 'images/activity/comment.png'){
            $(this).find("img").attr('src',"images/activity/comment_selected.png");
        }
        else {
            $(this).find("img").attr('src',"images/activity/comment.png");
        }
        $(this).parents(".container").children(".writeCom").toggle(200);
    });
    $(".submit_comment").live("click",function () {
        $(this).parents(".writeCom").children(".inputcontainer").children(".input_comment").val("");
        $(this).parents(".container").children(".btnDiv").children(".comment").find("img").attr('src', "images/activity/comment.png");
        $(this).parents(".writeCom").hide(200);
    });

    $("input").focus(function(){
        $(this).parent().css("border-color","#bbbbff");
        $(this).parent().css("box-shadow","0 0 4px #bbbbff");
    });
    $("input").blur(function(){
        $(this).parent().css("border-color","#cccccc");
        $(this).parent().css("box-shadow","none");
    });
});