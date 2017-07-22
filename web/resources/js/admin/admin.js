$(document).ready(function () {
    //组长权限
    function mainAdmin() {
        $("#horizonLine").show();
        $(".mainAdminDiv").show();
        $("#setManager").show();
        $("#handOver").show();
        $("#container").css("height","725px");

    }

    //创建组织
    function createGroup() {
        $(".mainAdminDiv").append("<div id=\"createBtn\" class=\"Btn\"><span>创 建</span></div>");
        $("#createBtn").click(function () {
            alert("");
        })
    }

    //解散组织
    function dismissGroup() {
        $(".mainAdminDiv").append("<div id=\"dismissBtn\" class=\"Btn\"><span>解 散</span></div>");
        $("#dismissBtn").click(function () {
            alert("");
        })
    }

    //成员权限
    function memberEntry() {
        $(".Btn").css("display","none");
        $("#addImg").css("display","none");
        $(".baseInfoRight").css("display","none");
        $(".baseInfoLeft").css("left","140px");
        $("#verticalLine").css("height","545px");
        $("textarea").attr("readonly","readonly");
    }
    //预定义函数结束

    
    $(".triangle-right").click(function () {
        if($(this).attr('id')==="memberManage"){
            $(".memberListContainer").show();
        }
        else {
            if($(this).parents(".headerDiv").children(".hiddenDiv").css("display") === "none")
                $(this).css("transform", "rotate(90deg)");
            else
                $(this).css("transform", "rotate(0)");
            $(this).parents(".headerDiv").children(".hiddenDiv").toggle(200);
        }

    });

    $(".clickHide").click(function () {
        $(".memberListContainer").hide();
    })


});