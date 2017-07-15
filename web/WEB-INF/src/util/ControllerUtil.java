package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.JAXMStreamSource;
import dao.UserInfoDAO;
import sun.util.resources.cldr.ta.CalendarData_ta_LK;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.util.JAXBSource;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by qudaohan on 2017/7/13.
 */
public class ControllerUtil {

    // 产生一个token并且返回
    public static String createToken(String userid) {
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        return userid + String.valueOf(currentTime);
    }

    // 产生验证码
    public static String genVCode() {
        Random random = new Random();
        int num = Math.abs(random.nextInt());
        System.out.println(num);
        return String.valueOf(num);
    }

    // 发送验证码至邮箱
    public static boolean sendEmail(String contact, String vcode) {
        System.out.println("Start");
        MailUtil mailUtil = new MailUtil();
        mailUtil.setAddress("1042219769@qq.com", contact, "验证码");
        mailUtil.send("smtp.qq.com",
                "1042219769@qq.com",
                "opfsuopswljebfdd",
                "您的Unisle验证码为" + vcode + "请及时输入");
        System.out.println("Finished");
        return true;
    }

    // 发送验证码至手机
    public static boolean sendText(String contact, String vcode) {
        return true;
    }

    // 将中文日期转化为Timestamp
    public static String normalizeTime(String chiTime) {

        try {
            byte[] bytes= chiTime.getBytes("ISO-8859-1"); //这里写原编码方式
            chiTime=new String(bytes,"UTF-8"); //这里写转换后的编码方式
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        chiTime = chiTime + " 00:00:00";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf1.parse(chiTime);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 从request中获取tokenid
    public static String getTidFromReq(HttpServletRequest request) {

        String tokenid = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("tokenid")){
                    tokenid = cookie.getValue();
                }
            }
        }
        return tokenid;
    }

}
