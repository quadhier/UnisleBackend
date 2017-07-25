

$(document).ready(function () {

    $("#resetpassForm").ajaxForm({

        type: "POST",
        url: "password/reset",
        dataType: "json",
        data: {},
        timeout: 5000,
        cache: false,
        beforeSubmit: function (arr, $form, option) {
            if (arr[0].value === null || arr[0].value === "" ||
                arr[1].value === null || arr[1].value === "") {

                alert("输入内容不能为空");
                return false;
            }
            if (arr[0].value !== arr[1].value) {
                alert("两次输入的密码不一致，请重新输入");
            }
        },
        error: function () {

        },
        success: function (res) {
            if (res.result === "SUCCESS") {
                alert("修改成功");
            }
        }

    }).submit(function () {
        return false;
    });

});