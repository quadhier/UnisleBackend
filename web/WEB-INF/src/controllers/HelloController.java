package controllers;

import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import models.*;

/**
 * Created by qudaohan on 2017/7/8.
 */

@Controller
@RequestMapping("/")
public class HelloController {

    @RequestMapping("/")
    public String returnHomePage() {
        return "index";
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST)
    @ResponseBody
    public Object returnJson(@RequestParam("username") String name,
                             @RequestParam("password") String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return user;
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public User returnEntity(@RequestBody User user) {
        return user;
    }

}
