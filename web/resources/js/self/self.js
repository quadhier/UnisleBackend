$(document).ready(function () {

    // 转换到个人显示页面呢
    $("#control_self").click(function () {
        $(".controlBar div").css("background-color", "rgba(204,204,204,0.8)").hover(function () {
            $(this).css("background-color", "rgba(255,255,255,0.57)");
        }, function () {
            $(this).css("background-color", "rgba(204,204,204,0.8)");
        });
        $(this).css("background-color", "rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#self_concrete").show();
    });

    // 转换到个人编辑页面
    $("#control_edit").click(function () {
        $(".controlBar div").css("background-color", "rgba(204,204,204,0.8)").hover(function () {
            $(this).css("background-color", "rgba(255,255,255,0.57)");
        }, function () {
            $(this).css("background-color", "rgba(204,204,204,0.8)");
        });
        $(this).css("background-color", "rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#edit_concrete").show();
    });

    // 转换到隐私页面
    $("#control_privacy").click(function () {
        $(".controlBar div").css("background-color", "rgba(204,204,204,0.8)").hover(function () {
            $(this).css("background-color", "rgba(255,255,255,0.57)");
        }, function () {
            $(this).css("background-color", "rgba(204,204,204,0.8)");
        });
        $(this).css("background-color", "rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#privacy_concrete").show();
    });

    // 转换到相册页面
    $("#control_album").click(function () {
        $(".controlBar div").css("background-color", "rgba(204,204,204,0.8)").hover(function () {
            $(this).css("background-color", "rgba(255,255,255,0.57)");
        }, function () {
            $(this).css("background-color", "rgba(204,204,204,0.8)");
        });
        $(this).css("background-color", "rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#album_concrete").show();

        // 翻页效果
        $("#itemContainer").empty();

        for (var i = 0; i < 50; i++) {
            $("#itemContainer").append("<div><img src='images/img (1).jpg' alt='image'><img src='images/img (2).jpg' alt='image'><img src='images/img (3).jpg' alt='image'><img src='images/img (4).jpg' alt='image'><img src='images/img (5).jpg' alt='image'></div>");
            $("#itemContainer").append("<div><img src='images/img (6).jpg' alt='image'><img src='images/img (7).jpg' alt='image'><img src='images/img (8).jpg' alt='image'><img src='images/img (9).jpg' alt='image'><img src='images/img (10).jpg' alt='image'></div>");
        }
        $(function () {
            $("div.holder").jPages({
                containerID: "itemContainer",
                previous: "←",
                next: "→",
                perPage: 5
            });
        });
    });

    $("input").focus(function () {
        $(this).parent().css("border-color", "#bbbbff");
        $(this).parent().css("box-shadow", "0 0 4px #bbbbff");
    });
    $("input").blur(function () {
        $(this).parent().css("border-color", "#cccccc");
        $(this).parent().css("box-shadow", "none");
    });

    $("textarea").focus(function () {
        $(this).parent().css("border-color", "#bbbbff");
        $(this).parent().css("box-shadow", "0 0 4px #bbbbff");
    });
    $("textarea").blur(function () {
        $(this).parent().css("border-color", "#cccccc");
        $(this).parent().css("box-shadow", "none");
    });

    $("select").focus(function () {
        $(this).parent().css("border-color", "#bbbbff");
        $(this).parent().css("box-shadow", "0 0 4px #bbbbff");
    });
    $("select").blur(function () {
        $(this).parent().css("border-color", "#cccccc");
        $(this).parent().css("box-shadow", "none");
    });

    $("#activityVisibility_2").click(function () {
        var className1 = ($("#activityVisibility").attr('class') === "close1") ? "open1" : "close1";
        var className2 = ($("#activityVisibility_2").attr('class') === "close2") ? "open2" : "close2";
        $("#activityVisibility").attr('class', className1);
        $("#activityVisibility_2").attr('class', className2);

        if (className1 === 'open1')
            alert("open");
        else
            alert("close");
    });

    $("#receiveInfo_2").click(function () {
        var className1 = ($("#receiveInfo").attr('class') === "close1") ? "open1" : "close1";
        var className2 = ($("#receiveInfo_2").attr('class') === "close2") ? "open2" : "close2";
        $("#receiveInfo").attr('class', className1);
        $("#receiveInfo_2").attr('class', className2);

        if (className1 === 'open1')
            alert("open");
        else
            alert("close");
    })

});