package controller;

import com.sun.org.apache.regexp.internal.RE;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import converter.ResultInfo;
import dao.CommonDAO;
import dao.UserInfoDAO;
import entity.UuserEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import util.ControllerUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

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

    private String sset(String value) {
        return value == null ? "" : value;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object updateSelfInfo(@RequestParam(value = "realname", required = false) String realname,
                                 @RequestParam(value = "nickname", required = false) String nickname,
                                 @RequestParam(value = "school", required = false) String school,
                                 @RequestParam(value = "department", required = false) String department,
                                 @RequestParam(value = "grade", required = false) String grade,
                                 @RequestParam(value = "sex", required = false) String sex,
                                 @RequestParam(value = "birthday", required = false) String birthday,
                                 @RequestParam(value = "area", required = false) String hometown,
                                 @RequestParam(value = "contact", required = false) String contact,
                                 @RequestParam(value = "description", required = false) String description,
                                 @RequestParam(value = "music", required = false) String music,
                                 @RequestParam(value = "PE", required = false) String sport,
                                 @RequestParam(value = "book", required = false) String book,
                                 @RequestParam(value = "movies", required = false) String movie,
                                 @RequestParam(value = "game", required = false) String game,
                                 @RequestParam(value = "other", required = false) String other,
                                 @RequestParam(value = "signature", required = false) String signature,
                                 HttpServletRequest request) {

        realname = sset(realname);
        nickname = sset(nickname);
        school = sset(school);
        department = sset(department);
        grade = sset(grade);
        sex = sset(sex);
        birthday = sset(birthday);
        hometown = sset(hometown);
        contact = sset(contact);
        description = sset(description);
        music = sset(music);
        sport = sset(sport);
        book = sset(book);
        movie = sset(movie);
        game = sset(game);
        other = sset(other);
        signature = sset(signature);

        String userid = ControllerUtil.getUidFromReq(request);
        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);

        user.setRealname(realname);
        user.setNickname(nickname);
        user.setSchool(school);
        user.setDepartment(department);
        user.setGrade(grade);
        user.setSex(sex);
        user.setHometown(hometown);
        user.setDescription(description);
        user.setSignature(signature);

        user.setBirthday(Timestamp.valueOf(ControllerUtil.normalizeTime(birthday)));

        if(contact.contains("@"))
            user.setEmail(contact);
        else
            user.setTelephone(contact);

        //
        //设置兴趣爱好
        //
        //......

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");

        return rinfo;
    }


    @RequestMapping(value = "/userpic", method = RequestMethod.POST)
    public String addUserPic(@RequestParam("userpic") CommonsMultipartFile userPic,
                             HttpServletRequest request) {

        String userid = ControllerUtil.getUidFromReq(request);

        byte[] userpic = null;
        if(!userPic.isEmpty()) {

            userpic = userPic.getBytes();
            UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);
            user.setUserpic(userpic);
            CommonDAO.updateItem(UuserEntity.class, userid, user);
            System.out.println("saved");
        }

        return "/self.html";
    }

    @RequestMapping(value = "/userpic", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserPic(HttpServletRequest request) {

        String userid = ControllerUtil.getUidFromReq(request);
        UuserEntity user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class, userid);
        byte[] bytes = user.getUserpic();
        System.out.println("created");
        String path = "/tmp/unisle/images";
        ControllerUtil.byteToFile(bytes, path, "user" + userid);
        return path;
    }




}
