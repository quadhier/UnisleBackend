

$("#loginForm").ajaxForm({

    type: "POST",
    url: "login",
    dataType: "json",
    data: {

    },
    timeout: 5000,
    cache: false,
    beforeSubmit: function (arr, $form, option) {

        alert(arr.length);

        if(arr[0].value == null || arr[0].value == "") {
            alert("请填写账号");
            return false;
        } else if(arr[1].value == null || arr[1].value == "") {
            alert("请填写密码");
            return false;
        }

    },
    error: function () {
        alert("网络错误")
    },
    success: function (res) {

        if(res.result === "SUCCESS") {
            //alert("登录成功，我要跳转到主页");
            window.location.href = "home.html";
        } else if (res.reason === "E_WRONG_USER_OR_PASSWD"){
            alert("用户名或密码错误")
        }

    }
});