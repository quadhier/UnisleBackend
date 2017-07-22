$(document).ready(function () {
    //变量定义区
    var userNickname;
    var userID;
    var currentChatterID = null;
    var websocket = null;
    var friendList = null;
    //方法及回调方法定义区
    function searchFriendList(text) {
        if (friendList == null) {
            $('.message_card').die();
            //第一次装载好友列表时执行
            $.ajax({
                type: 'GET',
                url: 'friend/getFriend',
                dataType: 'json',
                data: {},
                timeout: 2000,
                async: false,
                success: function (res) {
                    if (res.result == 'LOGINERROR') {
                        window.reload('login.html');
                    }
                    if (res.result != 'SUCCESS') {
                        alert('获取好友列表信息失败，请刷新或重新登陆。')
                    }
                    friendList = res.data;
                    for (var i = 0; i < friendList.length; i++) {
                        var frienddata = friendList[i];
                        var friendID = frienddata.userid;
                        var friendNickname = frienddata.nickname;
                        var friendNote = frienddata.note;
                        var friendShowName;
                        if (friendNote == null)
                            friendShowName = friendNickname;
                        else
                            friendShowName = friendNote;

                        $('.message_list_content').append(
                            "<div id = '" + friendID + "' class = 'message_card' >" +
                            "<div class='message_card_img_div'><img src='images/message/headimg.png' class='message_card_img'/></div>" +
                            "<div class='message_card_p_div'><p class='message_card_p'>" + friendShowName + "</p></div>"
                        );
                    }
                    $('.message_card').live('click', function () {
                        var friendid = $(this).attr('id');
                        getHistoryMessage(friendid);
                    });
                }
            });
        } else if (text == null || text.length == 0) {
            //搜索栏内容重置为空时执行
            $('.message_card').remove();
            $('.message_card').die();
            for (var i = 0; i < friendList.length; i++) {
                var frienddata = friendList[i];
                var friendID = frienddata.userid;
                var friendNickname = frienddata.nickname;
                var friendNote = frienddata.note;
                var friendShowName;
                if (friendNote == null)
                    friendShowName = friendNickname;
                else
                    friendShowName = friendNote;

                $('.message_list_content').append(
                    "<div id = '" + friendID + "' class = 'message_card' >" +
                    "<div class='message_card_img_div'><img src='images/message/headimg.png' class='message_card_img'/></div>" +
                    "<div class='message_card_p_div'><p class='message_card_p'>" + friendShowName + "</p></div>"
                );
            }
            $('.message_card').live('click', function () {
                var friendid = $(this).attr('id');
                getHistoryMessage(friendid);
            });
        } else {
            //执行搜索
            $('.message_card').remove();
            $('.message_card').die();
            for (var i = 0; i < friendList.length; i++) {
                var frienddata = friendList[i];
                var friendID = frienddata.userid;
                var friendNickname = frienddata.nickname;
                var friendNote = frienddata.note;
                var friendShowName;
                if (friendNote == null)
                    friendShowName = friendNickname;
                else
                    friendShowName = friendNote;
                //判断是否为搜索结果

                if (friendShowName.indexOf(text) < 0)
                    continue;

                $('.message_list_content').append(
                    "<div id = '" + friendID + "' class = 'message_card' >" +
                    "<div class='message_card_img_div'><img src='images/message/headimg.png' class='message_card_img'/></div>" +
                    "<div class='message_card_p_div'><p class='message_card_p'>" + friendShowName + "</p></div>"
                );
            }
            $('.message_card').live('click', function () {
                var friendid = $(this).attr('id');
                getHistoryMessage(friendid);
            });
        }
    }

    function getHistoryMessage(friendid) {
        currentChatterID = friendid;
        $('.message_my_word_div').remove();
        $('.message_your_word_div').remove();
        var unreadNumber;
        $.ajax({
            type: 'GET',
            url: 'chat/getUnreadMessageNumber',
            dataType: 'json',
            data: {
                'friendid': friendid
            },
            async: false,
            timeout: 2000,
            success: function (res) {
                if (res.result == 'LOGINERROR') {
                    window.reload('login.html');
                }
                if (res.result != 'SUCCESS') {
                    alert('获取消息列表失败，请刷新。')
                }
                unreadNumber = res.data;
            }
        });

        $.ajax({
            type: 'GET',
            url: 'chat/getHistoryMessage',
            dataType: 'json',
            async: false,
            data: {
                'friendid': friendid,
                'lasttime': (new Date()).valueOf(),
                'startat': 0,
                'number': unreadNumber > 5 ? unreadNumber : 5
            },
            timeout: 2000,
            success: function (res) {
                if (res.result == 'LOGINERROR') {
                    window.reload('login.html');
                }
                if (res.result != 'SUCCESS') {
                    alert('获取消息列表失败，请刷新。')
                }
                var messageList = res.data;
                for (var i = 0; i < messageList.length; i++) {
                    var message = messageList[i];
                    if (message.chatrecordEntityPK.sender == userID)
                        $('.message_content').prepend(
                            "<div id = '" + message.chatrecordEntityPK.senddatedtime +
                            "' class='message_my_word_div'><p>" + message.content + "</p></div>"
                        );
                    else
                        $('.message_content').prepend(
                            "<div id = '" + message.chatrecordEntityPK.senddatedtime +
                            "' class='message_your_word_div'><p>" + message.content + "</p></div>"
                        );

                    $.ajax({
                        type: 'POST',
                        url: 'chat/setMessageRead',
                        dataType: 'json',
                        data: {
                            'friendid': friendid,
                            'sendtime': message.chatrecordEntityPK.senddatedtime
                        },
                        timeout: 2000
                    });
                }
            }
        });
    }

    $('#message_btn').live('click', function () {
        var text = $('#message_input').val();
        if (text.length == 0)
            return;
        var currenttime = (new Date()).valueOf();
        var sendJson = JSON.stringify({
            'askcode': '100',
            'senderid': userID,
            'receiverid': currentChatterID,
            'content': text,
            'senddatetime': currenttime
        });
        websocket.send(sendJson);

        $('.message_content').append(
            "<div id = '" + currenttime +
            "' class='message_my_word_div'><p>" + text + "</p></div>"
        );
        $('.message_content').scrollTop($('.message_content').get(0).scrollHeight);
        $('#message_input').val("");
    });
    $('#findreceiver_input').live('change', function () {
        var searchText = $('#findreceiver_input').val();
        searchFriendList(searchText);
    })
    window.onbeforeunload = function () {
        var sendJson = JSON.stringify({
            'askcode': '999',
            'userid': userID
        });
        websocket.send(sendJson);
        websocket.close();
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
            userID = userdata.userid;
            userNickname = userdata.nickname;
        }

    });
    searchFriendList(null);
    if (friendList != null)
        getHistoryMessage(friendList[0].userid);
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/websocket");
        websocket.onopen = function () {
            var sendJson = JSON.stringify({
                'askcode': '000',
                'userid': userID
            });
            websocket.send(sendJson);
        };
        websocket.onclose = function () {
            var sendJson = JSON.stringify({
                'askcode': '999',
                'userid': userID
            });
            websocket.send(sendJson);
        };
        websocket.onerror = function () {
            var sendJson = JSON.stringify({
                'askcode': '999',
                'userid': userID
            });
            websocket.send(sendJson);
        };
        websocket.onmessage = function (event) {
            var returnJson = JSON.parse(event.data);
            if (returnJson.returncode == '102') {
                var senderid = returnJson.senderid;
                var sendtime = returnJson.sendtime;
                var content = returnJson.content;
                if (senderid = currentChatterID) {
                    $('.message_content').append(
                        "<div id = '" + sendtime +
                        "' class='message_your_word_div'><p>" + content + "</p></div>"
                    );
                    $('.message_content').scrollTop($('.message_content').get(0).scrollHeight);
                    $.ajax({
                        type: 'POST',
                        url: 'chat/setMessageRead',
                        dataType: 'json',
                        data: {
                            'friendid': senderid,
                            'sendtime': sendtime
                        },
                        timeout: 2000
                    });
                }
            }
        }

    } else {
        alert("你的浏览器不支持websocket,不能使用聊天功能。");
    }

});