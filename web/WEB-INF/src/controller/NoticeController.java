package controller;

import converter.ResultInfo;
import dao.NoticeDAO;
import entity.NoticeEntityPK;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ControllerUtil;
import util.WebSocketUtil;
import websocket.WebSocketHandler;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;


@Controller
@RequestMapping("/notice")
public class NoticeController {
    @RequestMapping(value = "sendNotice" , method = RequestMethod.POST)
    @ResponseBody
    public Object sendNotice(HttpServletRequest req,
                               @RequestParam(value = "receiver") String receiver,
                               @RequestParam(value = "content", required = false) String content,
                               @RequestParam(value = "type") String type) {
        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if (type != null &&
                !type.equals("groupinvite") &&
                !type.equals("articlecommented") &&
                !type.equals("activitycommented") &&
                !type.equals("activityproed") &&
                !type.equals("activityforwarded") &&
                !type.equals("addedtoblicklist") &&
                !type.equals("friendshipdeleted")) {
            result.setResult("ERROR");
            result.setReason("NO_SUCH_TYPE");
            return result;
        }
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (NoticeDAO.sendNotice(userid, receiver,content, type,time)) {
            result.setResult("SUCCESS");
            WebSocketUtil.sendNoticeIfOnline(userid,receiver,type,time,content);
        } else{
            result.setResult("ERROR");
            result.setReason("E_SEND_FAILED");
        }

        return result;
    }

    @RequestMapping(value = "/getSocketLinkState")
    @ResponseBody
    public Object getSocketLinkState(@RequestParam(value = "userid") String userid,
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

    @RequestMapping(value = "getUnreadNotice" , method = RequestMethod.GET)
    @ResponseBody
    public Object getUnreadNotice(HttpServletRequest req){
        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if(NoticeDAO.getNoticenum(userid) == 0){
            result.setResult("ERROR");
            result.setReason("NO_UNREAD_NOTICE");
            return result;
        }

        result.setResult("SUCCESS");
        result.setData(NoticeDAO.getNewNoticeList(userid,null));

        return result;
    }

    @RequestMapping(value = "getUnreadNumber" , method = RequestMethod.GET)
    @ResponseBody
    public Object getUnreadNumber(HttpServletRequest req){
        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        int number = NoticeDAO.getNoticenum(userid);
        if(number < 0){
            result.setResult("ERROR");
            result.setReason("E_GET_FAILED");
            return result;
        }

        result.setResult("SUCCESS");
        result.setData(number);

        return result;
    }

    @RequestMapping(value = "getAllNotice" , method = RequestMethod.GET)
    @ResponseBody
    public Object getAllNotice(HttpServletRequest req,
                                  @RequestParam(value = "type", required = false) String type){
        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if(type != null &&
                !type.equals("friendshipask") &&
                !type.equals("groupinvite") &&
                !type.equals("articlecommented") &&
                !type.equals("activitycommented") &&
                !type.equals("activityproed") &&
                !type.equals("activityforwarded") &&
                !type.equals("addedtoblicklist") &&
                !type.equals("friendshipdeleted") ){
            result.setResult("ERROR");
            result.setReason("NO_SUCH_TYPE");
            return result;
        }

        result.setData(NoticeDAO.getAllNoticeList(userid,type));
        result.setResult("SUCCESS");
        return result;
    }

    @RequestMapping(value = "deleteANotice" , method = RequestMethod.POST)
    @ResponseBody
    public Object deleteANotice(HttpServletRequest req,
                                @RequestParam(value = "sender") String sender,
                                @RequestParam(value = "sendtime") String time){
        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        Timestamp timestamp = new Timestamp(Long.parseLong(time));
        NoticeEntityPK pk = new NoticeEntityPK();
        pk.setSender(sender);
        pk.setReceiver(userid);
        pk.setGendatetime(timestamp);
        if(NoticeDAO.deleteANotice(pk)){
            result.setResult("SUCCESS");
        }else{
            result.setReason("E_DELETE_FAILED");
            result.setResult("ERROR");
        }

        return result;
    }

    @RequestMapping(value = "setNoticeRead" , method = RequestMethod.POST)
    @ResponseBody
    public Object setNoticeRead(HttpServletRequest req,
                                @RequestParam(value = "sender") String sender,
                                @RequestParam(value = "sendtime") String time) {
        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        Timestamp timestamp = new Timestamp(Long.parseLong(time));
        NoticeEntityPK pk = new NoticeEntityPK();
        pk.setSender(sender);
        pk.setReceiver(userid);
        pk.setGendatetime(timestamp);
        if (NoticeDAO.setNoticeRead(pk)) {
            result.setResult("SUCCESS");
        } else {
            result.setReason("E_DELETE_FAILED");
            result.setResult("ERROR");
        }

        return result;
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

