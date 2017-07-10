package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import models.*;

/**
 * Created by qudaohan on 2017/7/8.
 */

@Controller
@RequestMapping("/")
public class HelloController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String returnLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        if(user.getName() == "haha" && user.getPassword() == "xixi") {
            return "login";
        }
        else
            return "error";
    }



}
