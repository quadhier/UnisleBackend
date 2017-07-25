var lived = false;
$(document).unbind("ready").ready(function () {

    var boardname = externBoardname;
    var themename = externThemename;
    var articleList;

    function jumpToArticleContent(articleId, authorId) {
        var newURL = 'article.html?' + 'id=' + articleId + '&author=' + authorId;
        simURLReplace(newURL);
    }

    $.ajax({
        type: "GET",
        url: "forum/article/all",
        dataType: "json",
        async: false,
        data: {
            'boardName': boardname,
            'themeName': themename,
            'startAt': '0',
            'number': '999'
        },
        timeout: 5000,
        success: function (res) {
            if (res.result == 'LOGINERROR') {
                window.reload('login.html');
            }
            if (res.result != 'SUCCESS') {
                alert('获取文章列表失败，请刷新。')
            }
            articleList = res.data;
        }

    });//给articleList赋值
    if (articleList == null || articleList.length == 0)
        return;
    /*
     一定要注意！没有内容的.theme_content也会被点击到，导致下面层的.theme_content不能被点击
     */
    $('.board_content_item').remove();
    $('.board_content_item').die();
    for (var i = 0; i < articleList.length; i++) {

        var articleId = articleList[i].articleid;
        var articleTitle = articleList[i].title;
        var articleAuthor = articleList[i].author;
        var lastComTime = new Date(parseInt(articleList[i].lastcomdatetime)).toLocaleString().replace(/:d{1,2}$/, ' ');
        var visitNumber = articleList[i].viewtimes;

        var articleAuthorForumAccount;
        $.ajax({
            type: "GET",
            url: "forum/userinfo",
            dataType: "json",
            data: {
                'userid': articleAuthor
            },
            async: false,
            timeout: 5000,
            success: function (res) {
                articleAuthorForumAccount = res.data;
            }
        });

        $('#itemContainer').append(
            '<div class="board_content_item" id="' + articleId + '" >' +
            '<div class="item_top"><img src="images/forum/collection.png" class="item_top_img_good"/><span>' + articleTitle + '</span><img src="images/forum/watching.png" class="item_top_img_comment"/></div>' +
            '<div class="item_bottom">' +
            '<span class="item_good_num"></span>' +
            '<span class="item_author_time">' +
            '<span class="item_author" id="' + articleAuthor + '">作者:' + articleAuthorForumAccount.nickname + '</span>' +
            '<span>&nbsp;最后评论:&nbsp;</span>' +
            '<span class="item_time">' + lastComTime + '</span>' +
            '</span>' +
            '<span class="item_comment_num">' + visitNumber + '</span>' +
            '</div>' +
            '</div>'
        );
        /**
         *
         * live前一定要die append前一定要remove
         *
         * live前一定要die append前一定要remove
         *
         * live前一定要die append前一定要remove
         * live前一定要die append前一定要remove
         * live前一定要die append前一定要remove
         * live前一定要die append前一定要remove
         *
         * live前一定要die append前一定要remove
         * live前一定要die append前一定要remove
         *
         * live前一定要die append前一定要remove
         live前一定要die  append前一定要remove
         同时注意：写在ready下的live和die只会执行一次，只有写在事件函数里
         才能每次加载前都die.
         */


    }
    $(function () {
        $("#pageHolder").jPages({
            containerID: "itemContainer",
            previous: "←",
            next: "→",
            perPage: 20
        });
    });//加载翻页函数必须在append之后
    //不能在子页面重复绑定事件，因为会让父页面也一并绑定上。
    //可以只绑定一次，或者放到父页面绑定
});