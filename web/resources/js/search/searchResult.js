$(document).ready(function () {
    var resultList = null;
    if(searchType == 'type_self'){
        $.ajax({
            type: "GET",
            url:'friend/searchUser',
            dataType: "json",
            data:{
                'mailornickname':searchContent
            },
            async:false,
            success:function (res) {
                if(res.result != 'SUCCESS') {
                    //alert('获取搜索结果失败');
                    return ;
                }

                resultList = res.data;
                $('#itemContainer').children().die();
                $('#itemContainer').children().remove();
                for(var i =0;i<resultList.length;i++){
                    var entity = resultList[i];
                    $('#itemContainer').append(
                        "<div class='searchRstCardContainer' data-type = '"+searchType+"' data-userid ='" + entity.userid + "' data-nickname ='" + entity.nickname + "' data-email = '" + entity.email + "'>" +
                        "<div class='imgDiv'><img src='" + entity.userpic + "'></div>" +
                        "<div class='mailDiv'><span>" + entity.email + "</span></div>" +
                        "<div class='nameDiv'><span>" + entity.nickname + "</span></div>" +
                        "</div>"
                    );
                }
            }
        });
    }else if(searchType == 'type_org'){
        $.ajax({
            type: "GET",
            url: 'group/byname',
            dataType: "json",
            data: {
                'groupName': searchContent
            },
            async: false,
            success: function (res) {
                if(res.result != 'SUCCESS') {
                    //alert('获取搜索结果失败');
                    return ;
                }
                resultList = res.data;
                $('#itemContainer').children().die();
                $('#itemContainer').children().remove();
                for(var i =0;i<resultList.length;i++){
                    var entity = resultList[i];
                    $('#itemContainer').append(
                        "<div class='searchRstCardContainer'  data-type = '"+searchType+"' data-groupid ='" + entity.groupid + "' data-groupname ='" + entity.name + "' >" +
                        "<div class='imgDiv'><img src='" + entity.grouppic + "'></div>" +
                        "<div class='mailDiv'><span>" + entity.groupid + "</span></div>" +
                        "<div class='nameDiv'><span>" + entity.name + "</span></div>" +
                        "</div>"
                    );
                }
            }
        });

    }else if(searchType == 'type_post'){
        $.ajax({
            type: "GET",
            url: 'forum/searchArticle',
            dataType: "json",
            data: {
                'name': searchContent
            },
            async: false,
            success: function (res) {
                if(res.result != 'SUCCESS' || res.data == null) {
                    //alert('获取搜索结果失败');
                    return ;
                }
                resultList = res.data;
                $('#itemContainer').children().die();
                $('#itemContainer').children().remove();
                for(var i =0;i<resultList.length;i++){
                    var entity = resultList[i];
                    var authorEntity = null;
                    $.ajax({
                        type: "GET",
                        url: "forum/userinfo",
                        dataType: "json",
                        data: {
                            'userid': entity.author
                        },
                        async: false,
                        timeout: 5000,
                        success: function (res) {
                            authorEntity = res.data;
                        }
                    });
                    $('#itemContainer').append(
                        "<div class='searchRstCardContainer'  data-type = '"+searchType+"' data-articleid ='" + entity.articleid + "' data-authorid ='" + entity.anthor + "'>"+
                        "<div class='articleDiv'><span>"+entity.title+"</span></div>"+
                        "<div class='authorDiv'><span>——"+authorEntity.nickname+"</span></div>"+
                        "</div>"
                    );
                }
            }
        });
    }

    $(function() {
        $("div.holder").jPages({
            containerID: "itemContainer",
            previous: "←",
            next: "→",
            perPage: 6
        });
    });
});