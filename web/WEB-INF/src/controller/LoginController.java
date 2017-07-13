package controllers;

import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import entity.*;
import org.springframework.web.portlet.ModelAndView;
import org.w3c.dom.html.HTMLModElement;
import sun.misc.Request;
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
    public ModelAndView login(@RequestParam("userid") String userid,
                              @RequestParam("password") String password) {

        ModelAndView modelview = null;
        // If userid and password both are valid
        if(userid != null && password != null && validatePassword(userid, password)) {

            modelview = new ModelAndView("home");
            // Add Activities of user
            HashMap activities = new HashMap();
            activities.put();
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


}
