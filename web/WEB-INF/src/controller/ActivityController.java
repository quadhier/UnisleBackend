package controller;

import converter.ActivityForward;
import dao.ActivityDAO;
import dao.CommonDAO;
import dao.UserInfoDAO;
import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import converter.ActivityAndComment;
import converter.ActivityCreation;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import util.ControllerUtil;
import converter.ResultInfo;


import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.rmi.activation.ActivationID;
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
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object createActivity(@RequestParam(value = "content") String content,
                                 @RequestParam(value = "shieldIDList", required = false) String[] shieldIDList,
                                 @RequestParam(value = "picture", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile picture,
                                 HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");


        System.out.println("Content:");
        System.out.println(content);
        System.out.println("ShieldIDList:");
        if(shieldIDList != null) {
            for(String shield : shieldIDList) {
                System.out.println(shield);
            }
        }

        String userid = ControllerUtil.getUidFromReq(request);
        System.out.println("Start Creating");

        List<String> shields = null;
        if(shieldIDList != null && shieldIDList.length != 0) {
            shields = ControllerUtil.arrToList(shieldIDList);
            System.out.println("Shields Constructed");
        }

        if(content == null && content.length() == 0) {

            rinfo.setReason("E_INCOMPLETE_CONTENT");
            return rinfo;
        }

        // 在数据库中储存动态数据并获取动态id
        String activityid = ActivityDAO.publishActivity(userid, shields, content, null);
        System.out.println("Activity Constructed");

        // 利用动态id作为图片名称并将相对路径储存进数据库
        String filepath = null;
        if(picture != null) {
            byte[] bytePic = picture.getBytes();
            filepath = ControllerUtil.storeFile(bytePic, activityid, null, "activitypic");
            ActivityEntity activity = (ActivityEntity) CommonDAO.getItemByPK(ActivityEntity.class, activityid);
            activity.setAttachment(filepath);
            CommonDAO.updateItem(ActivityEntity.class, activityid, activity);
        }

        class ActRes {
            public String activityid;
            public String actpic;
        }

        ActRes actRes = new ActRes();
        actRes.activityid = activityid;
        actRes.actpic = filepath;

        rinfo.setResult("SUCCESS");
        rinfo.setData(actRes);
        return rinfo;

    }



    // 删除一条动态
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteActivity(@RequestParam("activityid") String activityid,
                                 HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 确保只有动态发布者自身能删除动态
        String user = ControllerUtil.getUidFromReq(request);
        if(ActivityDAO.getAuthorID(activityid).equals(user) == false) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(ActivityDAO.deleteActivity(activityid) == false) {
            rinfo.setReason("E_NOT_DELETE");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 转发动态
    // 如果将request作为第一个参数，则会报错，目前不清楚原因
    @RequestMapping(value = "/forward", method = RequestMethod.POST)
    @ResponseBody
    public Object forwardActivity(@RequestParam("shieldList[]") String[] shieldIDList,
                                  @RequestParam("orgActivityID") String orgActivityID,
                                  HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        List<String> shields = null;
        if(shieldIDList != null && shieldIDList.length != 0)
            shields = ControllerUtil.arrToList(shieldIDList);

        String factid = ActivityDAO.forwardActivity(userid, shields, orgActivityID);
        if(factid == null || factid.length() == 0) {
            rinfo.setReason("E_NOT_FORWARDED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        rinfo.setData(factid);
        return rinfo;
    }



    // 一次一条的获取所有动态
    @RequestMapping(value = "/all/{isfirst}", method = RequestMethod.GET)
    @ResponseBody
    public Object allActivities(HttpServletRequest request,
                                @RequestParam(value = "lastTime", required = false) String lTime,
                                @RequestParam(value = "view", required = false) String view,
                                @PathVariable(value = "isfirst") String isfirst){

        synchronized(this) {

            // 初始化返回对象
            ResultInfo rinfo = new ResultInfo();
            rinfo.setResult("ERROR");

            String tokenid = ControllerUtil.getTidFromReq(request);
            Timestamp time = null;
            if (isfirst.equals("isfirst")) {
                System.out.println("isfirst");
                System.out.println("isfirst");
                System.out.println("isfirst");
                UserInfoDAO.updateViewActTime(tokenid, System.currentTimeMillis());
                time = new Timestamp(System.currentTimeMillis());
            } else {

                System.out.println("followed");
                System.out.println("followed");
                System.out.println("followed");

                try {
                    System.out.println("tokenid" + tokenid);
                    long lastTime = UserInfoDAO.getViewActTimeByToken(tokenid);
                    System.out.println("lastTime" + lastTime);
                    time = new Timestamp(lastTime - 5);
                } catch (Exception e) {
                    e.printStackTrace();
                    rinfo.setReason("E_MALFORMED_TIME");
                    return rinfo;
                }
            }

            // 默认为显示所有动态
            if (view == null || (!view.equals("friend") && !view.equals("self"))) {
                view = "friend";
            }

            String userid = UserInfoDAO.getUserByToken(tokenid);

            System.out.println(time);
            System.out.println(view);
            System.out.println(userid);

            // 获取一条动态，其发布用户的昵称，所有评论，以及评论者昵称

            ActivityEntity[] activities = ActivityDAO.getActivities(userid, time, 1, view);

            if (activities == null || activities.length == 0) {
                System.out.println("Non Activity");
                rinfo.setReason("E_NO_MORE_ACTIVITY");
                return rinfo;
            }

            ActivityEntity activity = activities[0];
            String ownerid = activity.getPublisher();
            UuserEntity owner = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, ownerid);
            String ownerName = owner.getNickname();

            ActivitycommentEntity[] comments = ActivityDAO.getActivityComments(activity.getActivityid());
            ArrayList<String> commenterList = new ArrayList<String>();
            ArrayList<String> commenteridList = new ArrayList<String>();
            ArrayList<Long> publishtimeList = new ArrayList<Long>();

            UuserEntity user = null;
            for (ActivitycommentEntity comment : comments) {
                user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, comment.getActivitycommentEntityPK().getUserid());
                commenterList.add(user.getNickname());
                commenteridList.add(user.getUserid());
                publishtimeList.add(comment.getActivitycommentEntityPK().getPublicdatetime().getTime());
            }
            String[] commenters = commenterList.toArray(new String[0]);
            String[] commenterids = commenteridList.toArray(new String[0]);
            Long[] publishtimes = publishtimeList.toArray(new Long[0]);



            ActivityAndComment actAndCom = new ActivityAndComment();

            ProEntityPK propk = new ProEntityPK();
            propk.setActivityid(activity.getActivityid());
            propk.setUserid(userid);
            if(CommonDAO.getItemByPK(ProEntity.class, propk) != null) {
                actAndCom.setPro(true);
            } else {
                actAndCom.setPro(false);
            }

            rinfo.setResult("SUCCESS");
            actAndCom.setUserName(ownerName);
            actAndCom.setActivity(activity);
            actAndCom.setComments(comments);
            actAndCom.setCommenters(commenters);
            actAndCom.setCommenterids(commenterids);
            actAndCom.setPublishtimes(publishtimes);
            rinfo.setData(actAndCom);

            UserInfoDAO.updateViewActTime(tokenid, activity.getPublicdatetime().getTime());

            System.out.println("current activity time" + activity.getPublicdatetime().getTime());

            return rinfo;
        }

    }


    // 点赞
    @RequestMapping(value = "/pro",method = RequestMethod.POST)
    @ResponseBody
    public Object like(@RequestParam(value = "activityid", required = false) String activtyid,
                       HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);

        if(userid == null || activtyid == null)
        {
            rinfo.setReason("E_INCOMPLETE_INFO");
            return rinfo;
        }

        ActivityDAO.makePro(activtyid, userid);
        rinfo.setResult("SUCCESS");
        return rinfo;

    }

    // 取消点赞
    @RequestMapping(value = "/pro/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object dislike(@RequestParam(value = "activityid", required = false) String activityid,
                          HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);

        if(userid == null || activityid == null) {
            if(userid == null)
                rinfo.setReason("E_LACK_OF_USERID");
            else
                rinfo.setReason("E_LACK_OF_ACTID");
            return rinfo;
        }

        if(ActivityDAO.cancelPro(activityid, userid) == true)
            rinfo.setResult("SUCCESS");

        return rinfo;
    }


    // 评论
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object makeComment(@RequestParam(value = "activityid", required = false) String activityid,
                              @RequestParam(value = "content", required = false) String content,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);

        if(activityid == null || userid == null) {
            rinfo.setReason("E_INCOMPLETE_INFO");
            return rinfo;
        }

        Timestamp comTime =  ActivityDAO.publishComment(activityid, userid, content);

        if(comTime == null) {
            rinfo.setReason("E_NOT_COMMENT");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        rinfo.setData(comTime.getTime());
        return rinfo;
    }



    // 删除评论
    @RequestMapping(value = "/comment/delete", method = RequestMethod.POST)
    @ResponseBody Object deleteComment(@RequestParam(value = "activityid", required = false) String activityid,
                                       @RequestParam(value = "publisher", required = false) String publisher,
                                       @RequestParam(value = "publishdatetime", required = false) long publishdatetime,
                                       HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");
        String userid = ControllerUtil.getUidFromReq(request);


        if(activityid == null || publisher == null || userid == null) {
            rinfo.setReason("E_INCOMPLETE_INFO");
            return rinfo;
        }

        if(ActivityDAO.whetherCanDeleteComment(userid, publisher, activityid) == true) {
            ActivitycommentEntityPK commentpk = new ActivitycommentEntityPK();
            commentpk.setActivityid(activityid);
            commentpk.setUserid(publisher);
            commentpk.setPublicdatetime(new Timestamp(publishdatetime));
            ActivityDAO.deleteActivityComment(commentpk);
            rinfo.setResult("SUCCESS");
            return rinfo;
        }
        rinfo.setReason("E_INSUFFICIENT_PERMISSION");
        return rinfo;
    }


}