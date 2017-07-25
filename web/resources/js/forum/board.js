var firstThemename;
var externBoardname;
var externThemename;
var histroyThemeName;
var textnumBoard = 0;

$(document).unbind("ready").bind("ready", function () {
    //文章按钮die之后已经不需要用if这种机制来防止多次加载了，但留下来作为教训
    textnumBoard += 1;

    if (true) {
        //变量定义区
        var getval = innerURL.split('?')[1];

        var boardnamenumber;

        if (getval.indexOf('&') > 0) {
            histroyThemeName = getval.split('&')[1].split('=')[1];
            boardnamenumber = getval.split('&')[0].split('=')[1];
        } else
            boardnamenumber = getval.split("=")[1];

        var boardname;
        switch (parseInt(boardnamenumber)) {
            case 1:
                boardname = '校园生活';
                break;
            case 2:
                boardname = '时事热点';
                break;
            case 3:
                boardname = '音乐天地';
                break;
            case 4:
                boardname = '影视先锋';
                break;
            case 5:
                boardname = '动漫乐园';
                break;
            case 6:
                boardname = '时尚前沿';
                break;
            case 7:
                boardname = '职场经验';
                break;
            case 8:
                boardname = '专业交流';
                break;
            case 9:
                boardname = '情感分享';
                break;
            case 10:
                boardname = '游戏世界';
                break;
            default:
                alert('switch failed');
        }
        var themenameList = null;
        var currentThemename = null;//用于翻页以及辨识发帖所属主题
        var userid = null;
        var articleList = null;
        var userForumAccount = null;

        function showArticleWithTheme(articleBlockid) {
            currentThemename = articleBlockid.split('_')[0];
            loadArticleList(currentThemename);
        }

        function loadArticleList(themename) {

            externBoardname = boardname;
            externThemename = currentThemename;
            var paramURL = "boardContent.html";
            selfRst = $.ajax({
                url: paramURL,
                async: true,
                cache: false,
                success:function(){
                    $(".board_content_container").html("");
                    $(".board_content_container").html(selfRst.responseText);
                }
            });


        }

        function jumpToArticleContent(articleId, authorId) {
            var newURL = 'article.html?' + 'id=' + articleId + '&author=' + authorId;
            //window.location.replace(newURL);
            simURLReplace(newURL);
        }

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
            }

        });
        $.ajax({
            type: "GET",
            url: "forum/userinfo",
            dataType: "json",
            data: {
                'userid': userid,
            },
            async: false,
            timeout: 5000,
            success: function (res) {
                if (res.result == 'LOGINERROR') {
                    window.reload('login.html');
                }
                if (res.result != 'SUCCESS') {
                    alert('获取用户个人信息失败，请刷新或重新登陆。')
                }
                userForumAccount = res.data;
            }
        });//获取用户信息
        $.ajax({
            type: "GET",
            url: "forum/theme",
            dataType: "json",
            data: {
                'boardName': boardname
            },
            async: false,
            timeout: 5000,
            success: function (res) {
                if (res.result == 'LOGINERROR') {
                    window.reload('login.html');
                }
                if (res.result != 'SUCCESS') {
                    alert('获取主题列表失败，请刷新。')
                }
                themenameList = res.data;
            }

        });//获取主题列表
        {
            for (var i = 0; i < themenameList.length; i++) {
                var themename = themenameList[i];
                var themeButtonid = themename + '_btn';

                $('.board_select_container').append(
                    '<div class="title_btn" id="' + themeButtonid + '"><span>' + themename + '</span></div>'
                );
                $('#' + themeButtonid).css({
                    'position': 'absolute',
                    'left': (30 + 105 * i) + 'px',
                    'top': '5px'
                });
                /*
                 var themeArticleid = themename + '_content';
                 $('.board_content_container').append(
                 '<div id="'+themeArticleid+'" class="theme_content"></div>'
                 );*/

            }
        }//根据主题列表添加按钮和文章列表显示区
        {
            $('#board_user').html('<b>' + userForumAccount.nickname + '</b>');
            $('#board_experience').text(userForumAccount.rank);
            var priviligeText;
            switch (parseInt(userForumAccount.privilige)) {
                case 0:
                    priviligeText = '被封禁';
                    break;
                case 1:
                    priviligeText = '不能交互';
                    break;
                case 2:
                    priviligeText = '不能发帖';
                    break;
                case 3:
                    priviligeText = '普通用户';
                    break;
                case 4:
                    priviligeText = '小编';
                    break;
                case 5:
                    priviligeText = '版主';
                    break;
                case 6:
                    priviligeText = '超级管理员';
                    break;
                default:
                    priviligeText = '未定义';
            }
            $('#board_privilege').text(priviligeText);
        }//设置用户个人信息栏的信息
        if(boardnamenumber == 10)
            boardnamenumberStr = '010.jpg';
        else if(boardnamenumber == 7)
            boardnamenumberStr = '007.png'
        else
            boardnamenumberStr = '00'+boardnamenumber+'.jpg';
        $('.board_pic_container').html('<img src="images/board/'+boardnamenumberStr+'" width="100%" height="100%"/>');
        firstThemename = themenameList[0];
        //回调方法绑定区

        $(".title_btn").css({
            "backgroundColor": "#ffffff",
            "color": "#006285"
        });
        /*$(".title_btn").hover(function () {
         $(this).css({
         "backgroundColor": "#D6D6D6"
         });
         },function () {
         $(this).css({
         "backgroundColor": "#ffffff"
         });
         });*/
        //当从论坛首页反复跳转到此页面时此处也会发生多重绑定，但不能用forumFirst控制，否则
        //从文章页面返回时不能绑定。故将返回论坛首页键也做成刷新主页面
        $(".title_btn").click(function () {
            //alert('clicked');
            var name = $(this).attr('id').split('_')[0];
            $(".title_btn").css({
                "backgroundColor": "#ffffff",
                "color": "#006285"
            });
            $(this).css({
                "backgroundColor": "#3771ff",
                "color": "#ffffff"
            });
            /*   $(".title_btn").hover(function () {
             $(this).css({
             "backgroundColor": "#D6D6D6"
             });
             },function () {
             $(this).css({
             "backgroundColor": "#ffffff"
             });
             });*/


            var themeButtonid = $(this).attr('id');
            var themename = themeButtonid.split('_')[0];
            var themeArticleid = themename + '_content';
            showArticleWithTheme(themeArticleid);

            /*$('#'+themeArticleid).css({
             'display':'block'
             })*/
        });
        $('.return_btn').css({
            "backgroundColor": "#ffffff",
            "color": "#006285"
        });
        $('.return_btn').hover(function () {
            $(this).css({
                "backgroundColor": "#D6D6D6"
            });
        }, function () {
            $(this).css({
                "backgroundColor": "#ffffff"
            });
        });
        $('.return_btn').click(function () {
            $(".title_btn").css({
                "backgroundColor": "#ffffff",
                "color": "#006285"
            });
            $(".title_btn").hover(function () {
                $(this).css({
                    "backgroundColor": "#D6D6D6"
                });
            }, function () {
                $(this).css({
                    "backgroundColor": "#ffffff"
                });
            });
            $(this).css({
                "backgroundColor": "#3771ff",
                "color": "#ffffff"
            });
            $(this).unbind("hover");
            var newURL = 'forum.html';
            //window.location.replace(newURL);
            window.location.href = 'home.html?load=forum';
        });
        $('#board_input_btn').click(function () {
            var title = $('#board_title_input').val();

            var content = $('#board_input').val();

            if (title.length == 0 || content.length == 0) {
                alert("文章和标题均不能为空")
                return;
            }
            $.ajax({
                type: "POST",
                url: "forum/article/publish",
                dataType: "json",
                data: {
                    'boardName': boardname,
                    'themeName': currentThemename,
                    'title': title,
                    'content': content,
                    'commentAllowed': 'yes'
                },
                timeout: 10000,
                success: function (res) {
                    if (res.result == 'LOGINERROR') {
                        window.reload('login.html');
                    }
                    if (res.result != 'SUCCESS') {
                        alert('发送失败。')
                    }
                    jumpToArticleContent(res.data, userid);
                }

            });
        });
        $('.board_content_item').die();
        if(forumFirst)
            $('.board_content_item').live('click', function () {
                var articleid = $(this).attr('id');
                var authorid = $(this).find($('.item_author')).attr('id');
                jumpToArticleContent(articleid, authorid);
            });
        function decodeUTF8(str) {
            return str.replace(/(\\u)(\w{4}|\w{2})/gi, function ($0, $1, $2) {
                return String.fromCharCode(parseInt($2, 16));
            });
        }

        {


            if (typeof histroyThemeName == 'string') {
                $('#' + decodeUTF8(histroyThemeName) + '_btn').click();
            } else
                $('#' + firstThemename + '_btn').click();
        }//模拟当页面加载后点击第一个button

    }
});