$(document).ready(function () {


    $("input").focus(function () {
        $(this).parent().css("border-color", "#bbbbff");
        $(this).parent().css("box-shadow", "0 0 4px #bbbbff");
    });
    $("input").blur(function () {
        $(this).parent().css("border-color", "#cccccc");
        $(this).parent().css("box-shadow", "none");
    });

    /*
    *
    * 函数定义
    *
    * */
    function strDisplay(str) {
        if(str === null || str.length === 0) {
            return "";
        }
        return str;
    }



    /*
    *
    * 初始化朋友信息
    *
    * */
    $.ajax({
        type: "GET",
        url: "friend/getFriend",
        dataType: "json",
        data: {
            "userid": null
        },
        timeout: 5000,
        cache: false,
        beforeSend: function () {

        },
        error: function () {

        },
        success: function (res) {

            if(res.result === "SUCCESS") {
                $.each(res.data, function (index, user) {

                    var school = strDisplay(user.school);
                    var department = strDisplay(user.department);
                    var grade = strDisplay(user.grade) === "" ? "" : strDisplay(user.grade) + "级";
                    var sex = user.sex;
                    var userHeadImg = user.userpic;
                    if(userHeadImg === null || userHeadImg.length <= 0) {
                        if(sex === "male") {
                            userHeadImg = "pic/userpic/male.jpg";
                        } else if(sex === "female") {
                            userHeadImg = "pic/userpic/female.jpeg";
                        } else {
                            userHeadImg = "pic/userpic/default.jpg"
                        }
                    }

                    $(".myfriend_container .friendPanel").append("<div class='friend_container'>" +
                        "<div class='headimg'><img src='" + userHeadImg + "'width='105' height='70'></div>" +
                        "<div class='infoDiv'>" +
                        "<p class='pSchool'>" + user.nickname + "</p>" +
                        "<p class='pInfo'>" + school + " " + department + "<br>" + grade + "</p>" +
                        "</div>" +
                        "</div>");
                });
            } else if(res.reason === "LOGINERROR") {
                window.location.replace("message.html");
            } else {
                alert(res.reason);
            }
        }
    });



    /*
     *
     * 为发送好友申请按钮绑定事件
     * 步骤如下：（验证邮箱格式有效性，）查找邮箱对应的用户，查看发送好友申请的权限，发送申请
     *
     * */
    $("#findfriend_btn").click(function () {
        var recAccount = $("#findfriend_input").val();
        if (recAccount.indexOf("@") === -1) {
            alert("请输入有效的用户邮箱");
            $("#findfriend_input").val("");
        } else {
            //
            // 先通过邮箱找到目标用户的id
            //
            $.ajax({
                type: "GET",
                url: "friend/searchUser",
                dataType: "json",
                data: {
                    "mailornickname": recAccount
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {
                },
                error: function () {
                    alert("检查用户网络请求失败!");
                },
                success: function (res) {
                    if (res.result === "ERROR") {
                        alert("未发现符合要求的用户");
                        $("#findfriend_btn").val("");
                    } else {
                        var recUserid = res.data[0].userid;
                        alert(recUserid);
                        //
                        // 确认能否向目标用户发送好友申请
                        //
                        $.ajax({
                            type: "GET",
                            //服务端url
                            url: "friend/checkAsk",
                            dataType: "json",
                            data: {
                                "sender": null,
                                "receiver": recUserid
                            },
                            timeout: 5000,
                            cache: false,
                            beforeSend: function () {

                            },
                            error: function () {
                                alert("确认权限网络请求失败");
                            },
                            success: function (res) {
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
                                            "receiver": recUserid,
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
                        }); // 确认发送权限结束
                    }
                }
            }); // 寻找目标用户id结束

        } // 大概确认邮箱有效结束

    }); // 为发送申请按钮绑定事件结束


    /*
    *
    * 为发送群组申请按钮绑定事件
    *
    * */

    $("#findorg_btn").click(function () {
        var groupid = $("#findorg_input").val();
        //
        // 查看该组是否存在
        //
        $.ajax({
            type: "GET",
            url: "group",
            dataType: "json",
            data: {
                "groupid": groupid
            },
            timeout: 5000,
            cache: false,
            beforeSend: function () {

            },
            error: function () {
                alert("检索群组网络请求失败!");
            },
            success: function (res) {
                if(res.result === "SUCCESS") {
                    //
                    // 发送申请
                    //
                    $.ajax({
                        type: "POST",
                        url: "group/join",
                        dataType: "json",
                        data: {
                            "groupid": groupid
                        },
                        timeout: 5000,
                        cache: false,
                        beforeSend: function () {

                        },
                        error: function () {
                            alert("发送申请网络请求失败!");
                        },
                        success: function (res) {
                            if(res.result === "SUCCESS") {
                                alert("群组加入申请发送成功");
                            } else if(res.reason === "E_ALREADY_JOIN") {
                                alert("您已经该群组的成员");
                            } else if(res.reason === "E_ALREADY_SENDED") {
                                alert("您已发送过群组申请，请耐心等候");
                            }
                        }

                    });

                } else {
                    if(res.reason === "E_INVALID_GROUPID") {
                        alert("所寻找的组不存在");
                    }
                }
            }
        });
    });


});