package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;

import dao.*;
import util.*;

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
        return "login.html";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam(value = "userAccount", required = false) String userAccount,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletResponse response) {

        ResultInfo rinfo = new ResultInfo();

        // 用户名和密码均有效
        if(userAccount != null && password != null && UserInfoDAO.validateUser(userAccount, password)) {

            // generate token and add token id to cookie
            String userid = UserInfoDAO.getUserID(userAccount);
            String tokenid = createToken(userid);
            UserInfoDAO.saveToken(tokenid, userid);
            Cookie tokenCookie = new Cookie("tokenid", tokenid);
            response.addCookie(tokenCookie);
            rinfo.setResult("LOGIN_SUCCESS");
            return rinfo;
        }
        // 用户名不存在或密码错误
        else
        {
            rinfo.setResult("ERROR");
            rinfo.setReason("E_WRONG_USER_OR_PASSWD");
            return rinfo;
        }
    }

    // 产生一个token并且返回
    private String createToken(String userid) {
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        return userid + String.valueOf(currentTime);
    }

}
