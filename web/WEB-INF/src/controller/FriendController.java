package controller;

import dao.*;
import entity.UuserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import converter.ResultInfo;
import util.ControllerUtil;
import util.Rewrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    @ResponseBody
    public Object searchForFriend(@RequestParam(value = "mailornickname",required = true) String mailornickname,
                                  HttpServletRequest req)
            throws Exception{
        List resultList = new ArrayList();
        ResultInfo result = new ResultInfo();
        if(mailornickname.contains("@")){
            UuserEntity user = (UuserEntity) CommonDAO.getItemByPK
                    (UuserEntity.class, UserInfoDAO.getUserID(mailornickname));
            if(user != null)
                resultList.add(user); // null不应该被加入resultList中去
            else{
                result.setResult("ERROR");
                result.setReason("E_NO_USER_IS_FOUND");
                return result;
            }
        }else{
            int resultLength = UserInfoDAO.searchNickname(mailornickname).length;
            if(resultLength == 0){
                result.setResult("ERROR");
                result.setReason("E_NO_USER_IS_FOUND");
                return result;
            }
            resultLength = resultLength>20?20:resultLength;
            UuserEntity[] searched = UserInfoDAO.searchNickname(mailornickname);
            for (int i=0;i<resultLength;i++) {
                resultList.add(searched[i]);
            }
        }
        result.setResult("SUCCESS");
        result.setData(Rewrapper.wrapList(resultList, UuserEntity.class,"1011111111111111111"));

        return result;
    }

    //tested
    @RequestMapping(value = "/lookInfo",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserInfo(HttpServletRequest req) throws Exception{
        ResultInfo result = new ResultInfo();
        String userid = ControllerUtil.getUidFromReq(req);
        UuserEntity user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class,userid);
        result.setResult("SUCCESS");
        result.setData(Rewrapper.wrap(user,"1011111111111111111"));
        return result;
    }

    //tested
    @RequestMapping(value="/checkAsk")
    @ResponseBody
    public Object checkFriendshipAsk(HttpServletRequest req,
                                     @RequestParam(value = "receiver",required = true) String receiver){

        String sender = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();


        if(sender.equals(receiver)) {
            result.setReason("E_SAMEONE");
            result.setResult("ERROR");
        } else if(FriendshipDAO.getFriendIDList(sender).contains(receiver)){
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
    @ResponseBody
    public Object sendFriendshipAsk(HttpServletRequest req,
                                    @RequestParam(value = "receiver") String receiver,
                                    @RequestParam(value = "content",required = false, defaultValue = "Hi, I want to be your friend.") String content){

        String sender = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        //用户1向用户2发送好友请求时，自动将2从自己的黑名单中删除（如果有）
        FriendshipDAO.deleteBlacklistItem(sender,receiver);
        if(NoticeDAO.seekSendedNotice(sender,receiver,"friendshipask")){
            result.setReason("E_ALREADY_SENDED");
            result.setResult("ERROR");
        }else if(NoticeDAO.sendNotice(sender,receiver,content,"friendshipask")){
            result.setResult("SUCCESS");
        }else{
            result.setReason("E_SEND_ERROR");
            result.setResult("ERROR");
        }

        return result;
    }

    //tested
    @RequestMapping(value = "/getFriend",method = RequestMethod.GET)
    @ResponseBody
    public Object getFriendNoteList(HttpServletRequest req){
        ResultInfo result = new ResultInfo();
        String userid = ControllerUtil.getUidFromReq(req);

        Map<String,String> map = FriendshipDAO.getFriendIDNoteMap(userid);
        if(map==null || map.isEmpty()){
            result.setResult("ERROR");
            result.setReason("E_NOT_FOUND");
            return result;
        }

        class FriendNoteAdapter{
            public String userid;
            public String nickname;
            public String sex;
            public String userpic;
            public String note;
            public String school;
            public String department;
            public String grade;
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
            adapter.school = entity.getSchool();
            adapter.department = entity.getDepartment();
            adapter.grade = entity.getGrade();
            adapterList.add(adapter);
        }
        result.setResult("SUCCESS");
        result.setData(adapterList);

        return result;
    }

    @RequestMapping(value = "/deleteFriend", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteFriend(HttpServletRequest req,
                               @RequestParam(value = "friendid") String friendid){

        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if(FriendshipDAO.deleteFriendship(userid,friendid)){
            result.setResult("SUCCESS");
        }else{
            result.setResult("ERROR");
            result.setReason("E_NOT_DELETE");
        }

        return result;
    }

    @RequestMapping(value = "setNote",method = RequestMethod.POST)
    @ResponseBody
    public Object setNote(HttpServletRequest req,
                          @RequestParam(value = "friendid") String friendid,
                          @RequestParam(value = "note",required = false,defaultValue = "") String note){

        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if(FriendshipDAO.setFriendshipNote(userid, friendid, note)){
            result.setResult("SUCCESS");
        }else{
            result.setReason("E_SET_FAILED");
            result.setResult("ERROR");
        }

        return result;
    }

    @RequestMapping(value = "addBlackList",method = RequestMethod.POST)
    @ResponseBody
    public Object addBlackList(HttpServletRequest req,
                          @RequestParam(value = "coactee") String coactee) {

        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if (FriendshipDAO.addBlackListItem(userid, coactee)) {
            NoticeDAO.deleteSomebodyNotice(coactee, userid);
            FriendshipDAO.deleteFriendship(userid, coactee);
            ChatDAO.deleteSomeoneMessage(userid,coactee);
            NoticeDAO.sendNotice(userid, coactee, "你被我拉黑了，再见", "addedtoblicklist");
            result.setResult("SUCCESS");
        } else {
            result.setReason("E_ADDBLACKLIST_FAILED");
            result.setResult("ERROR");
        }
        return result;
    }

    @RequestMapping(value = "checkBlackList",method = RequestMethod.POST)
    @ResponseBody
    public Object checkBlackList(HttpServletRequest req,
                               @RequestParam(value = "coactee") String coactee) {

        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if(FriendshipDAO.existBlacklistItem(userid,coactee)){
            result.setResult("SUCCESS");
            result.setData(true);
        }else{
            result.setResult("SUCCESS");
            result.setData(false);
        }
        return result;
    }

    @RequestMapping(value = "addFriend",method = RequestMethod.POST)
    @ResponseBody
    public Object addFriend(HttpServletRequest req,
                          @RequestParam(value = "friendid") String friendid) {
        String userid = ControllerUtil.getUidFromReq(req);
        ResultInfo result = new ResultInfo();
        if (FriendshipDAO.addFriendship(userid,friendid)) {
            result.setResult("SUCCESS");
        } else {
            result.setReason("E_ADD_FAILED");
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
}