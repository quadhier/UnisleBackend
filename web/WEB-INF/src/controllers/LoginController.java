package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import models.*;
import sun.misc.Request;

/**
 * Created by qudaohan on 2017/7/8.
 */

@Controller
@RequestMapping("/token")
public class TokenController {

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam("userid") String userid,
                        @RequestParam("password") String password) {

        if (useridExists(userid) == true && getPassword(userid).equals(password)) {
            return "Login Success";
        }

        return "User Does Not Exist Or Password Wrong";
    }

    @RequestMapping("/findpassword")
    @ResponseBody
    public String findpassword() {

    }

    boolean useridExists(String userid) {
        return true;
    }

    String getPassword(String userid) {
        return "12345";
    }



}
