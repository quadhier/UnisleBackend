$(document).ready(function () {


    /*
    *
    * 函数定义
    *
    * */

    // 添加动态
    function appendCard(actAndCom) {

        var userName = actAndCom.userName;
        var activity = actAndCom.activity;
        var comments = actAndCom.comments;
        var commenters = actAndCom.commenters;
        var pro = actAndCom.pro;


        // 设置点赞图标的样式
        var proImg;
        if(pro === true) {
            proImg = "<img class='goodImg' src='images/activity/good_selected.png'>";
        } else {
            proImg = "<img class='goodImg' src='images/activity/good.png'>";
        }

        // 组成评论的展示字符串
        var commentDisplay = "<div class='commentDiv'>";
        for (i in comments) {
            commentDisplay = commentDisplay +
                "<p class='pCommenter'>" + commenters[i] + "</p>" +
                "<p class='pComment'>" + comments[i].content + "</p>";
        }
        commentDisplay = commentDisplay + "</div>";

        // 日历时间转化为本地时间字符串
        var pubDate = new Date(activity.publicdatetime);
        var strTime = pubDate.toLocaleString();

        $("#actContainer").append(
            "<div id='" + activity.activityid + "' class='container'>" +
            "<hr style='top: 30px'/>" +
            "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
            "<div class='subRight'><p class='pSmall'>" + strTime + "</p></div>" +
            "<div class='divcenter'>" +
            "<p class='pContent'>" + activity.content + "</p>" +
            "</div>" +
            "<hr/>" +
            "<div class='btnDiv'>" +
            "<div class='comment'><img src='images/activity/comment.png'></div>" +
            "<div class='good'>" + proImg + "</div>" +
            "<p class='pGoodNum'><span>" + activity.pros + "</span>人觉得很赞</p>" +
            "</div>" +
            commentDisplay +
            "<div class='writeCom'>" +
            "<div class='inputcontainer'><input type='text' class='input_comment'></div>" +
            "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>" +
            "</div>" +
            "</div>");
    }


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
            if(res.result === "LOGINERROR")
                window.location.reload("login.html");
            if(res.result === "ERROR") {
                alert(res.reason);
                window.location.reload("login.html");
            }

            var user = res.data;
            var userheadImg = user.userpic;
            var nickname = user.nickname === null ? "未知" : user.nickname;
            var school = user.school === null ? "未知" : user.school;
            var department = user.department === null ? "未知" : user.department;
            var grade = user.grade === null ? "未知" : user.grade;

            userName = user.nickname;

            if (user.sex === 'male') {
                $("#sexImg")[0].src = "images/activity/male.png";
                $("#userheadImg")[0].src = "pic/userpic/male.jpg"
            } else {
                $("#sexImg")[0].src = "images/activity/female.png";
                $("#userheadImg")[0].src = "pic/userpic/female.jpeg";
            }
            if(userheadImg !== null && userheadImg !== "") {
                $("#userheadImg")[0].src = userheadImg;
            }

            $("#nickname").text("昵称: " + nickname);
            $("#schooldepartment").html("学校: " + school + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;院系: " + department);
            $("#grade").text("年级: " + grade);
        }

    });

    /*
     *
     * 绑定发布动态的事件
     *
     * */

    $("#releaseBtn").click(function () {
        if ($("#release_input").val() !== "") {
            $.ajax({
                type: "POST",
                url: "activity",
                dataType: "json",
                data:{
                    "content": $("#release_input").val()
                },
                //async:false,
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },

                error: function () {

                },

                success: function (res) {

                    if(res.result === "SUCCESS") {

                        var content = $("#release_input").val();
                        var nowDate = new Date();
                        var activityid = res.data;

                        $("#actContainer").prepend(
                            "<div id='" + activityid + "' class='container'>" +
                            "<hr style='top: 30px'/>" +
                            "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
                            "<div class='subRight'><p class='pSmall'>" + nowDate.toLocaleString() + "</p></div>" +
                            "<div class='divcenter'>" +
                            "<p class='pContent'>" + content + "</p>" +
                            "</div>" +
                            "<hr/>" +
                            "<div class='btnDiv'>" +
                            "<div class='comment'><img src='images/activity/comment.png'></div>" +
                            "<div class='good'><img class='goodImg' src='images/activity/good.png'></div>" +
                            "<p class='pGoodNum'><span>0</span>人觉得很赞</p>" +
                            "</div>" +
                            "<div class='commentDiv'></div>" +
                            "<div class='writeCom'>" +
                            "<div class='inputcontainer'><input type='text' class='input_comment'></div>" +
                            "<div class='submitcontainer'><button class='submit_comment'>评论</button></div>" +
                            "</div>" +
                            "</div>");
                            $("#release_input").val("");

                    } else {
                        alert(res.reason);
                    }
                }
            });


        } else {
            alert("输入内容不能为空！");
        }
    });


    /*
     *
     * 加载动态并设置滚动时加载
     *
     * */
    $.ajax({
        type: "GET",
        url: "activity/all/isfirst",
        dataType: "json",
        data: {
            "lastTime": null,
            "view": "all"
        },
        async: false,                           //可能会使得效率下降
        timeout: 5000,
        cache: false,
        beforeSend: function () {

        },
        error: function () {

        },
        success: function (res) {

            if (res.result === "SUCCESS") {
                appendCard(res.data);
            } else {
                alert(res.reason);
            }
        }
    });


    for (var i = 0; i < 4; i++) {

        $.ajax({
            type: "GET",
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

            },
            error: function () {

            },
            success: function (res) {

                if (res.result === "SUCCESS") {
                    appendCard(res.data);
                } else {
                    alert(res.reason);
                }
            }
        });
    }


    //var hasMore = true;
    $(window).scroll(function () {

        var scrollTop = $(this).scrollTop();
        var scrollHeight = document.body.scrollHeight;
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

                },
                error: function () {

                },
                success: function (res) {

                    if(res.result === "SUCCESS") {
                        appendCard(res.data);
                    } else {
                        alert(res.reason);
                    }
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
        //alert(activityid);
        // 如果未点赞，再次点击则点赞
        // 向后台同步数据，同时使显示的的点赞数加一
        if ($(this).find("img").attr('src') === 'images/activity/good.png') {
            $(this).find("img").attr('src', "images/activity/good_selected.png");
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

                },
                error: function () {

                },
                success: function (res) {
                    if(res.result === "SUCCESS") {
                        var pdiv = "#" + activityid;
                        var proNum = 1 + parseInt($(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text());
                        $(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text(proNum);
                    } else {
                        alert(res.reason);
                    }

                }
            });

        }
        // 如果已经点赞，再次点击则取消点赞
        // 向后台同步数据，同时使显示的的点赞数减一
        else {
            $(this).find("img").attr('src', "images/activity/good.png");
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

                },
                error: function () {

                },
                success: function (res) {

                    if(res.result === "SUCCESS") {
                        var pdiv = "#" + activityid;
                        var proNum = parseInt($(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text()) - 1;
                        $(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text(proNum);
                    } else {
                        alert(res.reason);
                    }
                }
            });

        }
    });



    /*
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
     *  为评论按钮绑定事件
     *
     * */

    $(".submit_comment").live("click", function () {


        var inputEle = $(this).parents(".submitcontainer").siblings(".inputcontainer").children("input");
        var input = inputEle.val();
        if (input !== "") {

            var activityid = $(this).parents(".container").attr("id");
            $.ajax({
                type: "POST",
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

                    var pdiv = "#" + activityid;
                    $(pdiv).children(".commentDiv").append("<p class='pCommenter'>" + userName + "</p>" +
                        "<p class='pComment'>" + input + "</p>");
                    inputEle.val("");
                    inputEle.parents(".container").children(".btnDiv").children(".comment").click();

                }
            });
        }
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