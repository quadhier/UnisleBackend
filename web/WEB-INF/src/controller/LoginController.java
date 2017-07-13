package controller;

import entity.ActivitycommentEntity;
import entity.OriginalactivityEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.portlet.ModelAndView;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by qudaohan on 2017/7/8.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage(@RequestParam String tokenid) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("userid") String userAccount,
                              @RequestParam("password") String password) {

        ModelAndView modelview = null;
        // If userid and password both are valid
        if(userAccount != null && password != null && validatePassword(userAccount, password)) {

            modelview = new ModelAndView("home");

            // Add Token
            String userid = getUserID(userAccount);
            String tokenid = createToken(userid);
            saveToken(tokenid, userid);
            modelview.addObject("tokenid", tokenid);

            // Add Activities of user
            ActivityEntity[] activities = getActivities("1980-01-01", 10);
            HashMap<String, Object> comments = new HashMap<>();
            for(ActivityEntity activity : activities) {
                String actid = activity.getActivityID();
                ActivitycommentEntity[] actComments = getActivityComments(actid);
                comments.put(actid, actComments);
            }
            modelview.addObject("activities", activities);
            modelview.addObject("comments", comments);
            return modelview;
        }
        // If userid is invalid of password is not correct
        else
        {
            modelview = new ModelAndView("error");
            modelview.addObject("errorType", "E_USER_OR_PASSWD");
            return modelview;
        }
    }

    private String createToken(String userid) {
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        return userid + String.valueOf(currentTime);
    }

    private boolean saveToken(String tokenid, String userid) {
        return true;
    }

    private boolean validatePassword(String userid, String password) {
        return true;
    }


}
