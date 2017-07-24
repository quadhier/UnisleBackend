package controller;

import com.sun.org.apache.regexp.internal.RE;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import converter.ResultInfo;
import converter.UserInterest;
import dao.CommonDAO;
import dao.UserInfoDAO;
import entity.InterestEntity;
import entity.UuserEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import util.ControllerUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

/**
 * Created by qudaohan on 2017/7/17.
 */

@Controller
@RequestMapping("/self")
public class SelfController {


    // 屏蔽密码属性后，返回用户的信息
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object getSelfInfo(@RequestParam(value = "userid",required = false) String userid,
                              @RequestParam(value = "withInst", required = false) String withInst,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(userid == null || userid.length() == 0) {
            userid = ControllerUtil.getUidFromReq(request);
        }
        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);

        if(user == null) {
            rinfo.setReason("E_INVALID_USERID");
            return rinfo;
        }


        if(withInst != null) {

            InterestEntity interest = (InterestEntity) CommonDAO.getItemByPK(InterestEntity.class, userid);
            UserInterest ui = new UserInterest();
            user.setPassword(null);
            ui.setUser(user);
            ui.setInterest(interest);
            rinfo.setResult("SUCCESS");
            rinfo.setData(ui);
        } else {

            user.setPassword(null);
            rinfo.setResult("SUCCESS");
            rinfo.setData(user);
        }

        return rinfo;
    }



    // Helper Function
    private String sset(String value) {
        return value == null ? "" : value;
    }

    // 更新用户信息
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
                                 @RequestParam(value = "contact", required = false) String contactway,
                                 @RequestParam(value = "description", required = false) String description,
                                 @RequestParam(value = "signature", required = false) String signature,
                                 @RequestParam(value = "music", required = false) String music,
                                 @RequestParam(value = "PE", required = false) String sport,
                                 @RequestParam(value = "book", required = false) String book,
                                 @RequestParam(value = "movies", required = false) String movie,
                                 @RequestParam(value = "game", required = false) String game,
                                 @RequestParam(value = "other", required = false) String other,
                                 HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        realname = sset(realname);
        nickname = sset(nickname);
        school = sset(school);
        department = sset(department);
        grade = sset(grade);
        sex = sset(sex);
        birthday = sset(birthday);
        hometown = sset(hometown);
        contactway = sset(contactway);
        description = sset(description);
        signature = sset(signature);
        // 储存兴趣爱好信息
        music = sset(music);
        sport = sset(sport);
        book = sset(book);
        movie = sset(movie);
        game = sset(game);
        other = sset(other);

        String userid = ControllerUtil.getUidFromReq(request);
        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);

        user.setRealname(realname);
        user.setNickname(nickname);
        user.setSchool(school);
        user.setDepartment(department);
        user.setGrade(grade);
        user.setSex(sex);
        user.setHometown(hometown);
        user.setContactway(contactway);
        user.setDescription(description);
        user.setSignature(signature);

        // 设置生日
        String strBirthday = ControllerUtil.normalizeTime(birthday);
        if(strBirthday == null) {
            rinfo.setReason("E_MALFORMED_DATE");
            return rinfo;
        }
        user.setBirthday(Timestamp.valueOf(strBirthday));


        // 设置兴趣爱好
        InterestEntity newiInterest = new InterestEntity();
        newiInterest.setMusic(music);
        newiInterest.setSport(sport);
        newiInterest.setBook(book);
        newiInterest.setMovie(movie);
        newiInterest.setGame(game);

        InterestEntity oldInterest = (InterestEntity) CommonDAO.getItemByPK(InterestEntity.class, userid);

        if(oldInterest == null) {
            CommonDAO.saveItem(newiInterest);
        } else {
            CommonDAO.updateItem(InterestEntity.class, userid, oldInterest);
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }

    //
    // 上传用户头像
    //
    @RequestMapping(value = "/userpic", method = RequestMethod.POST)
    @ResponseBody
    public Object addUserPic(@RequestParam("userpic") CommonsMultipartFile userPic,
                             HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        byte[] userPicByte = null;
        if(userPic.isEmpty() == true) {
            rinfo.setReason("E_EMPTY_FILE");
            return rinfo;
        }

        userPicByte = userPic.getBytes();
        String userPicPath =  ControllerUtil.storeFile(userPicByte, userid, null, "userpic");//storeUserPic(userid, userPicByte);
        UuserEntity user = (UuserEntity) CommonDAO.getItemByPK(UuserEntity.class, userid);
        user.setUserpic(userPicPath);
        CommonDAO.updateItem(UuserEntity.class, userid, user);
        System.out.println("userPic saved" + userPicPath);
        rinfo.setResult("SUCCESS");
        return rinfo;

    }


    // 返回图片的储存路径，以便浏览器加载
    // 若用户无头像，则返回系统默认头像的路径
    @RequestMapping(value = "/userpic", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserPic(HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");

        String userid = ControllerUtil.getUidFromReq(request);
        UuserEntity user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class, userid);

        String defaultPic = "/pic/userpic/default.png";
        String sex = user.getSex();
        if(sex.equals("male")) {
            defaultPic = "/pic/userpic/male.jpg";
        } else if(sex.equals("female")) {
            defaultPic = "/pic/userpic/female.jpeg";
        }

        String userPic = user.getUserpic();
        if(userPic == null || userPic.equals(""))
        {
            userPic = defaultPic;
        }
        rinfo.setData(userPic);
        return rinfo;
    }

}
