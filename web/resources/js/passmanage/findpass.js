/**
 * Created by qudaohan on 2017/7/25.
 */


$(document).ready(function () {

    $("#getVcodeBtn").click(function () {

        // alert($("#iemail").val());
        // alert($("#ivcode").val());

        var contact = $("#iemail").val();
        var vcode = $("#ivcode").val();


        $.ajax({
            type: "POST",
            url: "password/vcode",
            dataType: "json",
            data: {
                contact: contact
            },
            timeout: 5000,
            cache: false,
            beforeSend: function () {

            },
            error: function () {

            },
            success: function (res) {
                if(res.result === "SUCCESS") {
                    alert("验证码已发送，请查收");
                } else {
                    if(res.reason === "E_NO_CONTACT") {
                        alert("请先填写邮箱");
                    } else if(res.reason === "E_ACCOUNT_NOT_EXIST") {
                        alert("该邮箱未注册未Unisle用户");
                    }
                }
            }
        });

    });


    $("#findpassForm").ajaxForm({

        type: "POST",
        url: "password/getback",
        dataType: "json",
        data: {

        },
        timeout: 5000,
        cache: false,
        beforeSubmit: function (arr, $form, option) {

            //alert(arr[0].name + " " + arr[0].value);
            //alert(arr[1].name + " " + arr[1].value);

            if(arr[0].value === null || arr[0].value === "" ||
                arr[1].value === null || arr[1].value === "") {
                alert("输入内容不能为空");
                return false;
            }
        },
        error: function () {

        },
        success: function (res) {
            if(res.result === "SUCCESS") {
                alert("验证通过，密码已发送至您的邮箱");
                window.location.href = "login.html";
            } else {
                if(res.reason === "E_INCOMPELETE_INFO") {
                    alert("请填写验证码");
                } else if(res.reason === "E_WRONG_VCODE") {
                    alert("验证码错误");
                }
            }
        }

    }).submit(function () {
        return false;
    });


});