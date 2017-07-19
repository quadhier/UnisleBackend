package controller;

import converter.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.WebSocketUtil;
import websocket.WebSocketHandler;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/notice")
public class NoticeController {

    @RequestMapping(value = "/getSocketLinkState")
    @ResponseBody
    public Object sendFriendshipAsk(@RequestParam(value = "userid") String userid,
                                    HttpServletRequest req){
        ResultInfo info = new ResultInfo();

        System.out.println("map size:" + WebSocketHandler.channels.size()+
                "user link numbers:" + WebSocketHandler.channels.get(userid).size());
        if(WebSocketUtil.sendToAllPages(userid,"your are in map. id is:"+userid)){
            info.setResult("SUCCESS");
        }else{
            info.setResult("ERROR");
            info.setReason("E_CANNOT_SEND");
        }

        return info;
    }

    @RequestMapping(value = "/testMsg")
    @ResponseBody
    public Object test(@RequestParam(value = "msg") String msg){


        return null;
    }

}

/*
@Controller
@RequestMapping("/message")
public class NoticeController{
    //websocket服务层调用类
    @Autowired
    private WebSocketMessageService wsMessageService;

    //请求入口
    @RequestMapping(value="/TestWS",method= RequestMethod.GET)
    @ResponseBody
    public String TestWS(@RequestParam(value="userId",required=true) Long userId,
                         @RequestParam(value="message",required=true) String message){

        if(wsMessageService.sendToAllTerminal(userId, message)){
            return "发送成功";
        }else{
            return "发送失败";
        }
    }
}
*/

