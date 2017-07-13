package controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.portlet.ModelAndView;

import util.*;

/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/register")
public class RegisterController {

    private String vcode;

    @RequestMapping(method = RequestMethod.GET)
    public String getRegisterPage() {
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("userAccount") String userAccount,
                                 @RequestParam("validationCode") String validationCode,
                                 @RequestParam("nickname") String nickname,
                                 @RequestParam("password") String password,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("birthday") String birthday,
                                 @RequestParam("realname") String realname,
                                 @RequestParam("school") String school,
                                 @RequestParam("grade") String grade) {

        ModelAndView modelview = new ModelAndView();

        // If information is not complete
        if(userAccount == null || nickname == null || password == null) {
            modelview.setViewName("error");
            modelview.addObject("errorType", "E_INCOMPELETE_INFO");
        }
        else {
            if(accountExist(userAccount)) {
                modelview.setViewName("error");
                modelview.addObject("errorType", "E_ACCOUNT_USED");
            }
            if(validationCode == null && !validationCode.equals(vcode))
            {
                modelview.setViewName("error");
                modelview.addObject("errorType", "E_WRONG_VCODE");
            }
            else {
                createUser(userAccount, nickname, password, sex, birthday, realname, school, grade);
                modelview.setViewName("login");
            }

            return modelview;
        }
    }

    @RequestMapping(value = "/vcode", method = RequestMethod.POST)
    public void sendVCode(@RequestParam("contact") String contact) {

        vcode = ControllerUtil.genVCode();
        if(contact.contains("@")) {
            ControllerUtil.sendEmail(vcode);
        }
        else
            ControllerUtil.sendText(vcode);
    }

}
