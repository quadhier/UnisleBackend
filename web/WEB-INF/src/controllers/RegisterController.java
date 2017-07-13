package controllers;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by qudaohan on 2017/7/12.
 */


@Controller
@RequestMapping("/register")
public class RegisterController {

    String verificationCode;

    @RequestMapping(method = RequestMethod.GET)
    public String getRegisterPage() {
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("type") String type,
                           @RequestParam("contact") String contact,
                           @RequestParam("vcode") String vcode,
                           @RequestParam("nickname") String nickname,
                           @RequestParam("password") String password,
                           @RequestParam("sex") String sex,
                           @RequestParam("birthday") String birthday,
                           @RequestParam("realnaem") String realname,
                           @RequestParam("school") String school,
                           @RequestParam("department") String department,
                           @RequestParam("grade") String grade) {
        if(!vcode.equals(verificationCode))
            return "Wrong Verification Code";
        else
            return "Register Success";
    }



//    @RequestMapping(value = "/sendvcode", method = RequestMethod.POST)
//    public void SendVCode(@RequestParam("type") String type,
//                          @RequestParam("contact") String contact) {
//        verificationCode = generateVCode();
//        if(type.equals("email")) {
//            sendEmail(contact);
//        }
//        else {
//            sendText(contact);
//        }
//    }

}
