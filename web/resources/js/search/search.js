var searchContent = null;
var searchType = null;

$(document).unbind('ready').ready(function () {
    function jumpToArticleContent(articleId, authorId) {
        var newURL = 'article.html?' + 'id=' + articleId + '&author=' + authorId;
        simURLReplace(newURL);
    }

    $.ajax({
        type: "GET",
        url: "self",
        dataType: "json",
        data:{},
        async:false,
        timeout:5000,
        cache:false,
        success: function (res) {
            if(res.result == 'LOGINERROR'){
                window.reload('login.html');
            }
            if(res.result != 'SUCCESS'){
                alert('获取用户个人信息失败，请刷新或重新登陆。')
            }
        }

    });

    if(innerURL.indexOf('?')>0){
        searchContent = innerURL.split('?')[1].split('=')[1];
        searchType = 'type_self';
        $("#type_self").css("background-color","rgba(255,255,255,0.57)");
        $('#search_input').val(searchContent);
        loadAjax(searchContent,searchType);
    }else{
        searchType = 'type_self';
        $("#type_self").css("background-color","rgba(255,255,255,0.57)");
    }



    function loadAjax(content,type) {
        searchContent = content;
        searchType = type;
        var selfRst=$.ajax({url:"searchResult.html",async:false});
        $("#searchRst").css("top","4px");
        $("#searchRst").html(selfRst.responseText);
    }


    $("#type_self").click(function () {
        if($('#search_input').val() == null || $('#search_input').val().length == 0)
            return;
        searchContent = $('#search_input').val();
        searchType = 'type_self';
        $(this).css("background-color","rgba(255,255,255,0.57)");
        $("#type_org").css("background-color","rgba(204,204,204,0.8)");
        $("#type_post").css("background-color","rgba(204,204,204,0.8)");

        loadAjax(searchContent,searchType);
    });
    $("#type_org").click(function () {
        if($('#search_input').val() == null || $('#search_input').val().length == 0)
            return;
        searchContent = $('#search_input').val();
        searchType = 'type_org';
        $(this).css("background-color","rgba(255,255,255,0.57)");
        $("#type_self").css("background-color","rgba(204,204,204,0.8)");
        $("#type_post").css("background-color","rgba(204,204,204,0.8)");

        loadAjax(searchContent,searchType);
    });
    $("#type_post").click(function () {
        if($('#search_input').val() == null || $('#search_input').val().length == 0)
            return;
        searchContent = $('#search_input').val();
        searchType = 'type_post';
        $(this).css("background-color","rgba(255,255,255,0.57)");
        $("#type_org").css("background-color","rgba(204,204,204,0.8)");
        $("#type_self").css("background-color","rgba(204,204,204,0.8)");

        loadAjax(searchContent,searchType);
    });
    $('#searchBtn').click(function () {
        if($('#search_input').val() == null || $('#search_input').val().length == 0)
            return;
        searchContent = $('#search_input').val();
        loadAjax(searchContent,searchType);
    });
    $("input").focus(function(){
        $(this).parent().css("border-color","#bbbbff");
        $(this).parent().css("box-shadow","0 0 4px #bbbbff");
    });
    $("input").blur(function(){
        $(this).parent().css("border-color","#cccccc");
        $(this).parent().css("box-shadow","none");
    });
    //子页面回调方法live绑定区，记得子页面生成前要先remove和die
    if(!firstSearch)
        return;
    firstSearch = false;
    $('.searchRstCardContainer').live('click',function () {
        var type = $(this).attr('data-type');

        if(type == 'type_self'){
            simURLReplace('information.html?user='+$(this).attr('data-userid'));
        }else if(type == 'type_org'){

        }else if(type == 'type_post'){
            jumpToArticleContent($(this).attr('data-articleid'),$(this).attr('data-authorid'));
        }
    });

});