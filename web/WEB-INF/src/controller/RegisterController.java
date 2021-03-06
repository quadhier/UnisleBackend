package controller;

import converter.ResultInfo;
import dao.UserInfoDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import util.*;

/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/signup")
public class RegisterController {

    private String vcode;

    // 发送注册信息等待验证
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody()
    public Object register(@RequestParam(value = "userAccount", required = false) String userAccount,
                           @RequestParam(value = "validationCode", required = false) String validationCode,
                           @RequestParam(value = "nickname", required = false) String nickname,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "sex", required = false) String sex,
                           @RequestParam(value = "birthday", required = false) String birthday,
                           @RequestParam(value = "realname", required = false) String realname,
                           @RequestParam(value = "school", required = false) String school,
                           @RequestParam(value = "grade", required = false) String grade) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");
        if(vcode == null) {
            vcode = "";
        }

        // If information is not complete
        if(userAccount == null || userAccount.equals("") ||
                nickname == null || nickname.equals("") ||
                password == null || password.equals("") ||
                validationCode == null || validationCode.equals("")) {
            rinfo.setResult("ERROR");
            rinfo.setReason("E_INCOMPELETE_INFO");
        }
        else {
            if(UserInfoDAO.seekReuseEmail(userAccount)) {
                rinfo.setReason("E_ACCOUNT_USED");
            }
            else if(!validationCode.equals(vcode)) {
                rinfo.setReason("E_WRONG_VCODE");
            }
            else {
                birthday = ControllerUtil.normalizeTime(birthday);
                UserInfoDAO.createUser(userAccount, nickname, password, sex, birthday, realname, school, grade);
                rinfo.setResult("SUCCESS");
            }
        }
        vcode = null;
        return rinfo;
    }

    // 通过此URL获取验证码，通过手机或者邮箱接收
    @RequestMapping(value = "/vcode", method = RequestMethod.POST)
    @ResponseBody
    public Object sendVCode(@RequestParam(value = "contact", required = false) String contact) {

        vcode = ControllerUtil.genVCode();
        if(contact.contains("@")) {
            ControllerUtil.sendEmail(contact, vcode);
        }
        else
            ControllerUtil.sendText(contact, vcode);
        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("Finished");
        return rinfo;
    }

}
