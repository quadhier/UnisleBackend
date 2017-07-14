package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.JAXMStreamSource;

import javax.xml.bind.util.JAXBSource;
import java.util.Random;

/**
 * Created by qudaohan on 2017/7/13.
 */
public class ControllerUtil {

    // 产生验证码
    public static String genVCode() {
        Random random = new Random();
        int num = Math.abs(random.nextInt());
        System.out.println(num);
        return String.valueOf(num);
    }

    // 发送验证码至邮箱
    public static boolean sendEmail(String contact, String vcode) {
        MailUtil mailUtil = new MailUtil();
        mailUtil.setAddress("1042219769@qq.com", contact, "验证码");
        mailUtil.send("smtp.qq,com",
                "1042219769@qq.com",
                "opfsuopswljebfdd",
                "您的Unisle验证码为" + vcode + "请及时输入");

        return true;
    }

    // 发送验证码至手机
    public static boolean sendText(String contact, String vcode) {
        return true;
    }

}
