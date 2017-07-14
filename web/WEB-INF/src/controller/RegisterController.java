package controller;

import com.sun.org.apache.regexp.internal.RE;
import dao.UserInfoDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;

import util.*;

/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/signup")
public class RegisterController {

    private String vcode;

    // 获取注册页面
    @RequestMapping(method = RequestMethod.GET)
    public String getRegisterPage() {
        System.out.println("This is signup page");
        return "signup";
    }

    // 发送注册信息等待验证
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestParam(value = "userAccount", required = false) String userAccount,
                           @RequestParam(value = "validationCode", required = false) String validationCode,
                           @RequestParam(value = "nickname", required = false) String nickname,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "sex", required = false) String sex,
                           @RequestParam(value = "birthday", required = false) String birthday,
                           @RequestParam(value = "realname", required = false) String realname,
                           @RequestParam(value = "school", required = false) String school,
                           @RequestParam(value = "grade", required = false) String grade) {

        // If information is not complete
        if(userAccount == null || nickname == null || password == null || vcode == null) {

            return "E_INCOMPELETE_INFO";
        }
        else {
            if(UserInfoDAO.seekReuseEmail(userAccount)) {
                return "E_ACCOUNT_USED";
            }
            else if(validationCode == null && !validationCode.equals(vcode))
            {
                return "E_WRONG_VCODE";
            }
            else {
                UserInfoDAO.createUser(userAccount, nickname, password, sex, birthday, realname, school, grade);
                ModelAndView modelview = new ModelAndView("login");
                return modelview;
            }
        }
    }

    // 通过此URL获取验证码，通过手机或者邮箱接收
    @RequestMapping(value = "/vcode", method = RequestMethod.POST)
    @ResponseBody
    public Object sendVCode(@RequestParam("contact") String contact) {

        vcode = ControllerUtil.genVCode();
        if(contact.contains("@")) {
            ControllerUtil.sendEmail(contact, vcode);
        }
        else
            ControllerUtil.sendText(contact, vcode);
        return "SUCCESS";
    }

}
