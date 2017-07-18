package controller;

import converter.ResultInfo;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/notice")
public class NoticeController {
    /**

    @RequestMapping(value = "/sendFriendshipAsk")
    public Object sendFriendshipAsk(@RequestParam(value = "userid") String userid,
                                    @RequestParam(value = "friendid") String friendid,
                                    HttpServletRequest req){
        ResultInfo info = new ResultInfo();

        WebSocketTest webSocketTest = WebSocketTest.sockets.get(userid);
        System.out.println("socket size:" + WebSocketTest.sockets.size());
        if(webSocketTest.sendMessage(friendid,"test mag")){
            info.setResult("SUCCESS");
        }else{
            info.setResult("ERROR");
            info.setReason("E_CANNOT_SEND");
        }

        return info;
    }
     */

    @RequestMapping(value = "/testMsg")
    @ResponseBody
    public Object test(@RequestParam(value = "msg") String msg){
        CometEngine engine = CometContext.getInstance().getEngine();
        engine.sendToAll("channel1",msg);

        return null;
    }
}
