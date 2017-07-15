package controller;

import converter.ActivityForward;
import dao.ActivityDAO;
import dao.CommonDAO;
import dao.UserInfoDAO;
import entity.ActivityEntity;
import entity.ActivitycommentEntity;
import entity.UuserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import converter.ActivityAndComment;
import converter.ActivityCreation;
import util.ControllerUtil;
import converter.ResultInfo;


import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/activity")
public class ActivityController {

    // 创建一条动态
    // 如果将request作为第一个参数，则会报错，目前不清楚原因
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public String createActivity(@RequestBody ActivityCreation activityCreation,
                                 HttpServletRequest request) {

        String[] shieldIDList = activityCreation.getShieldIDList();
        String content = activityCreation.getContent();
        byte[] attachment = activityCreation.getAttachment();

        System.out.println(shieldIDList);
        System.out.println(content);
        System.out.println(attachment);

        String userid = ControllerUtil.getUidFromReq(request);
        System.out.println("Start Creating");
        List<String> shields = null;
        if(shieldIDList != null && shieldIDList.length != 0) {
            shields = ControllerUtil.arrToList(shieldIDList);
            System.out.println("Shields Constructed");
        }
        if((content != null && content.length() != 0) || (attachment != null && attachment.length != 0)) {

            ActivityDAO.publishActivity(userid, shields, content, null);
            System.out.println("Activity Constructed");
        }
        return "redirect:/activity/all";
    }

    // 删除一条动态
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Object getActivity(HttpServletRequest request,
                              @RequestParam("activityid") String activityid) {

        ResultInfo rinfo = new ResultInfo();
        if(ActivityDAO.deleteActivity(activityid) == true) {
            rinfo.setResult("SUCCESS");
        } else {
            rinfo.setResult("ERROR");
            rinfo.setReason("E_NOT_DELETE");
        }
        return rinfo;
    }

    // 转发动态
    // 如果将request作为第一个参数，则会报错，目前不清楚原因
    @RequestMapping(value = "/forward", method = RequestMethod.POST)
    public String forwardActivity(@RequestBody ActivityForward activityForward,
                                  HttpServletRequest request) {

        String[] shieldIDList = activityForward.getShieldIDList();
        String orgActivityID = activityForward.getOriginalActivityID();

        String userid = ControllerUtil.getUidFromReq(request);
        List<String> shields = null;
        if(shieldIDList != null && shieldIDList.length != 0)
            shields = ControllerUtil.arrToList(shieldIDList);
        ActivityDAO.forwardActivity(userid, shields, orgActivityID);
        return "redirect:/activity/all";
    }


    // 一次一条的获取所有动态
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object allActivities(HttpServletRequest request,
                                @RequestParam(value = "lastTime", required = false) String lastTime,
                                @RequestParam(value = "view", required = false) String view ){

        // 默认为当前时间
        Timestamp time = null;
        try {
            time = new Timestamp(Long.valueOf(lastTime));

        } catch (Exception e) {
            time = new Timestamp(System.currentTimeMillis());
        }

        // 默认为显示所有动态
        if(view == null || view.length() == 0)
        {
            view = "self";
        }

        //System.out.println(System.currentTimeMillis());
        //System.out.println(time);

        String tokenid = ControllerUtil.getTidFromReq(request);
        String userid = UserInfoDAO.getUserByToken(tokenid);

        // 初始化返回对象
        ActivityAndComment actAndCom = new ActivityAndComment();

        // 获取一条动态，其发布用户的昵称，所有评论，以及评论者昵称

        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);
        ActivityEntity[] activities = ActivityDAO.getActivities(userid, time, 1, view);

        if(activities == null || activities.length == 0) {
            System.out.println("Non Activity");
            actAndCom.setTag(false);
            return actAndCom;
        }
        String userName = user.getNickname();
        ActivityEntity activity = activities[0];
        ActivitycommentEntity[] comments = ActivityDAO.getActivityComments(activity.getActivityid());
        ArrayList<String> commenterArray = new ArrayList<String>();

        for(ActivitycommentEntity comment : comments) {
             user =  (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class, comment.getActivitycommentEntityPK().getUserid());
             commenterArray.add(user.getNickname());
        }
        String[] commenters = commenterArray.toArray(new String[0]);

        actAndCom.setTag(true);
        actAndCom.setUserName(userName);
        actAndCom.setActivity(activity);
        actAndCom.setComments(comments);
        actAndCom.setCommenters(commenters);

        return actAndCom;

    }

}