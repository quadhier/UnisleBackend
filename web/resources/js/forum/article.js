$(document).unbind("ready").bind("ready", function () {

    //变量定义区
    var userid;
    var nickname;
    var articleid = innerURL.split('?')[1].split('&')[0].split('=')[1];
    var authorid = innerURL.split('?')[1].split('&')[1].split('=')[1];
    var articleEntity;
    var commentList;
    //初始化执行区
    $.ajax({
        type: "GET",
        url: "self",
        dataType: "json",
        data: {},
        async: false,
        timeout: 5000,
        cache: false,
        success: function (res) {
            if (res.result == 'LOGINERROR') {
                window.reload('login.html');
            }
            if (res.result != 'SUCCESS') {
                alert('获取用户个人信息失败，请刷新或重新登陆。')
            }
            var userdata = res.data;
            userid = userdata.userid;
            nickname = userdata.nickname;
        }

    });//获得用户信息
    $.ajax({
        type: "GET",
        url: "forum/article/content",
        dataType: "json",
        data: {
            'articleid': articleid
        },
        timeout: 5000,
        async: false,
        cache: false,
        success: function (res) {
            if (res.result == 'LOGINERROR') {
                window.reload('login.html');
            }
            if (res.result != 'SUCCESS') {
                alert('获取文章信息失败，请刷新。')
            }
            articleEntity = res.data;
        }

    });//获得文章信息
    $.ajax({
        type: "GET",
        url: "forum/article/comment",
        dataType: "json",
        data: {
            'articleid': articleid
        },
        timeout: 5000,
        async: false,
        cache: false,
        success: function (res) {
            if (res.result == 'LOGINERROR') {
                window.reload('login.html');
            }
            if (res.result != 'SUCCESS') {
                alert('获取评论列表失败，请刷新。')
            }
            commentList = res.data;
        }

    });//获得评论实体列表
    $.ajax({
        type: "GET",
        url: "forum/userinfo",
        dataType: "json",
        data: {
            'userid': authorid
        },
        timeout: 3000,
        success: function (res) {
            var authorData = res.data;
            $('#authorName').text(authorData.nickname);
            $('#authorLevel').text('作者等级：' + authorData.rank);
            $('#authorExp').text('作者经验：' + authorData.exp);
        }
    });//获得作者信息并修改作者栏
    {
        var publictime = new Date(parseInt(articleEntity.publicdatetime)).toLocaleString().replace(/:d{1,2}$/, ' ');
        $('#articleTitleSpan').text(articleEntity.title);
        $('.articleContent').html('<p>' + articleEntity.content + '</p>');
        $('#articlePublicTimeSpan').text('发表时间:' + publictime);
    }//修改文章栏
    {
        $(".articleBackIcon").hide();
        $(".articleContentDiv").hover(function () {
            $(".articleBackIcon").show(500);
        }, function () {
            $(".articleBackIcon").hide(500);
        });
    }//设置返回按钮
    {
        $('.commentDiv').remove();
        var commentNum = commentList.length;
        $('#articleCommentNumberSpan').text('评论:' + commentNum);
        for (var i = 0; i < commentNum; i++) {
            var commentEntity = commentList[i];
            var commenter;
            var commentContent = commentEntity.content;
            var commentTime = new Date(parseInt(commentEntity.articlecommentEntityPK.publicdatetime)).toLocaleString().replace(/:d{1,2}$/, ' ');
            $.ajax({
                type: "GET",
                url: "forum/userinfo",
                dataType: "json",
                async: false,
                data: {
                    'userid': commentEntity.articlecommentEntityPK.userid
                },
                timeout: 3000,
                success: function (res) {
                    commenter = res.data;
                }
            });

            $('#itemContainer').append(
                '<div id = "comment_NO."' + i + ' class="commentDiv">' +
                '<div class="commentDivTop">' +
                '<div class="commenter"><span>' + commenter.nickname + '</span></div>' +
                '<div class="floor"><span>#' + (i + 1) + '</span></div>' +
                '</div>' +
                '<div class="commentContent">' +
                '<p>' + commentContent + '</p>' +
                '</div>' +
                '<div class="commentTime">' +
                '<span>' + commentTime + '</span>' +
                '</div>' +
                '</div>'
            );
        }
    }//加载评论列表
    $(function () {
        $("div.holder").jPages({
            containerID: "itemContainer",
            previous: "←",
            next: "→",
            perPage: 5
        });
    });//设置页码栏
    //回调方法绑定区
    $('#publishComment').click(function () {
        var content = $('.comment_input').val();
        //$('.comment_input').val("");
        if (content.length == 0) {
            alert("回复不能为空")
            return;
        }
        $.ajax({
            type: "POST",
            url: "forum/article/comment",
            dataType: "json",
            data: {
                'articleid': articleid,
                'content': content
            },
            timeout: 10000,
            success: function (res) {
                if (res.result == 'LOGINERROR') {
                    window.reload('login.html');
                }
                if (res.result != 'SUCCESS') {
                    alert('发送失败。')
                }
                //发送通知：文章评论
                $.ajax({
                    type:'POST',
                    url:'notice/sendNotice',
                    dataType:'json',
                    data:{
                        'receiver':authorid,
                        'type':'articlecommented',
                        'content':content+' (Re:'+articleEntity.title+')'
                    },
                    timeout:10000
                });
                var newURL = 'article.html?' + 'id=' + articleid + '&author=' + authorid;
                //window.location.replace(newURL);
                simURLReplace(newURL);
            }

        });
    });
    function encodeUTF8(str) {
        var temp = "", rs = "";
        for (var i = 0, len = str.length; i < len; i++) {
            temp = str.charCodeAt(i).toString(16);
            rs += "\\u" + new Array(5 - temp.length).join("0") + temp;
        }
        return rs;
    }

    $(".articleBackIcon").unbind("click");
    $(".articleBackIcon").click(function () {
        forumFirst = false;
        var boardname = articleEntity.boardname;
        var boardcount;

        {
            if (boardname == '校园生活')
                boardcount = 1;
            else if (boardname == '时事热点')
                boardcount = 2;
            else if (boardname == '音乐天地')
                boardcount = 3;
            else if (boardname == '影视先锋')
                boardcount = 4;
            else if (boardname == '动漫乐园')
                boardcount = 5;
            else if (boardname == '时尚前沿')
                boardcount = 6;
            else if (boardname == '职场经验')
                boardcount = 7;
            else if (boardname == '专业交流')
                boardcount = 8;
            else if (boardname == '情感分享')
                boardcount = 9;
            else if (boardname == '游戏世界')
                boardcount = 10;
        }
        var oldURL = 'board.html?board=' + boardcount + '&theme=' + encodeUTF8(articleEntity.themename);

        //window.location.replace(oldURL);
        simURLReplace(oldURL);
        //window.history.back(-1);
    })

});