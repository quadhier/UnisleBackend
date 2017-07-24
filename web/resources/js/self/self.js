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
    });


    /*
     *
     * 函数定义
     *
     * */

    // 加载头像
    function getHeadImg(owner) {
        if (owner.userpic !== null && owner.userpic !== "")
            return owner.userpic;
        var picPath = "pic/userpic/default.jpg";
        if (owner.sex === "male") {
            picPath = "pic/userpic/male.jpg";
        } else if (owner.sex === "female") {
            picPath = "pic/userpic/female.jpeg";
        }
        return picPath;
    }

    // 将null转化为空字符串
    function toStr(str) {
        if (str === null)
            str = "";
        return str;
    }


    /*
     *
     * 个人信息加载
     *
     * */

    // 全局变量定义


    var owner;
    var ownerid;
    var signature;
    var userHeadImg;
    var nickname;
    var realname;
    var school;
    var department;
    var grade;
    var sex;
    var birthday;
    var hometown;
    var contactway;
    var description;
    var intet;

    $.ajax({
        type: "GET",
        url: "self",
        dataType: "json",
        data: {
            withInst: "withInst"
        },
        async: false,
        timeout: 5000,
        cache: false,
        beforeSend: function () {

        },
        error: function () {

        },
        success: function (res) {

            var owner = res.data.user;
            ownerid = owner.userid;
            var signature = owner.signature;
            var userHeadImg = getHeadImg(owner);
            var nickname = toStr(owner.nickname);
            var realname = toStr(owner.realname);
            var school = toStr(owner.school);
            var department = toStr(owner.department);
            var grade = toStr(owner.grade);
            var sex = toStr(owner.sex);
            var birthday = owner.birthday === null ? "" : toStr((new Date(parseInt(owner.birthday))).toDateString());
            var hometown = toStr(owner.hometown);
            var contactway = toStr(owner.contactway);
            var description = toStr(owner.description);

            //
            // 填充个人页面中的信息
            //

            // 个性签名
            $(".self_sign span").text(toStr(signature));

            // 设置头像
            $(".self_headImg img")[0].src = userHeadImg;

            // 设置个人信息
            $(".self_top_text p:eq(0)").text(nickname);
            $(".self_top_text p:eq(1)").text(school);

            $("#drealname").text(realname);
            $("#dschool").text(school);
            $("#dmajor").text(department);
            $("#dgrade").text(grade);
            $("#dsex").text(sex);
            $("#dbirthday").text(birthday);
            $("#darea").text(hometown);
            $("#dcontact").text(contactway);
            $(".self_bottom_right .self_bottom_item p").text(description);


            // 兴趣爱好
            $(".self_bottom_left .self_bottom_item p").html("");
            var intet = res.data.interest;
            if (intet !== null) {
                var interests = "music:<br />" + toStr(intet.music) + "<br />" +
                    "sports:<br />" + toStr(intet.sport) + "<br />" +
                    "book:<br />" + toStr(intet.book) + "<br />" +
                    "movie:<br />" + toStr(intet.movie) + "<br />" +
                    "game:<br />" + toStr(intet.game) + "<br />"; //+
                //"others:<br />" + toStr(intet.other);
                $(".self_bottom_left .self_bottom_item p").html(interests);
            }


            //
            // 填充编辑页面中的信息
            //


            if(realname !== "")
                $("#irealname").attr("placeholder", realname);
            if(nickname !== "")
                $("#inickname").attr("placeholder", nickname);
            if(school !== "")
                $("#ischool").attr("placeholder", school);
            if(department !== "")
                $("#idepartment").attr("placeholder", department);
            if(grade !== "")
                $("#igrade").attr("placeholder", grade);
            if(contactway !== "")
                $("#icontactway").attr("placeholder", contactway);
            if(hometown !== "")
                $("#ihometown").attr("placeholder", hometown);
            if(sex !== "")
                $("#isex").attr("value", "male");
            if(birthday !== "")
                $("#ibirthday").attr("value", "2012-02-02");
            if(intet.music !== "")
                $("#imusic").attr("placeholder", intet.music);
            if(intet.sport)
                $("#isport").attr("placeholder", intet.sport);
            if(intet.book !== "")
                $("#ibook").attr("placeholder", intet.book);
            if(intet.movie)
                $("#imovie").attr("placeholder", intet.movie);
            if(intet.game)
                $("#igame").attr("placeholder", intet.game);
            if(intet.other)
                $("#iother").attr("placeholder", intet.other);



        }

    });


    /*
     *
     * 按键绑定
     *
     * */

    // 个人页面中的更改头像按钮
    function uploadHandle() {


        $("#picForm").ajaxSubmit({

            type: "POST",
            url: "self/userpic",
            dataType: "json",
            data: {},
            forceSync: true,
            timeout: 5000,
            cache: false,
            beforeSubmit: function (arr, $form, option) {
                // form中的input标签如果没有name属性则不会出现在arr中
                if(arr[0].value === "") {
                    return false;
                }
            },
            error: function () {

            },
            success: function (res) {
                // 更新头像，加入随机数使得图片即时更新
                $("#userpic")[0].src = "pic/userpic/" + ownerid + "?r=" + Math.random();

            }
        });

    }

    var pic = $("#picForm input")[0];
    pic.addEventListener("change", uploadHandle, false);


    // 编辑页面中的确认按钮，取消按钮






    // 更改动态的可见性，是否接收通知，修改密码






    // 黑名单






});