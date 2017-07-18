package controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import converter.ResultInfo;
import dao.CommonDAO;
import dao.UserInfoDAO;
import entity.UuserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ControllerUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qudaohan on 2017/7/17.
 */

@Controller
@RequestMapping("/self")
public class SelfController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object getSelfInfo(HttpServletRequest request) {

        String userid = ControllerUtil.getUidFromReq(request);
        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);
        user.setPassword(null);
        return user;

    }


}
