package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by qudaohan on 2017/7/13.
 */


@Controller
@RequestMapping("/logout")
public class LogoutController {

    public String logout(@RequestParam("tokenid") String tokenid) {
        deleteToken(tokenid);
        return "redirect:login";
    }

}
