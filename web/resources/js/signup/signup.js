$(document).ready(function () {



    $("#vcodeBtn").click(function () {

        $.ajax({
            type: "POST",
            url: "signup/vcode",
            dataType: "json",
            data: {
                "contact": $("#iemail").val()
            },
            timeout: 5000,
            cache: false,
            beforeSend: function () {
                if($("#iemail").val() === null || $("#iemail").val() === "") {
                    alert("请填写邮箱");
                    return false;
                }
            },
            error: function () {

            },
            success: function (res) {
                if(res.result === "SUCCESS") {
                    alert("验证码已发送，请注意查收")
                }
            }
        });

    });


    $("#signupForm").ajaxForm({

        type: "POST",
        url: "signup",
        dataType: "json",
        data: {},
        timeout: 5000,
        cache: false,
        beforeSubmit: function (arr, $form, option) {

        },
        error: function () {

        },
        success: function (res) {
            window.location.href = "login.html";
        }
    }).submit(function () {
        return false;
    });

});