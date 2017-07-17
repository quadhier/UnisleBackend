package controller;

import dao.CommonDAO;
import dao.FriendshipDAO;
import dao.NoticeDAO;
import dao.UserInfoDAO;
import entity.UuserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ResultInfo;
import util.Rewrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.Console;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/15.
 */
@Controller
@RequestMapping("/friend")
public class FriendController {
    //tested
    @RequestMapping(value = "/searchUser",method = RequestMethod.GET)
    @ResponseBody()
    public Object searchForFriend(HttpServletRequest req,
                                  @RequestParam(value = "mailornickname",required = true) String mailornickname)
            throws Exception{
        List resultList = new ArrayList();
        if(mailornickname.contains("@")){
            UuserEntity user = (UuserEntity) CommonDAO.getItemByPK
                    (UuserEntity.class,UserInfoDAO.getUserID(mailornickname));
            resultList.add(user);
        }else{
            int resultLength = UserInfoDAO.searchNickname(mailornickname).length;
            if(resultLength == 0)
                return null;
            resultLength = resultLength>20?20:resultLength;
            UuserEntity[] searched = UserInfoDAO.searchNickname(mailornickname);
            for (int i=0;i<resultLength;i++) {
                resultList.add(searched[i]);
            }
        }

        return Rewrapper.wrapList(resultList,UuserEntity.class,"100110001000000");
    }
    //tested
    @RequestMapping(value = "/lookInfo",method = RequestMethod.GET)
    @ResponseBody()
    public Object getUserInfo(HttpServletRequest req,
                              @RequestParam(value = "userid") String userid)
            throws Exception{
        UuserEntity user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class,userid);
        return Rewrapper.wrap(user,"101111111111000");
    }
    //tested
    @RequestMapping(value="/checkAsk")
    @ResponseBody()
    public Object checkFriendshipAsk(HttpServletRequest req,
                                     @RequestParam(value = "sender",required = true) String sender,
                                     @RequestParam(value = "receiver",required = true) String receiver){
        ResultInfo result = new ResultInfo();
        if(FriendshipDAO.getFriendIDList(sender).contains(receiver)){
            result.setReason("E_FRIEND_ALREADY_ADDED");
            result.setResult("ERROR");
        }else if(FriendshipDAO.getBlacklist(receiver).contains(sender)){
            result.setReason("E_YOU_ARE_IN_BLACKLIST");
            result.setResult("ERROR");
        }else{
            result.setResult("SUCCESS");
        }
        return result;
    }

    @RequestMapping(value = "/sendAsk",method = RequestMethod.POST)
    @ResponseBody()
    public Object sendFriendshipAsk(HttpServletRequest req,
                                    @RequestParam(value = "sender") String sender,
                                    @RequestParam(value = "receiver") String receiver,
                                    @RequestParam(value = "content",required = false,defaultValue = "Hi, I want to be your friend.") String content){
        ResultInfo result = new ResultInfo();
        //用户1向用户2发送好友请求时，自动将2从自己的黑名单中删除（如果有）
        FriendshipDAO.deleteBlacklistItem(sender,receiver);
        if(NoticeDAO.seekSendedNotice(sender,receiver,"friendshipAsk")){
            result.setReason("E_ALLREADY_SENDED");
            result.setResult("ERROR");
        }else if(NoticeDAO.sendNotice(sender,receiver,content,"friendshipAsk")){
            result.setResult("SUCCESS");
        }else{
            result.setReason("E_SEND_ERROR");
            result.setResult("ERROR");
        }

        return result;
    }
    //tested
    /*
    @RequestMapping(value = "/getFriend",method = RequestMethod.GET)
    @ResponseBody
    public Object getFriendList(HttpServletRequest req,
                                @RequestParam(value = "userid") String userid)
            throws Exception{
        List idList = FriendshipDAO.getFriendIDList(userid);
        if(idList==null || idList.isEmpty())
            return null;

        List entityList = new ArrayList(idList.size());
        UuserEntity entity = null;
        for(int i=0;i<idList.size();i++) {
            entity = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, (String) idList.get(i));
            entityList.add(entity);
        }

        return Rewrapper.wrapList(entityList,UuserEntity.class,"100110001000000");
    }*/
    //tested
    @RequestMapping(value = "/getFriend",method = RequestMethod.GET)
    @ResponseBody
    public Object getFriendNoteList(HttpServletRequest req,
                                @RequestParam(value = "userid") String userid){
        Map<String,String> map = FriendshipDAO.getFriendIDNoteMap(userid);
        if(map==null || map.isEmpty())
            return null;

        class FriendNoteAdapter{
            public String userid;
            public String nickname;
            public String sex;
            public byte[] userpic;
            public String note;
        }

        List adapterList = new ArrayList();
        UuserEntity entity = null;
        FriendNoteAdapter adapter = null;
        for(Map.Entry<String,String> entry:map.entrySet()) {
            entity = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, entry.getKey());
            adapter = new FriendNoteAdapter();
            adapter.userid = entity.getUserid();
            adapter.nickname = entity.getNickname();
            adapter.sex = entity.getSex();
            adapter.userpic = entity.getUserpic();
            adapter.note = entry.getValue();
            adapterList.add(adapter);
        }

        return adapterList;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody()
    public Object deleteFriend(HttpServletRequest req,
                               @RequestParam(value = "friendid") String friendid){

    }

    @RequestMapping(value = "setNote",method = RequestMethod.POST)
    @ResponseBody()
    public Object setNote(HttpServletRequest req,
                          @RequestParam(value = "friendid") String friendid,
                          @RequestParam(value = "note",required = false,defaultValue = "") String note){

    }



}
