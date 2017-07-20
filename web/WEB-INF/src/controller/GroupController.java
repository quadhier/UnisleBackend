package controller;

import com.sun.corba.se.spi.transport.CorbaAcceptor;
import com.sun.org.apache.regexp.internal.RE;
import converter.GroupMember;
import converter.ResultInfo;
import dao.ActivityDAO;
import dao.CommonDAO;
import dao.GroupDAO;
import dao.UserInfoDAO;
import entity.ActivityEntity;
import entity.UgroupEntity;
import entity.UuserEntity;
import org.hibernate.usertype.UserCollectionType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.jvm.hotspot.runtime.ResultTypeFinder;
import util.ControllerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.ColorType;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by qudaohan on 2017/7/19.
 */



@Controller
@RequestMapping("/group")
public class GroupController {



    // 获取一个组织的信息
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object getGroupInfo(@RequestParam(value = "groupid", required = false) String groupid ,
                               HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        UgroupEntity group = (UgroupEntity) CommonDAO.getItemByPK(UgroupEntity.class, groupid);
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
    public Object createGroup(@RequestParam(value = "groupName", required = false) String groupName,
                              @RequestParam(value = "tag", required = false) String tag,
                              @RequestParam(value = "school", required = false) String school,
                              @RequestParam(value = "department", required = false) String department,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "groupPic", required = false) CommonsMultipartFile userPic,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");



        String userid = ControllerUtil.getUidFromReq(request);
        String groupid = GroupDAO.createGroup(userid, groupName, tag, school, department, description, null);
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
    public Object saveGroup(@RequestParam(value = "groupid", required = false) String groupid,
                            @RequestParam(value = "name") String name,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "school", required = false) String school,
                            @RequestParam(value = "department", required = false) String department,
                            @RequestParam(value = "description", required = false) String description,
                            HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 权限检查，只有管理员可以更改组织的信息
        String userid = ControllerUtil.getUidFromReq(request);
        String position = GroupDAO.getPositionInGroup(userid, groupid);
        if(position.equals("director") == false && position.equals("manager") == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(true)//GroupDAO.updateGroupInfo(groupid, name, tag, school, department, description) == false)
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
    public Object dismissGroup(@RequestParam(value = "groupid") String groupid,
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
    public Object joinGroup(@RequestParam(value = "groupid") String groupid,
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
    public Object inviteMember(@RequestParam(value = "groupid") String groupid,
                               @RequestParam(value = "member") String member,
                               HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);

        return null;

    }



    // 从组织中踢出成员
    @RequestMapping(value = "/kickout", method = RequestMethod.POST)
    @ResponseBody
    public Object kickOutMember(@RequestParam(value = "groupid") String groupid,
                                @RequestParam(value = "member") String member,
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
        String postion = GroupDAO.getPositionInGroup(userid, groupid);
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


    // Helper Function
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
    public Object assignManager(@RequestParam(value = "groupid") String groupid,
                                @RequestParam(value = "members") String members[],
                                HttpServletRequest request) {

        String userid = ControllerUtil.getUidFromReq(request);
        return alterPosition(userid, groupid, members, "manager");
    }


    // 撤销消管理员
    @RequestMapping(value = "/resign", method = RequestMethod.POST)
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
    public Object handoverDirector(@RequestParam(value = "groupid") String groupid,
                                   @RequestParam(value = "member") String member,
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


    // 发布动态
    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    @ResponseBody
    public Object publishActivity(@RequestParam(value = "content") String content,
                                  @RequestParam(value = "groupid") String groupid,
                                  HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 权限检查，只有群主或者管理员能够发布动态
        String userid = ControllerUtil.getUidFromReq(request);
        String position = GroupDAO.getPositionInGroup(userid, groupid);
        if(position.equals("director") == false && position.equals("manager") == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        ActivityDAO.groupPublishActivity(groupid, content, null);
        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 删除动态
    @RequestMapping(value = "/activity/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteActivity(@RequestParam(value = "activityid") String activityid,
                                  HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 获取发布动态的组织id
        ActivityEntity activityEntity = (ActivityEntity) CommonDAO.getItemByPK(ActivityEntity.class, activityid);
        String groupid = activityEntity.getPublisher();

        // 权限检查，只有群主或者管理员能够删除组织动态
        String userid = ControllerUtil.getUidFromReq(request);
        String position = GroupDAO.getPositionInGroup(userid, groupid);
        if(position.equals("director") == false && position.equals("manager") == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(ActivityDAO.deleteActivity(activityid) == false) {
            rinfo.setReason("E_NOT_DELETED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // Not Finished
    // 发布通知
    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    @ResponseBody
    public Object publishNotice(@RequestParam(value = "content") String content,
                                @RequestParam(value = "groupid") String groupid,
                                HttpServletRequest request) {


        return null;
    }


    // 通过组名搜索组织
    @RequestMapping(value = "/byname", method = RequestMethod.GET)
    @ResponseBody
    public Object searchGroupByName(@RequestParam(value = "groupName") String groupName,
                             HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();

        List groups = GroupDAO.searchGroupByName(groupName);
        rinfo.setResult("SUCCESS");
        rinfo.setData(groups);
        return rinfo;
    }

    // 通过组织标签搜索组织
    @RequestMapping(value = "/bytag", method = RequestMethod.GET)
    @ResponseBody
    public Object seachGroupByTag(@RequestParam(value = "tag") String tag,
                                  HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();

        List groups = GroupDAO.searchGroupByTag(tag);
        rinfo.setResult("SUCCESS");
        rinfo.setData(groups);
        return rinfo;
    }

    // 改变组织的可见性
    @RequestMapping(value = "/altervisibility", method = RequestMethod.POST)
    @ResponseBody
    public Object alterGroupVisibility(@RequestParam(value = "groupid") String groupid,
                                       @RequestParam(value = "visibility") String visibility,
                                       HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(GroupDAO.isInGroup(userid, groupid) == false) {
            rinfo.setReason("E_NOT_MEMBER");
            return rinfo;
        }

        if(GroupDAO.alterVisibility(userid, groupid, visibility) == false) {
            rinfo.setReason("E_NOT_CHANGED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 获取用户设置为可见的加入的组织
    @RequestMapping(value = "/useradded", method = RequestMethod.GET)
    @ResponseBody
    public Object getGroupAdded(@RequestParam(value = "viewedUserid") String viewedUserid,
                                HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");

        String userid = ControllerUtil.getUidFromReq(request);
        if(userid.equals(viewedUserid) == true) {
            rinfo.setData(GroupDAO.showGroupsYouAdded(viewedUserid, "all"));
            return rinfo;
        } else {
            rinfo.setData(GroupDAO.showGroupsYouAdded(viewedUserid, "visible"));
            return rinfo;
        }

    }

    // 获取组织的所有成员
    @RequestMapping(value = "/allmembers", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllMembers(@RequestParam("allmembers") String groupid,
                                HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setData("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(GroupDAO.isInGroup(userid, groupid) == false) {
            rinfo.setReason("E_NOT_MEMBER");
            return rinfo;
        }

        List<String> memberids = GroupDAO.getAllMembersID(groupid);
        List<GroupMember> members = new ArrayList<GroupMember>();
        UuserEntity user = null;
        GroupMember member = null;
        for(String memberid : memberids) {
            user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class, memberid);
            member = new GroupMember();
            member.setUserid(user.getUserid());
            member.setNickname(user.getNickname());
            member.setRealname(user.getRealname());
            member.setSex(user.getSex());
            member.setSchool(user.getSchool());
            member.setDepartment(user.getDepartment());
            member.setGrade(user.getGrade());
            member.setUserPic(user.getUserpic());
            members.add(member);
        }
        rinfo.setResult("SUCCESS");
        rinfo.setData(members);
        return rinfo;

    }


}
