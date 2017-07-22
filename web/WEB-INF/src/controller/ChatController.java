package controller;


import converter.ResultInfo;
import dao.ChatDAO;
import entity.ChatrecordEntityPK;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ControllerUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping(value = "getUnreadMessageNumber", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistroyMessage(HttpServletRequest request,
                                    @RequestParam(value = "friendid") String friendid){
        String userid = ControllerUtil.getUidFromReq(request);
        ResultInfo rinfo = new ResultInfo();
        int result = ChatDAO.gerUnreadMessageNumber(userid,friendid);
        if(result < 0 ){
            rinfo.setReason("E_UNREAD_MESSAGE_NUMBER_CANNOT_GET");
            rinfo.setResult("ERROR");
        }else{
            rinfo.setResult("SUCCESS");
            rinfo.setData(result);
        }

        return rinfo;
    }

    @RequestMapping(value = "getHistoryMessage", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistroyMessage(HttpServletRequest request,
                                    @RequestParam(value = "friendid") String friendid,
                                    @RequestParam(value = "lasttime") String lasttime,
                                    @RequestParam(value = "startat") int startat,
                                    @RequestParam(value = "number") int number) {
        String userid = ControllerUtil.getUidFromReq(request);
        ResultInfo rinfo = new ResultInfo();
        List resultList = ChatDAO.getMessageList(userid, friendid, new Timestamp(Long.parseLong(lasttime)), startat, number);
        if (resultList == null) {
            rinfo.setResult("ERROR");
            rinfo.setReason("E_NO_HISTORY_MESSAGE");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        rinfo.setData(resultList);
        return rinfo;
    }

    @RequestMapping(value = "setMessageRead", method = RequestMethod.POST)
    @ResponseBody
    public Object setMessageRead(HttpServletRequest request,
                                 @RequestParam(value = "friendid") String friendid,
                                 @RequestParam(value = "sendtime") String sendtime) {
        String userid = ControllerUtil.getUidFromReq(request);
        ResultInfo rinfo = new ResultInfo();
        ChatrecordEntityPK pk = new ChatrecordEntityPK();
        pk.setSender(friendid);
        pk.setReceiver(userid);
        pk.setSenddatedtime(new Timestamp(Long.parseLong(sendtime)));
        if (ChatDAO.setMessageRead(pk)) {
            rinfo.setResult("SUCCESS");
        } else {
            rinfo.setResult("ERROR");
            rinfo.setReason("E_SET_READ_FAILED");
        }
        return rinfo;
    }
}

