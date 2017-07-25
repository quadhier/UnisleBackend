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
        var commenterids = actAndCom.commenterids;
        var publishtimes = actAndCom.publishtimes;
        var pro = actAndCom.pro;


        // 设置点赞图标的样式
        var proImg;
        if (pro === true) {
            proImg = "<img class='goodImg' src='images/activity/good_selected.png'>";
        } else {
            proImg = "<img class='goodImg' src='images/activity/good.png'>";
        }

        // 组成评论的展示字符串
        var commentDisplay = "";
        for (i in comments) {
            commentDisplay = commentDisplay +
                "<div class='commentDiv'>" +
                "<div class ='" + commenterids[i] + "'style='display:none'></div>" +
                "<div class='" + publishtimes[i] + "' style='display:none'></div>" +
                "<div class='comment_x_img'><img src='images/activity/cross.png' style='width:100%;height:100%'/></div>" +
                "<p class='pCommenter'>" + commenters[i] + "</p>" +
                "<p class='pComment'>" + comments[i].content + "</p>" +
                "</div>";
        }
        commentDisplay = commentDisplay + "";


        // 组成图片展示的字符串
        var picDisplay = "";
        if (activity.attachment !== null) {
            picDisplay = "<img class='activitypic' src='" + activity.attachment + "'/>";
        }


        // 日历时间转化为本地时间字符串
        var pubDate = new Date(activity.publicdatetime);
        var strTime = pubDate.toLocaleString();

        $("#actContainer").append(
            "<div id='" + activity.activityid + "' class='container'>" +
            "<div class='" + activity.publisher + "' style='display:none'>1</div>" +
            "<hr style='top: 30px'/>" +
            "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
            "<div class='subRight'><p class='pSmall'>" + strTime + "</p></div>" +
            "<div class='divcenter'>" +
            "<p class='pContent'>" + activity.content + "</p>" +
            picDisplay +
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
            "<div class='activity_x_img'><img src='images/activity/cross.png' style='width:100%;height:100%'/></div>" +
            "</div>");
    }


    // 产生随机图片后缀，强制图片更新
    function randomTail() {
        return "?r=" + Math.random();
    }


    // 储存用户名
    var userName;
    // 储存用户id
    var userid;
    // 动态可见性
    var actView = "friend";


    /*
     *
     * 加载个人信息
     *
     * */

    $.ajax({
        type: "GET",
        url: "self",
        dataType: "json",
        data: {
            "withInst": "withInst"
        },
        async: false,
        timeout: 5000,
        cache: false,
        beforeSend: function () {

        },

        error: function () {

        },

        success: function (res) {
            if (res.result === "LOGINERROR")
                window.location.replace("login.html");
            if (res.result === "ERROR") {
                alert(res.reason);
                window.location.replace("login.html");
            }


            var user = res.data.user;
            var sex = user.sex;
            var userheadImg = user.userpic;
            var nickname = user.nickname === null ? "未知" : user.nickname;
            var school = user.school === null ? "未知" : user.school;
            var department = user.department === null ? "未知" : user.department;
            var grade = user.grade === null ? "未知" : user.grade;

            userName = user.nickname;
            userid = user.userid;

            var sexImgNode = $("#sexImg")[0];
            var userheadImgNode = $("#userheadImg")[0];

            if (user.sex === 'male') {
                sexImgNode.src = "images/activity/male.png";
                userheadImgNode.src = "pic/userpic/male.jpg"
            } else {
                sexImgNode.src = "images/activity/female.png";
                userheadImgNode.src = "pic/userpic/female.jpeg";
            }
            if (userheadImg !== null && userheadImg !== "") {
                userheadImgNode.src = userheadImg + randomTail();
            } else {
                userheadImgNode.src = "pic/userpic/default.jpg";
                if (sex === "male") {
                    userheadImgNode.src = "pic/userpic/male.jpg";
                } else if (sex === "female") {
                    userheadImgNode.src = "pic/userpic/female.jpeg"
                }
            }

            $("#nickname").text("昵称: " + nickname);
            $("#schooldepartment").html("学校: " + school + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;院系: " + department);
            $("#grade").text("年级: " + grade);
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
            "view": actView
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
                "view": actView
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
        //!!!!!!
        //从主页进入搜索页面时有bug，会导致搜索页面滚动条也触发加载动态事件，故使用全局变量加以控制
        if(!onActivity)
            return;

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
                    "view": actView
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
                        //alert(res.reason);
                    }
                }
            });

        }
    });


    /*
     *
     * 提交表格，发布动态
     *
     * */

    $("#activityForm").ajaxForm({
        type: "POST",
        url: "activity",
        dataType: "json",
        data: {},
        forceSync: true,
        timeout: 5000,
        cache: false,
        beforeSubmit: function (arr, $form, option) {
            if (arr[0].value === "") {
                alert("输入内容不能为空");
                return false;
            }
            if (arr[1].value === "") {
                // !!! 如果文件为空，则删除该元素
                // !!! 否则spring会将其作为字符串处理，进而报错
                arr.splice(1, 1);
            }
        },
        error: function () {

        },
        success: function (res) {

            if (res.result === "SUCCESS") {

                var content = $("#release_input").val();
                var nowDate = new Date();
                var activityid = res.data.activityid;

                var picDisplay = "";
                if (res.data.actpic !== null) {
                    picDisplay = "<img class='activitypic' src='" + res.data.actpic + "'/>";
                }

                $("#actContainer").prepend(
                    "<div id='" + activityid + "' class='container'>" +
                    "<div class='" + userid + "' style='display:none'></div>" +
                    "<hr style='top: 30px'/>" +
                    "<div class='subLeft'><p class='pTitle'>" + userName + "</p></div>" +
                    "<div class='subRight'><p class='pSmall'>" + nowDate.toLocaleString() + "</p></div>" +
                    "<div class='divcenter'>" +
                    "<p class='pContent'>" + content + "</p>" +
                    picDisplay +
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
                    "<div class='activity_x_img'><img src='images/activity/cross.png' style='width:100%;height:100%'/></div>" +
                    "</div>");
                $("#release_input").val("");

            } else {
                alert(res.reason);
            }
        }
    });


    /*
     *
     * 为点赞图标绑定事件
     *
     * */

    $(".good").live("click", function () {
        var activityid = $(this).parents(".container").attr("id");
        var publisher = $(this).parents(".container").children().eq(0).attr('class');
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
                    if (res.result === "SUCCESS") {
                        var pdiv = "#" + activityid;
                        var proNum = 1 + parseInt($(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text());
                        $(pdiv).children(".btnDiv").children(".pGoodNum").children("span").text(proNum);
                    } else {
                        alert(res.reason);
                    }
                    //发送通知：动态点赞
                    //this在此处取父元素是取不到的，必须在上边取发布者id.
                    $.ajax({
                        type: 'POST',
                        url: 'notice/sendNotice',
                        dataType: 'json',
                        data: {
                            'receiver': publisher,
                            'type': 'activityproed',
                            'content': '赞！'
                        },
                        timeout: 10000
                    });
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

                    if (res.result === "SUCCESS") {
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
            var publisher = $(this).parents(".container").children().eq(0).attr('class');
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
                    //发送通知：动态评论
                    $.ajax({
                        type: 'POST',
                        url: 'notice/sendNotice',
                        dataType: 'json',
                        data: {
                            'receiver': publisher,
                            'type': 'activitycommented',
                            'content': input
                        },
                        timeout: 10000
                    });

                    var pdiv = "#" + activityid;
                    // 评论区可能不存在
                    // 将评论加在评论框的前面
                    $(pdiv).children(".writeCom:first").before("<div class='commentDiv'>" +
                        "<div class ='" + userid + "'style='display:none'></div>" +
                        "<div class='" + res.data + "' style='display:none'></div>" +
                        "<div class='comment_x_img'><img src='images/activity/cross.png' style='width:100%;height:100%'/></div>" +
                        "<p class='pCommenter'>" + userName + "</p>" +
                        "<p class='pComment'>" + input + "</p>" +
                        "</div>");
                    inputEle.val("");
                    alert(input);
                    inputEle.parents(".container").children(".btnDiv").children(".comment").click();

                }
            });
        }
    });


    // 为删除动态键绑定事件
    $(".container .activity_x_img").live("click", function () {
        var publisherid = $(this).parents(".container").children(0).attr("class");
        var activityid = $(this).parents(".container").attr("id");
        var targetDiv = $(this).parents(".container");

        if (publisherid === userid) {

            $.ajax({
                type: "POST",
                url: "activity/delete",
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
                    if (res.result === "SUCCESS") {
                        alert("删除成功");
                        targetDiv.remove();
                    } else {
                        alert(res.reason);
                    }
                }
            });
        }

    });


    // 为删除评论键绑定事件
    $(".container .commentDiv .comment_x_img").live("click", function () {

        var activityid = $(this).parents(".container").attr("id");
        var commenterid = $(this).parents(".commentDiv").children(":first").attr("class");
        var publishtime = $(this).parents(".commentDiv").children(":first").next().attr("class");
        var targetDiv = $(this).parents(".commentDiv");

        if (commenterid === userid) {

            $.ajax({
                type: "POST",
                url: "activity/comment/delete",
                dataType: "json",
                data: {
                    "activityid": activityid,
                    "publisher": commenterid,
                    "publishdatetime": parseInt(publishtime)
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },
                error: function () {

                },
                success: function (res) {
                    if (res.result === "SUCCESS") {
                        alert("删除评论成功");
                        targetDiv.remove();
                    } else {
                        alert(res.reason);
                    }
                }
            });

        }

    });


    /*
    *
    * 为群组公告部分绑定按键
    *
    * */
    {
        var articleEntityList = null;
        $.ajax({
            type: "GET",
            url: "forum/getMostRead",
            dataType: "json",
            data: {
                "maxnumber":5
            },
            timeout: 5000,
            async:false,
            success:function (res) {
                if(res.result != "SUCCESS")
                    alert("获取最热文章失败");
                articleEntityList = res.data;
            }
        });
        for(var i =0;i<articleEntityList.length;i++){
            $('.messageDiv',$('#floatRight')).append(
            '<div class="message_title" data-articleid = "'+articleEntityList[i].articleid+'" data-authorid = "'+articleEntityList[i].author+'" data-content = "'+articleEntityList[i].content+'">'+
                '<span>'+'【访问量:'+articleEntityList[i].viewtimes+'】 '+articleEntityList[i].title+'</span>'+
            '</div>'
            );
        }

        $(".message_content").hide();

        $(".message_title").hover(function () {
            $(this).css({
                "opacity": "0.7"
            });
        }, function () {
            $(this).css({
                "opacity": "1"
            });
        });

        $(".message_title").click(function () {
            var content = $(this).attr('data-content');
            content = '文章摘要:<br>'+content;

            if(content.length>50){
                content = content.substr(0,50)+'...';
            }
            content = content + '<br>(更多精彩请访问论坛)'

            $(".message_content p").html(content);
            $("#headline").slideUp(500, function () {
                $(".message_title").slideUp(500, function () {
                    $(".message_content").slideDown(500);
                });
            });
            $(".message_content").hover(function () {
            }, function () {

                $(".message_content").slideUp(500, function () {
                    $("#headline").slideDown(500, function () {
                        $(".message_title").slideDown(500);
                    });
                });

            });
        });

    }




    // 输入框被选中事件
    $("input").focus(function () {
        $(this).parent().css("border-color", "#bbbbff");
        $(this).parent().css("box-shadow", "0 0 4px #bbbbff");
    });
    $("input").blur(function () {
        $(this).parent().css("border-color", "#cccccc");
        $(this).parent().css("box-shadow", "none");
    });

    // 添加图片按钮特效
    $("#addImgBtn").change(function () {
        if ($(this).children("input").val().length === 0)
            $(this).css("opacity", "0.5");
        else
            $(this).css("opacity", "1");
    });

    // 删除动态的叉叉的特效
    $(".container").live("mouseenter", function () {

        publisherid = $(this).children(":first").attr("class");
        if (publisherid === userid)
            $(this).children(".activity_x_img").show();

    }).live("mouseleave", function () {

        $(this).children(".activity_x_img").hide();

    });

    // 删除评论的叉叉的特效
    $(".container .commentDiv").live("mouseenter", function () {

        commenterid = $(this).children(":first").attr("class");
        if (commenterid === userid)
            $(this).children(".comment_x_img").show();

    }).live("mouseleave", function () {

        $(this).children(".comment_x_img").hide();

    });


});