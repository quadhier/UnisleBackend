package controller;

import entity.ActivityEntity;
import entity.ActivitycommentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/activity")
public class ActivityController {

//    // Add Activities of user
//    ActivityEntity[] activities = getActivities(userid, "all", "1980-01-01", 10);
//    HashMap<String, Object> comments = new HashMap<>();
//            for(ActivityEntity activity : activities) {
//        String actid = activity.getActivityid();
//        ActivitycommentEntity[] actComments = getActivityComments(actid);
//        comments.put(actid, actComments);
//    }
//            modelview.addObject("activities", activities);
//            modelview.addObject("comments", comments);
//

}
