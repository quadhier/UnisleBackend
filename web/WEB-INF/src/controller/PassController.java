package controller;

import converter.ResultInfo;
import dao.CommonDAO;
import dao.UserInfoDAO;
import entity.UuserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ControllerUtil;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by qudaohan on 2017/7/25.
 */

@Controller
@RequestMapping("/password")
public class PassController {


    private String vcode;


    // 通过此URL获取验证码，通过手机或者邮箱接收
    @RequestMapping(value = "/vcode", method = RequestMethod.POST)
    @ResponseBody
    public Object sendVCode(@RequestParam(value = "contact", required = false) String contact) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(contact == null || contact.equals("")) {
            rinfo.setReason("E_NO_CONTACT");
            return rinfo;
        }

        String userid = UserInfoDAO.getUserID(contact);
        System.out.println(userid);
        if(userid == null) {
            rinfo.setReason("E_ACCOUNT_NOT_EXIST");
            return rinfo;
        }

        vcode = ControllerUtil.genVCode();

        //
        // 在新线程中发送邮件
        //
        Runnable emailRunnable = new Runnable(){
            public void run(){

                if(contact.contains("@")) {
                    ControllerUtil.sendEmail(contact, vcode);
                }
                else
                    ControllerUtil.sendText(contact, vcode);
            }

        };

        Thread thread = new Thread(emailRunnable);
        thread.start();

        rinfo.setResult("SUCCESS");
        return rinfo;
    }





    // 找回密码
    // 先填写验证码，被验证后发送密码至邮箱
    @RequestMapping(value = "/getback", method = RequestMethod.POST)
    @ResponseBody
    public Object getBackPasswd(@RequestParam(value = "userAccount", required = false) String userAccount,
                                @RequestParam(value = "validationCode", required = false) String validationCode,
                                HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setReason("ERROR");
        if(vcode == null) {
            vcode = "";
        }

        if(userAccount == null || userAccount.equals("") ||
                validationCode == null || validationCode.equals("")) {

            rinfo.setReason("E_INCOMPELETE_INFO");

        } else if(!validationCode.equals(vcode)) {

            rinfo.setReason("E_WRONG_VCODE");

        } else  {

            String userid = UserInfoDAO.getUserID(userAccount);
            UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);

            //
            // 在新线程中发送邮件
            //
            Runnable emailRunnable = new Runnable(){
                public void run(){

                    ControllerUtil.sendPasswd(userAccount, user.getPassword());

                }

            };

            rinfo.setResult("SUCCESS");
        }

        vcode = null;
        return rinfo;
    }





    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ResponseBody
    public Object resetPasswd(@RequestParam("newpassword") String newpassword,
                              @RequestParam("cfmpassword") String cfmpassword,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(newpassword == null || cfmpassword.equals("") ||
                cfmpassword == null || cfmpassword.equals("")) {
            rinfo.setReason("E_INCOMPLETE_INFO");
            return rinfo;
        }

        if(!newpassword.equals(cfmpassword)) {
            rinfo.setReason("E_INCONSISTENT_PASSWD");
            return rinfo;
        }

        String userid = ControllerUtil.getUidFromReq(request);
        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);
        user.setPassword(newpassword);
        CommonDAO.updateItem(UuserEntity.class, userid, user);
        rinfo.setResult("SUCCESS");
        return rinfo;
    }


}
