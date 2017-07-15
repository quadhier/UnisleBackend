package controller;

import dao.ActivityDAO;
import dao.CommonDAO;
import dao.UserInfoDAO;
import entity.ActivityEntity;
import entity.ActivitycommentEntity;
import entity.UuserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ActivityAndComment;
import util.ControllerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.smartcardio.CommandAPDU;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/activities")
public class ActivityController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object allActivities(HttpServletRequest request,
                                @RequestParam(value = "lastTime", required = false) String lastTime){

        Timestamp time = null;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(lastTime);

        } catch (ParseException pe) {
            pe.printStackTrace();

            try {
                date = sdf.parse("1970-01-01 00:00:00");
            // 此处不应该捕获到错误
            } catch (ParseException e) {
                System.out.println("Fatal Error!");
                e.printStackTrace();
            }
        }

        time = new Timestamp(date.getTime());

        String tokenid = ControllerUtil.getTidFromReq(request);
        String userid = UserInfoDAO.getUserByToken(tokenid);

        // 获取一定数量的动态数组，建立与之对应的用户昵称数组
        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);
        //String view = user.getActivityvisibility();
        ActivityEntity[] activityEntities = ActivityDAO.getActivities(userid, new Timestamp(System.currentTimeMillis()), 2, "friend");

        System.out.println(userid);
        System.out.println(time);
        System.out.println(activityEntities);

        ArrayList<String> usernames = new ArrayList<String>();

        // 将动态id对应的用户名放入用户名数组链表中，同时将对应的评论数组储存在HaspMap中
        HashMap<String, ActivitycommentEntity[]> allActivityComments = new HashMap<String, ActivitycommentEntity[]>();
        for(ActivityEntity activity : activityEntities) {
            user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class, activity.getPublisher());
            usernames.add(user.getNickname());
            ActivitycommentEntity[] activityComments = ActivityDAO.getActivityComments(activity.getActivityid());
            allActivityComments.put(activity.getActivityid(), activityComments);
        }
        String[] names = (String [])usernames.toArray();

        ActivityAndComment actcom = new ActivityAndComment();
        actcom.setActivities(activityEntities);
        actcom.setAllComments(allActivityComments);
        actcom.setUsers(names);

        return actcom;

    }




}
