package controller;

import com.sun.corba.se.spi.transport.CorbaAcceptor;
import com.sun.org.apache.regexp.internal.RE;
import converter.ResultInfo;
import org.hibernate.usertype.UserCollectionType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import util.ControllerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.ColorType;
import java.security.acl.Group;

/**
 * Created by qudaohan on 2017/7/19.
 */



@Controller
@RequestMapping("/group")
public class GroupController {



    // 返回一个组织的信息
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object getGroupInfo(@RequestParam("groupid") String groupid ,
                               HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        GroupEntity group = GroupDAO.getGroupByID(groupid);
        if(group == null) {
            rinfo.setReason("E_INVALID_GROUPID");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        rinfo.setData(group);
        return rinfo;
    }

    // 创建一个组织
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object createGroup(@RequestParam("groupName") String groupName,
                              @RequestParam("tag") String tag,
                              @RequestParam("school") String school,
                              @RequestParam("department") String department,
                              @RequestParam("description") String description,
                              @RequestParam("groupPic") CommonsMultipartFile userPic,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        String groupid = GroupDAO.createGroup(userid, groupName, tag, school, department, description, userPic);
        if(groupid == null || groupid.equals("")) {
            rinfo.setReason("E_NOT_CREATED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        rinfo.setData(groupid);
        return rinfo;
    }


    // 更新一个组织的信息
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object saveGroup(@RequestParam("groupid") String groupid,
                            @RequestParam("name") String name,
                            @RequestParam("tag") String tag,
                            @RequestParam("school") String school,
                            @RequestParam("department") String department,
                            @RequestParam("description") String description,
                            HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 权限检查，只有管理员可以更改组织的信息
        String userid = ControllerUtil.getUidFromReq(request);
        String position = GroupDAO.getPositionInGroup(userid);
        if(position.equals("director") == false && position.equals("manager") == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(GroupDAO.updateGroupInfo(groupid, name, tag, school, department, description) == false)
        {
            rinfo.setReason("E_NOT_UPDATED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 解散一个组织
    @RequestMapping(value = "/dismiss", method = RequestMethod.POST)
    @ResponseBody
    public Object dismissGroup(@RequestParam("groupid") String groupid,
                               HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 权限检查，只有群主可以解散一个组织
        String userid = ControllerUtil.getUidFromReq(request);
        if(GroupDAO.getDirectorID(groupid).equals(userid) == false)
        {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(GroupDAO.dismissGroup(groupid) == false) {
            rinfo.setReason("E_NOT_DISMISSED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;

    }


    // Not Finished
    // 申请加入一个组织
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ResponseBody
    public Object joinGroup(@RequestParam("groupid") String groupid,
                            HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);


        return null;

    }


    // Not Finished
    // 邀请成员
    @RequestMapping(value = "invite", method = RequestMethod.POST)
    @ResponseBody
    public Object inviteMember(@RequestParam("groupid") String groupid,
                               @RequestParam("member") String member,
                               HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);

        return null;

    }



    // 从组织中踢出成员
    @RequestMapping(value = "/kickout", method = RequestMethod.POST)
    @ResponseBody
    public Object kickOutMember(@RequestParam("groupid") String groupid,
                                @RequestParam("member") String member,
                                HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);

        // 确认被踢用户是组织成员
        if(GroupDAO.isInGroup(userid, groupid)) {
            rinfo.setReason("E_NOT_MEMBER");
            return rinfo;
        }

        // 权限检查，只有群主或者管理员可以踢出组员
        String postion = GroupDAO.getPositionInGroup(userid);
        if(postion.equals("director") == false && postion.equals("manager") == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(GroupDAO.kickoutMember(groupid, member) == false)
        {
            rinfo.setReason("E_NOT_KICKED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // helper function
    // 改变组员的职位
    private ResultInfo alterPosition(String userid, String groupid, String members[], String newPostion) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 权限检查，只有群主可以改变组员的职位
        String position = GroupDAO.getPositionInGroup(userid, groupid);
        if(position.equals("director") == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        // 确保列表中的memberid对应的用户皆为组织成员
        for(String member : members) {
            if(GroupDAO.isInGroup(member, groupid) == false)
            {
                rinfo.setReason("E_NOT_MEMBER");
                return rinfo;
            }
        }

        // 改变职位
        for(String member : members) {
            if(GroupDAO.alterPositionInGroup(groupid, member, newPostion) == false) {
                rinfo.setReason("E_PARTIALLY_CHANGED");
                return rinfo;
            }
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }

    // 分派管理员
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public Object assignManager(@RequestParam("groupid") String groupid,
                                @RequestParam("members") String members[],
                                HttpServletRequest request) {

        String userid = ControllerUtil.getUidFromReq(request);
        return alterPosition(userid, groupid, members, "manager");
    }


    // 撤销消管理员
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public Object resignManager(@RequestParam("groupid") String groupid,
                                @RequestParam("members") String members[],
                               HttpServletRequest request) {

        String userid = ControllerUtil.getUidFromReq(request);
        return alterPosition(userid, groupid, members, "member");
    }



    // 转让主管理者
    @RequestMapping(value = "/handover", method = RequestMethod.POST)
    @ResponseBody
    public Object handoverDirector(@RequestParam("groupid") String groupid,
                                   @RequestParam("member") String member,
                                   HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);

        // 权限检查，确保用户为群主
        String position = GroupDAO.getPositionInGroup(userid, groupid);
        if(position.equals("director") == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        // 确保被转让的用户为组织成员
        if(GroupDAO.isInGroup(member, groupid) == false) {
            rinfo.setReason("E_NOT_MEMBER");
            return rinfo;
        }

        if(GroupDAO.changeDirector(groupid, member) == false) {
            rinfo.setReason("E_NOT_CHANGED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // Not Finished
    // 发布动态
    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    @ResponseBody
    public Object publishActivity(@RequestParam("content") String content,
                                  @RequestParam("groupid") String groupid,
                                  HttpServletRequest request) {



        return null;
    }


    // Not Finished
    // 发布通知
    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    @ResponseBody
    public Object publishNotice(@RequestParam("content") String content,
                                @RequestParam("groupid") String groupid,
                                HttpServletRequest request) {


        return null;
    }


    // 搜索组织
    @RequestMapping(value = "/search", method = "")
    @ResponseBody
    public Object searchUser(@RequestParam(""))















}
