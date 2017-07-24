$(document).ready(function () {

    /*
    *
    * 函数定义
    *
    * */

    // 如果是朋友，显示删除按钮
    function isFriend() {
        $("#addBtn").hide();
        $("#deleteBtn").show();
    }

    // 如果是陌生人，显示添加按钮
    function isStranger() {
        $("#deleteBtn").hide();
        $("#addBtn").show();
    }
    //isFriend();
    //isStranger();

    function isMe() {
        $("#deleteBtn").hide();
        $("#addBtn").hide();
    }

    // 获取前一个页面的数据
    function getArgs() {
        var args = {};
        var query = location.search.substring(1);
        var pairs = query.split("&");
        for(var i = 0; i < pairs.length; i++) {
            var pos = pairs[i].indexOf('=');
            if (pos === -1)
                continue;
            var argName = pairs[i].substring(0, pos);
            var value = pairs[i].substring(pos + 1);
            value = decodeURIComponent(value);
            args[argName] = value;
        }
        return args;
    }

    // 加载头像
    function getHeadImg(owner) {
        if(owner.userpic !== null && owner.userpic !== "")
            return owner.userpic;
        var picPath = "pic/userpic/default.jpg";
        if(owner.sex === "male") {
            picPath = "pic/userpic/male.jpg";
        } else if (owner.sex === "female") {
            picPath = "pic/userpic/female.jpeg";
        }
        return picPath;
    }

    // 将null转化为空字符串
    function toStr(str) {
        if(str === null)
            str = "";
        return str;
    }



    /*
    *
    * 页面加载
    *
    * */

    var args = getArgs();
    viewedUser = args.user;



    // 获取被浏览者信息
    $.ajax({
        type: "GET",
        url: "self",
        dataType: "json",
        data: {
            userid: viewedUser,
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
            var signature = owner.signature;
            var userHeadImg = getHeadImg(owner);
            var nickname = toStr(owner.nickname);
            var realname = toStr(owner.realname);
            var school  = toStr(owner.school);
            var department = toStr(owner.department);
            var grade = toStr(owner.grade);
            var sex = toStr(owner.sex);
            var birthday = owner.birthday === null ? "" : toStr((new Date(parseInt(owner.birthday))).toDateString());
            var hometown = toStr(owner.hometown);
            var contactway = toStr(owner.contactway);
            var description = toStr(owner.description);


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
            if(intet !== null) {
                var interests = "music:<br />" + toStr(intet.music) + "<br />" +
                    "sports:<br />" + toStr(intet.sport) + "<br />" +
                    "book:<br />" + toStr(intet.book) + "<br />" +
                    "movie:<br />" + toStr(intet.movie) + "<br />" +
                    "game:<br />" + toStr(intet.game) + "<br />"; //+
                //"others:<br />" + toStr(intet.other);
                $(".self_bottom_left .self_bottom_item p").html(interests);
            }


        }

    });


    /*
    *
    *  按钮加载与绑定
    *
    * */

    // 按钮加载
    $.ajax({
        type: "GET",
        url: "friend/checkAsk",
        dataType: "json",
        data: {
            receiver: viewedUser
        },
        async: false,
        timeout: 5000,
        cache: false,
        beforeSend: function () {

        },
        error: function () {

        },
        success: function (res) {
            //alert(res.reason);
            if(res.result === "ERROR" && res.reason === "E_FRIEND_ALREADY_ADDED") {
                isFriend();
                //alert("friend");
            } else if(res.result === "ERROR" && res.reason === "E_SAMEONE") {
                isMe();
                //alert("me");
            } else {
                isStranger();
                //alert("stranger");
            }

        }
    });

    // 绑定添加好友按钮
    $("#addBtn").live("click", function () {
        //alert("start sending");
        $.ajax({
            type: "GET",
            //服务端url
            url: "friend/checkAsk",
            dataType: "json",
            data: {
                "sender": null,
                "receiver": viewedUser
            },
            timeout: 5000,
            cache: false,
            beforeSend: function () {

            },
            error: function () {
                alert("确认权限网络请求失败");
            },
            success: function (res) {
                //alert(res.result);
                if (res.result === "SUCCESS") {
                    var veriContent = "add friend request";
                    //
                    // 向目标用户发送好友申请
                    //
                    $.ajax({
                        type: "POST",
                        //服务端url
                        url: "friend/sendAsk",
                        dataType: "json",
                        data: {
                            "sender": null,
                            "receiver": viewedUser,
                            "content": veriContent
                        },
                        timeout: 5000,
                        cache: false,
                        beforeSend: function () {
                        },
                        error: function () {
                            alert("发送申请网络请求失败!");
                        },
                        success: function (res) {
                            //alert(res.result);
                            if(res.result === "SUCCESS") {
                                alert("发送好友请求成功");
                            } else {
                                if(res.reason === "E_ALREADY_SENDED") {
                                    alert("您已发送过好友申请，请耐心等待对方回复")
                                } else if (res.reason === "E_SEND_ERROR") {
                                    alert("好友申请发送失败");
                                }
                            }
                        }

                    }); // 发送好友申请结束

                } else {
                    if(res.reason === "E_FRIEND_ALREADY_ADDED") {
                        alert("对方已是您的好友");
                    } else if(res.reason === "E_YOU_ARE_IN_BLACKLIST") {
                        alert("您被加入黑名单，无法提出好友申请");
                    }
                }
            }
        });
    });


    // 绑定删除好友按钮
    $("#deleteBtn").live("click", function () {
            //alert("start sending");
            $.ajax({
                type: "GET",
                //服务端url
                url: "deleteFriend",
                dataType: "json",
                data: {
                    "friendid": viewedUser
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },
                error: function () {
                    alert("确认权限网络请求失败");
                },
                success: function (res) {
                    alert(res.result);
                }

            });

    });


});