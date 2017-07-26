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
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    // 发送密码至邮箱
    public static boolean sendPasswd(String contact, String passwd) {
        System.out.println("Start");
        MailUtil mailUtil = new MailUtil();
        mailUtil.setAddress("1042219769@qq.com", contact, "密码");
        mailUtil.send("smtp.qq.com",
                "1042219769@qq.com",
                "opfsuopswljebfdd",
                "您的Unisle密码为" + passwd + "请注意保存");
        System.out.println("Finished");
        return true;
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

    // 从request中获取userid
    public static String getUidFromReq(HttpServletRequest request) {

        String userid = null;
        userid = UserInfoDAO.getUserByToken(getTidFromReq(request));
        return userid;
    }

    // 从request中获取指定名称的cookie
    public static Cookie getCookieFromReq(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    // Array转化为List
    public static List arrToList(Object[] arr) {
        List list = new ArrayList<Object>();
        for(Object obj : arr) {
            list.add(obj);
        }
        return list;
    }


    //
    // ！！！需要检测文件夹的写入权限
    //
    // 将字节数组转化为文件存入文件系统中并且返回储存路径
    public static String storeFile(byte[] buf, String filename, String other, String type) {

        //此处必须是你服务器war文件夹中tmp/pic文件夹的本机上的绝对路径
        String realPrefix = "/Users/qudaohan/Devel/IdeaProjects/UnisleBackend/out/artifacts/UnisleBackend_war_exploded/tmp/pic"  +
                File.separator + type;
        byteToFile(buf, realPrefix, filename);
        String virtualPrefix = "pic" + File.separator + type;
        return virtualPrefix + File.separator + filename;
    }


    // 将字节数组写入文件中，为覆盖写入
    public static void byteToFile(byte[] buf, String filePath, String fileName)
    {

        System.out.println("byte to file");
        FileOutputStream fileOut = null;
        BufferedOutputStream bufferOut = null;
        File file = null;
        try
        {
            File resFile = new File(filePath);
            if (!resFile.exists())
            {
                resFile.mkdirs();
                System.out.println("directories made");
            }
            System.out.println(resFile.getCanonicalPath());
            file = new File(filePath + File.separator + fileName);
            if(!file.exists())
            {
                file.createNewFile();
                System.out.println("file created");
            }
            System.out.println(file.getCanonicalPath());
            fileOut = new FileOutputStream(file);
            bufferOut = new BufferedOutputStream(fileOut);
            bufferOut.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bufferOut != null)
            {
                try
                {
                    bufferOut.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fileOut != null)
            {
                try
                {
                    fileOut.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

}
