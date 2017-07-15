package advice;

import dao.UserInfoDAO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import util.ControllerUtil;
import converter.ResultInfo;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by qudaohan on 2017/7/14.
 */

@Component
@Aspect
public class LoginTest {


    @Around("execution(public * controller.*Controller.*(..)) && " +
            "(args(request,..) || args(..,request)) && " +
            "!execution(public * controller.LoginController.*(..))")
    public Object loginTest(ProceedingJoinPoint pjp, HttpServletRequest request) {

        System.out.println("Login Status Testing");

//        ActivityDAO.publishActivity("10000000005", null, "xixixiha", null);
//        ActivityDAO.publishActivity("10000000005", null, "hahahaha", null);
//        ActivityDAO.publishActivity("10000000005", null, "hehehehe", null);

//        ActivityDAO.publishComment("20100000009", "10000000005", "xixi");
//        ActivityDAO.publishComment("20100000010", "10000000005", "haha");
//        ActivityDAO.publishComment("20100000011", "10000000005", "hehe");


        System.out.println("Insert Finished");

        Class returnType = ((MethodSignature)pjp.getSignature()).getReturnType();

        if(UserInfoDAO.validateToken(ControllerUtil.getTidFromReq(request))){
            try {
                return pjp.proceed(pjp.getArgs());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            if(returnType == String.class) {
                return "redirect:/";
            } else {
                ResultInfo rinfo = new ResultInfo();
                rinfo.setResult("ERROR");
                rinfo.setReason("E_PROCESSING_ERROR");
                return rinfo;
            }
        }

        // 如果token无效，或原方法执行出错，则重定向到主页或返回错误信息
        if(returnType == String.class) {
            return "redirect:/";
        } else {
            ResultInfo rinfo = new ResultInfo();
            rinfo.setResult("ERROR");
            rinfo.setReason("E_NOT_LOGIN");
            return rinfo;
        }

    }
}
