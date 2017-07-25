
$(document).ready(function () {


    //
    // 跳转至个人信息展示页面
    //
    $("#control_self").click(function () {
        $(".controlBar div").css("background-color","rgba(204,204,204,0.8)").hover(function(){
            $(this).css("background-color","rgba(255,255,255,0.57)");
        },function(){
            $(this).css("background-color","rgba(204,204,204,0.8)");
        });
        $(this).css("background-color","rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#self_concrete").show();
    });

    //
    // 跳转至个人信息编辑页面
    //
    $("#control_edit").click(function () {
        $(".controlBar div").css("background-color","rgba(204,204,204,0.8)").hover(function(){
            $(this).css("background-color","rgba(255,255,255,0.57)");
        },function(){
            $(this).css("background-color","rgba(204,204,204,0.8)");
        });
        $(this).css("background-color","rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#edit_concrete").show();
    });

    //
    // 跳转至隐私设置页面
    //
    $("#control_privacy").click(function () {
        $(".controlBar div").css("background-color","rgba(204,204,204,0.8)").hover(function(){
            $(this).css("background-color","rgba(255,255,255,0.57)");
        },function(){
            $(this).css("background-color","rgba(204,204,204,0.8)");
        });
        $(this).css("background-color","rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#privacy_concrete").show();

        // 填充黑名单
        $(".blacklistContainer").empty();
        $.ajax({
            type: "GET",
            url: "friend/blacklist",
            dataType: "json",
            data: {

            },
            timeout: 5000,
            cache: false,
            beforeSend: function () {

            },
            error: function () {

            },
            success: function (res) {
                // alert("xixi");
                // alert(res.data.length);
                if(res.data !== null) {
                    var blacklist = res.data;
                    for(var i in blacklist) {

                        $(".blacklistContainer").append("<div class='blackCard' data-blackid='" + blacklist[i].userid + "' ><span class='blackTitle'>" +
                            blacklist[i].nickname + "</span><span class='blackMail'>" +
                            blacklist[i].email + "</span></div>");
                    }
                }
            }

        }); // ajax 结束

    });



    //
    // 跳转至相册页面
    //
    $("#control_album").click(function () {
        $(".controlBar div").css("background-color","rgba(204,204,204,0.8)").hover(function(){
            $(this).css("background-color","rgba(255,255,255,0.57)");
        },function(){
            $(this).css("background-color","rgba(204,204,204,0.8)");
        });
        $(this).css("background-color","rgba(255,255,255,0.57)").unbind('mouseenter').unbind('mouseleave');
        $(".concreteDiv").hide();
        $("#album_concrete").show();

        $("#allSelect").find('span').text("全选");

        $("#itemContainer").empty();



        for(var i = 0; i<50; i++)
        {
            $("#itemContainer").append("<div class='photo_default'><img src='images/img (1).jpg' alt='image'></div>");
            $("#itemContainer").append("<div class='photo_default'><img src='images/img (6).jpg' alt='image'></div>");
        }

        $(function() {
            $("div.holder").jPages({
                containerID: "itemContainer",
                previous: "←",
                next: "→",
                perPage: 25
            });
        });
    });

    $("input").focus(function(){
        $(this).parent().css("border-color","#bbbbff");
        $(this).parent().css("box-shadow","0 0 4px #bbbbff");
    });
    $("input").blur(function(){
        $(this).parent().css("border-color","#cccccc");
        $(this).parent().css("box-shadow","none");
    });

    $("textarea").focus(function(){
        $(this).parent().css("border-color","#bbbbff");
        $(this).parent().css("box-shadow","0 0 4px #bbbbff");
    });
    $("textarea").blur(function(){
        $(this).parent().css("border-color","#cccccc");
        $(this).parent().css("box-shadow","none");
    });

    $("select").focus(function(){
        $(this).parent().css("border-color","#bbbbff");
        $(this).parent().css("box-shadow","0 0 4px #bbbbff");
    });
    $("select").blur(function(){
        $(this).parent().css("border-color","#cccccc");
        $(this).parent().css("box-shadow","none");
    });

    $("#activityVisibility_2").click(function () {
        var className1 = ($("#activityVisibility").attr('class')=="close1")?"open1":"close1";
        var className2 = ($("#activityVisibility_2").attr('class')=="close2")?"open2":"close2";
        $("#activityVisibility").attr('class',className1);
        $("#activityVisibility_2").attr('class',className2);

        if(className1 === 'open1') {
            // alert("open");
            // 更改动态可见性为仅自己可见
            $.ajax({
                type: "POST",
                url: "self/actvisibility",
                dataType: "json",
                data: {
                    "view": "self"
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },
                error: function () {

                },
                success: function (res) {
                    alert(res.result);
                }
            });

        } else {
            // alert("close");
            // 更改动态可见性为自己和好友均可见
            $.ajax({
                type: "POST",
                url: "self/actvisibility",
                dataType: "json",
                data: {
                    "view": "friend"
                },
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },
                error: function () {

                },
                success: function (res) {
                    alert(res.result);
                }
            });

        }
    });
    
    $("#receiveInfo_2").click(function () {
        var className1 = ($("#receiveInfo").attr('class')=="close1")?"open1":"close1";
        var className2 = ($("#receiveInfo_2").attr('class')=="close2")?"open2":"close2";
        $("#receiveInfo").attr('class',className1);
        $("#receiveInfo_2").attr('class',className2);

        if(className1 === 'open1')
            alert("open");
        else
            alert("close");
    });

    $("#allSelect").click(function () {
        if($("div").hasClass("photo_default")){
            $(".photo_default").click();
            $(this).find('span').text("全不选");
        }
        else {
            $(".photo_selected").click();
            $(this).find('span').text("全选");
        }
    });


    $("#photo_delete").click(function () {
        if(!$("div").hasClass("photo_selected"))
            alert("请选择要删除的图片！");
        else {
            // 删除照片



            $("#control_album").click();
        }
    });


    $("#addPhoto").click(function () {
        // 添加照片


    });


    // 删除黑名单中的用户
    $("#black_delete").click(function () {
        if(!$("div").hasClass("blackCard_selected"))
            alert("请选择要移出黑名单的用户！");
        else {

            var blacklist = [];
            $(".blacklistContainer").children(".blackCard_selected").map(function () {
                alert($(this).data("blackid"));
                blacklist.push($(this).data("blackid"));
            });

            $.ajax({
                type: "POST",
                url: "friend/blacklist/delete",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(blacklist),
                timeout: 5000,
                cache: false,
                beforeSend: function () {

                },
                error: function () {

                },
                success: function (res) {

                    if(res.result === "SUCCESS") {
                        $("#control_privacy").click();
                    }
                }

            });

        }
    });


    // 相片选中
    $(".photo_default").live("click",function () {
        $(this).css("opacity","0.5");
        $(this).append("<img class='checked' src='images/self/checked.png'>");
        $(this).attr('class',"photo_selected");
    });

    // 相片取消选中
    $(".photo_selected").live("click",function () {
        $(this).attr('class',"photo_default");
        $(this).find(".checked").remove();
        $(this).css("opacity","1");
    });

    // 黑名单选中
    $(".blackCard").live("click",function () {
        $(this).attr('class',"blackCard_selected");
        //alert($(this).data("blackid"));
    });

    // 取消选中
    $(".blackCard_selected").live("click",function () {
        $(this).attr('class',"blackCard");
    });


    // 修改密码
    $("#changepass").click(function () {
        window.location.href = "home.html?load=resetpass";
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

    // 日期格式化
    Date.prototype.pattern = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, // 月份
            "d+": this.getDate(), // 日
            "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
            "H+": this.getHours(), // 小时
            "m+": this.getMinutes(), // 分
            "s+": this.getSeconds(), // 秒
            "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
            "S": this.getMilliseconds() // 毫秒
        };
        var week = {
            "0": "/u65e5",
            "1": "/u4e00",
            "2": "/u4e8c",
            "3": "/u4e09",
            "4": "/u56db",
            "5": "/u4e94",
            "6": "/u516d"
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        if (/(E+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };


    // 产生图片后缀，强制图片更新
    function randomTail() {
        return "?r=" + Math.random();
    }


    /*
     *
     * 个人信息加载
     *
     * */

    // 全局变量定义

    var ownerid; // 用来生成文件名


    var owner;
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

            owner = res.data.user;
            ownerid = owner.userid;
            signature = toStr(owner.signature);
            userHeadImg = getHeadImg(owner) + randomTail();
            nickname = toStr(owner.nickname);
            realname = toStr(owner.realname);
            school = toStr(owner.school);
            department = toStr(owner.department);
            grade = toStr(owner.grade);
            sex = toStr(owner.sex);
            birthday = owner.birthday === null ? "" : toStr((new Date(parseInt(owner.birthday))).toDateString());
            hometown = toStr(owner.hometown);
            contactway = toStr(owner.contactway);
            description = toStr(owner.description);

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
            intet = res.data.interest;
            if (intet !== null) {
                var interests = "music:<br />&nbsp;&nbsp;" + toStr(intet.music) + "<br />" +
                    "sports:<br />&nbsp;&nbsp;" + toStr(intet.sport) + "<br />" +
                    "book:<br />&nbsp;&nbsp;" + toStr(intet.book) + "<br />" +
                    "movie:<br />&nbsp;&nbsp;" + toStr(intet.movie) + "<br />" +
                    "game:<br />&nbsp;&nbsp;" + toStr(intet.game) + "<br />" +
                    "others:<br />&nbsp;&nbsp;" + toStr(intet.other);
                $(".self_bottom_left .self_bottom_item p").html(interests);
            }


            //
            // 填充编辑页面中的信息
            //

            // 基本信息
            if (realname !== "") {
                $("#irealname").attr("placeholder", realname);
            }
            if (nickname !== "") {
                $("#inickname").attr("placeholder", nickname);
            }
            if (school !== "") {
                $("#ischool").attr("placeholder", school);
            }
            if (department !== "") {
                $("#idepartment").attr("placeholder", department);
            }
            if (grade !== "") {
                $("#igrade").attr("placeholder", grade);
            }
            if (contactway !== "") {
                $("#icontactway").attr("placeholder", contactway);
            }
            if (hometown !== "") {
                $("#ihometown").attr("placeholder", hometown);
            }
            if (sex === "male") {
                // 这样就设置了默认值
                $("#isex  option[value='male']").attr("selected", true);
            } else {
                $("#isex  option[value='female']").attr("selected", true);
            }

            // 生日格式化，并设置默认值
            if (birthday !== "") {
                var bdate = new Date(birthday);
                $("#ibirthday")[0].defaultValue = bdate.pattern("yyyy-MM-dd");
            }


            // 兴趣信息
            if (intet !== null) {
                if (intet.music !== "")
                    $("#imusic").attr("placeholder", intet.music);
                if (intet.sport)
                    $("#isport").attr("placeholder", intet.sport);
                if (intet.book !== "")
                    $("#ibook").attr("placeholder", intet.book);
                if (intet.movie)
                    $("#imovie").attr("placeholder", intet.movie);
                if (intet.game)
                    $("#igame").attr("placeholder", intet.game);
                if (intet.other)
                    $("#iother").attr("placeholder", intet.other);
            }

            // 个人简介和个性签名
            if (description !== "") {
                $("#idescription").attr("placeholder", description);
            }
            if (signature !== "") {
                $("#isignature").attr("placeholder", signature);
            }

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
                if (arr[0].value === "") {
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


    // 编辑页面中的确认按钮

    //userForm
    $("#userForm").ajaxForm({
        type: "POST",
        url: "self",
        dataType: "json",
        data: {},
        timeout: 5000,
        cache: false,
        async: false,
        //resetForm: true,
        beforeSubmit: function (arr, $form, option) {

            // if(arr[1].value === "") {
            //     arr.splice(1, 1);
            // }

            var tagid;
            for (var x in arr) {

                if (arr[x].value !== null && arr[x].value !== "") {
                    if (arr[x].name === "birthday") {
                        $("#ibirthday")[0].defaultValue = arr[x].value;
                        var bdate = new Date(arr[x].value);
                        arr[x].value = bdate.getTime();
                        alert(arr[x].value);
                    } else if (arr[x].name === "sex") {
                        if (arr[x].value === "male") {
                            $("#isex  option[value='male']").attr("selected", true);
                        } else if (arr[x].value === "female") {
                            $("#isex  option[value='female']").attr("selected", true);
                        }
                    } else {
                        tagid = "#i" + arr[x].name;
                        $(tagid).attr("placeholder", arr[x].value);
                    }
                } else {
                    arr[x].name = "nouse";
                }
            }

        },
        error: function () {

        },
        success: function (res) {

        }

    }).submit(function () {
        return false;
    })


    // 更改动态的可见性，是否接收通知，修改密码


    // 黑名单




});