package controller;

import dao.UserInfoDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by qudaohan on 2017/7/13.
 */


@Controller
@RequestMapping("/logout")
public class LogoutController {

    // 注销用户并且使token无效
    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        String tokenid = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("tokenid")) {
                tokenid = cookie.getValue();
                break;
            }
        }
        if(tokenid != null)
            UserInfoDAO.deleteToken(tokenid);
        return "/";
    }

}
