package controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.HttpResponse;
import entity.ActivityEntity;
import entity.ActivitycommentEntity;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;

import dao.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by qudaohan on 2017/7/8.
 */

@Controller
@RequestMapping("/")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam("userAccount") String userAccount,
                        @RequestParam("password") String password,
                        HttpServletResponse response) {

        // If userid and password both are valid
        if(userAccount != null && password != null && UserInfoDAO.validateUser(userAccount, password)) {

            // generate token and add token id to cookie
            String userid = UserInfoDAO.getUserID(userAccount);
            String tokenid = createToken(userid);
            UserInfoDAO.saveToken(tokenid, userid);
            Cookie tokenCookie = new Cookie("tokenid", tokenid);
            response.addCookie(tokenCookie);
            ModelAndView modelView = new ModelAndView("home");
            return modelView;
        }
        // If userid is invalid of password is not correct
        else
        {
            return "E_WRONG_USER_OR_PASSWD";
        }
    }

    private String createToken(String userid) {
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        return userid + String.valueOf(currentTime);
    }

}
