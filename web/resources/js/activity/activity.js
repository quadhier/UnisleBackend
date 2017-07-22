$(document).ready(function () {
    function appendCard() {
        $("#actContainer").append(
            "<div id='ActivityId' class='container'>" +
            "<hr style='top: 30px'/>" +
            "<div class='subLeft'><p class='pTitle'>武汉大学</p></div>" +
            "<div class='subRight'><p class='pSmall'>X年X月X日 00:00</p></div>" +
            "<div class='divcenter'>" +
            "<p class='pContent'>dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd</p>" +
            "</div>" +
            "<hr/>" +
            "<div class='btnDiv'>" +
            "<div class='comment'><img src='images/activity/comment.png'></div>" +
            "<div class='good'><img class='goodImg' src='images/activity/good.png'></div>" +
            "<p class='pGoodNum'>XX人觉得很赞</p>" +
            "</div>" +
            "<div class='commentDiv'>" +
            "<p class='pCommenter'>1111:</p>" +
            "<p class='pComment'>11111111111111111111111111111111111111111111111111</p>" +
            "<p class='pCommenter'>1111:</p>" +
            "<p class='pComment'>11111111111111111111111111</p>" +
            "</div>" +
            "<div class='writeCom'>" +
            "<div class='inputcontainer'><input type='text' class='input_comment'></div>" +
            "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>" +
            "</div>" +
            "</div>");
    }

    appendCard();
    appendCard();
    appendCard();


    // 储存用户名
    var userName;

    /*
     *
     * 加载个人信息
     *
     * */

    $.ajax({
        type: "GET",
        url: "self",
        dataType: "json",
        data: {},
        async: false,
        timeout: 5000,
        cache: false,
        beforeSend: function () {

        },

        error: function () {

        },

        success: function (res) {
            //alert(res);
            //alert(res.nickname);
            //alert(res.department);

            if (res.sex === 'male')
                $("#u237_img").src = "images/activity/male.png";
            else
                $("#u237_img").src = "images/activity/female.png";
            $("#cache5").text(res.nickname);
            $("#cache7").text(res.department + "-" + res.grade + "级");
            userName = res.nickname;
        }

    });

    /*
     *
     * 绑定发布动态的事件
     *
     * */

    $("#u271").click(function () {
        if ($("#u245_input").val() !== "") {
            $.ajax({
                type: "POST",
                url: "activity",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    "shieldIDList": null,
                    "content": $("#u245_input").val(),
                    "attachment": null
                }),
                //async:false,
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },

                error: function () {

                },

                success: function (activityid) {

                    var nowDate = new Date();

                    $("#u246").prepend("<div id='" + activityid + "' class='container'>" +
                        "<hr style='top: 28px'/>" +
                        "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
                        "<div class='subRight'><p class='pSmall'>" + nowDate.toLocaleString() + "</p></div>" +
                        "<div class='divcenter'>" +
                        "<p class='pContent'>" + $("#u245_input").val() + "</p>" +
                        "</div>" +
                        "<hr style='top: 20px'/>" +
                        "<div class='btnDiv'>" +
                        "<div class='comment'><img src='images/activity/comment.png'></div>" +
                        "<div class='good'><img class='goodImg' src='images/activity/good.png'></div>" +
                        "<p class='pGoodNum'>" + "<span>0</span>" + "人觉得很赞</p>" +
                        "</div>" +
                        "<div class='commentDiv'></div>" +
                        "<div class='writeCom'>" +
                        "<div class='inputcontainer'><input type='text' class='input_comment'></div>" +
                        "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>" +
                        "</div>");
                    $("#u245_input").val("");

                }
            });


        }
    });

    /*
     *
     * 加载动态并设置滚动时加载
     *
     * */
    $.ajax({
        type: "GET",

        //服务端url
        url: "activity/all/isfirst",
        dataType: "json",
        data: {
            "lastTime": null,
            "view": "self"
        },
        async: false,                           //可能会使得效率下降
        timeout: 5000,
        cache: false,
        beforeSend: function () {
            //加载中...
        },
        error: function () {

        },

        //以下函数获取服务端传递给客户端的json格式数据
        success: function (res) {

            if (res.tag === true) {
                var userName = res.userName;
                var activity = res.activity;
                var comments = res.comments;
                var commenters = res.commenters;
                var pro = res.pro;


                //设置赞图标的显示格式
                var proDiv;
                if (pro === true) {
                    proDiv = "<img class='goodImg' src='images/activity/good_selected.png'>";
                } else {
                    proDiv = "<img class='goodImg' src='images/activity/good.png'>";
                }

                var commentDisplay = "<div class='commentDiv'>";

                // 组成评论的展示字符串
                for (i in comments) {
                    commentDisplay = commentDisplay +
                        "<p class='pCommenter'>" + commenters[i] + "</p>" +
                        "<p class='pComment'>" + comments[i].content + "</p>";
                }
                commentDisplay = commentDisplay + "</div>";

                var pubDate = new Date(activity.publicdatetime);

                $("#u246").append("<div id='" + activity.activityid + "' class='container'>" +
                    "<hr style='top: 28px'/>" +
                    "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
                    "<div class='subRight'><p class='pSmall'>" + pubDate.toLocaleString() + "</p></div>" +
                    "<div class='divcenter'>" +
                    "<p class='pContent'>" + activity.content + "</p>" +
                    "</div>" +
                    "<hr style='top: 20px'/>" +
                    "<div class='btnDiv'>" +
                    "<div class='comment'><img src='images/activity/comment.png'></div>" +
                    "<div class='good'>" + proDiv + "</div>" +
                    "<p class='pGoodNum'>" + "<span>" + activity.pros + "</span>" + "人觉得很赞</p>" +
                    "</div>" +
                    commentDisplay +
                    "<div class='writeCom'>" +
                    "<div class='inputcontainer'><input type='text' class='input_comment'></div>" +
                    "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>" +
                    "</div>");

            }

        }
    });


    for (var i = 0; i < 3; i++) {

        $.ajax({

            type: "GET",

            //服务端url
            url: "activity/all/follow",
            dataType: "json",
            data: {
                "lastTime": null,
                "view": "self"
            },
            //async: false,                           //可能会使得效率下降
            timeout: 5000,
            cache: false,
            beforeSend: function () {
                //加载中...
            },
            error: function () {

            },
            //以下函数获取服务端传递给客户端的json格式数据
            success: function (res) {

                if (res.tag === true) {
                    var userName = res.userName;
                    var activity = res.activity;
                    var comments = res.comments;
                    var commenters = res.commenters;
                    var pro = res.pro;


                    //设置赞图标的显示格式
                    var proDiv;
                    if (pro === true) {
                        proDiv = "<img class='goodImg' src='images/activity/good_selected.png'>";
                    } else {
                        proDiv = "<img class='goodImg' src='images/activity/good.png'>";
                    }

                    var commentDisplay = "<div class='commentDiv'>";

                    // 组成评论的展示字符串
                    for (i in comments) {
                        commentDisplay = commentDisplay +
                            "<p class='pCommenter'>" + commenters[i] + "</p>" +
                            "<p class='pComment'>" + comments[i].content + "</p>";
                    }
                    commentDisplay = commentDisplay + "</div>";

                    var pubDate = new Date(activity.publicdatetime);

                    $("#u246").append("<div id='" + activity.activityid + "' class='container'>" +
                        "<hr style='top: 28px'/>" +
                        "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
                        "<div class='subRight'><p class='pSmall'>" + pubDate.toLocaleString() + "</p></div>" +
                        "<div class='divcenter'>" +
                        "<p class='pContent'>" + activity.content + "</p>" +
                        "</div>" +
                        "<hr style='top: 20px'/>" +
                        "<div class='btnDiv'>" +
                        "<div class='comment'><img src='images/activity/comment.png'></div>" +
                        "<div class='good'>" + proDiv + "</div>" +
                        "<p class='pGoodNum'>" + "<span>" + activity.pros + "</span>" + "人觉得很赞</p>" +
                        "</div>" +
                        commentDisplay +
                        "<div class='writeCom'>" +
                        "<div class='inputcontainer'><input type='text' class='input_comment'></div>" +
                        "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>" +
                        "</div>");

                }

            }
        });
    }


    //var hasMore = true;
    $("#u227_state0").scroll(function () {

        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(this).get(0).scrollHeight;
        var windowHeight = $(this).height();


        //如果有更多动态，则继续加载
        if (scrollTop / ( scrollHeight - windowHeight ) >= 0.8 && scrollTop / ( scrollHeight - windowHeight ) < 1) { //&& hasMore) {

            $.ajax({

                type: "GET",

                //服务端url
                url: "activity/all/follow",
                dataType: "json",
                data: {
                    "lastTime": null,
                    "view": "self"
                },
                //async: false,                           //可能会使得效率下降
                timeout: 5000,
                cache: false,
                beforeSend: function () {
                    //加载中...
                },
                error: function () {

                },
                //以下函数获取服务端传递给客户端的json格式数据
                success: function (res) {

                    if (res.tag === true) {
                        var userName = res.userName;
                        var activity = res.activity;
                        var comments = res.comments;
                        var commenters = res.commenters;
                        var pro = res.pro;


                        //设置赞图标的显示格式
                        var proDiv;
                        if (pro === true) {
                            proDiv = "<img class='goodImg' src='images/activity/good_selected.png'>";
                        } else {
                            proDiv = "<img class='goodImg' src='images/activity/good.png'>";
                        }


                        var commentDisplay = "<div class='commentDiv'>";

                        // 组成评论的展示字符串
                        for (i in comments) {
                            commentDisplay = commentDisplay +
                                "<p class='pCommenter'>" + commenters[i] + "</p>" +
                                "<p class='pComment'>" + comments[i].content + "</p>";
                        }
                        commentDisplay = commentDisplay + "</div>";

                        var pubDate = new Date(activity.publicdatetime);


                        //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
                        $("#u246").append("<div id='" + activity.activityid + "' class='container'>" +
                            "<hr style='top: 28px'/>" +
                            "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
                            "<div class='subRight'><p class='pSmall'>" + pubDate.toLocaleString() + "</p></div>" +
                            "<div class='divcenter'>" +
                            "<p class='pContent'>" + activity.content + "</p>" +
                            "</div>" +
                            "<hr style='top: 20px'/>" +
                            "<div class='btnDiv'>" +
                            "<div class='comment'><img src='images/activity/comment.png'></div>" +
                            "<div class='good'>" + proDiv + "</div>" +
                            "<p class='pGoodNum'>" + "<span>" + activity.pros + "</span>" + "人觉得很赞</p>" +
                            "</div>" +
                            commentDisplay +
                            "<div class='writeCom'>" +
                            "<div class='inputcontainer'><input type='text' class='input_comment'></div>" +
                            "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>" +
                            "</div>");

                    } //else {
                      //  hasMore = false;
                      //}

                }
            });

        }
    });


    /*
     *
     * 为点赞图标绑定事件
     *
     * */

    $(".good").live("click", function () {

        var activityid = $(this).parents(".container").attr("id");
        // 如果未点赞，再次点击则点赞
        // 向后台同步数据，同时使显示的的点赞数加一
        if ($(this).find("img").attr('src') === 'images/activity/good.png') {
            $(this).find("img").attr('src', "images/activity/good_selected.png");
            //alert(activityid);
            // 向后台传递点赞消息
            $.ajax({
                type: "POST",

                //服务端url
                url: "activity/pro",
                dataType: "json",
                data: {
                    "activityid": activityid
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {
                    //加载中...
                },
                error: function () {

                },
                success: function () {
                    var pdiv = "#" + activityid;
                    var proNum = 1 + parseInt($(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text());
                    $(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text(proNum);

                }
            });

        }
        // 如果已经点赞，再次点击则取消点赞
        // 向后台同步数据，同时使显示的的点赞数减一
        else {
            $(this).find("img").attr('src', "images/activity/good.png");
            //alert(activityid);
            // 向后台传递取消点赞的消息
            $.ajax({
                type: "POST",

                //服务端url
                url: "activity/pro/delete",
                dataType: "json",
                data: {
                    "activityid": activityid
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {
                    //加载中...
                },
                error: function () {

                },
                success: function (res) {

//                            if(res.result === "ERROR")
//                                alert(res.reason);
//                            alert(res.result);

                    var pdiv = "#" + activityid;
                    var proNum = parseInt($(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text()) - 1;
                    $(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text(proNum);
                }
            });

        }
    });

    /*
     *
     *
     *  为评论图标绑定事件
     *
     * */
    $(".comment").live("click", function () {
        if ($(this).find("img").attr('src') === 'images/activity/comment.png') {
            $(this).find("img").attr('src', "images/activity/comment_selected.png");

        }
        else {
            $(this).find("img").attr('src', "images/activity/comment.png");
        }
        $(this).parents(".container").children(".writeCom").toggle(200);
    });


    /*
     *
     *
     *  为评论按钮绑定事件
     *
     * */

    $(".submit_comment").live("click", function () {


        //alert($(this).text());
        var inputEle = $(this).parents(".submitcontainer").siblings(".inputcontainer").children("input");
        var input = inputEle.val();
        //alert(input);
        if (input !== "") {

            var activityid = $(this).parents(".container").attr("id");
            //alert(activityid);
            //alert(input);
            $.ajax({
                type: "POST",

                // 服务端url
                // 不用contentType指定编码则会出现乱码问题
                url: "activity/comment",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType: "json",
                data: {
                    "activityid": activityid,
                    "content": input
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },
                error: function () {

                },
                success: function (res) {

//                            if (res.result === "ERROR")
//                                alert(res.reason);
//                            alert(res.result);

                    var pdiv = "#" + activityid;
                    $(pdiv).children(".commentDiv").append("<p class='pCommenter'>" + userName + "</p>" +
                        "<p class='pComment'>" + input + "</p>");
                    inputEle.val("");
                    inputEle.parents(".container").children(".btnDiv").children(".comment").click();

                }
            });
        }
    });


    $(window).scroll(function () {
        var scrollTop = $(this).scrollTop();
        var scrollHeight = document.body.scrollHeight;
        var windowHeight = $(this).height();
        if (scrollTop / (scrollHeight - windowHeight) >= 0.8) {
            //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
            appendCard();

        }

    });

    $(".good").live("click", function () {
        if ($(this).find("img").attr('src') === 'images/activity/good.png') {
            $(this).find("img").attr('src', "images/activity/good_selected.png");
        }
        else {
            $(this).find("img").attr('src', "images/activity/good.png");
        }
    });
    $(".comment").live("click", function () {
        if ($(this).find("img").attr('src') === 'images/activity/comment.png') {
            $(this).find("img").attr('src', "images/activity/comment_selected.png");
        }
        else {
            $(this).find("img").attr('src', "images/activity/comment.png");
        }
        $(this).parents(".container").children(".writeCom").toggle(200);
    });
    $(".submit_comment").live("click", function () {
        $(this).parents(".writeCom").children(".inputcontainer").children(".input_comment").val("");
        $(this).parents(".container").children(".btnDiv").children(".comment").find("img").attr('src', "images/activity/comment.png");
        $(this).parents(".writeCom").hide(200);
    });

    $("input").focus(function () {
        $(this).parent().css("border-color", "#bbbbff");
        $(this).parent().css("box-shadow", "0 0 4px #bbbbff");
    });
    $("input").blur(function () {
        $(this).parent().css("border-color", "#cccccc");
        $(this).parent().css("box-shadow", "none");
    });
});