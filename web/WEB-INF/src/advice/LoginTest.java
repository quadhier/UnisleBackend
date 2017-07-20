package advice;

import dao.UserInfoDAO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import util.ControllerUtil;
import converter.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by qudaohan on 2017/7/14.
 */

@Component
@Aspect
public class LoginTest {

//
//    @Around("execution(public * controller.*Controller.*(..)) && " +
//            "(args(request,..) || args(..,request)) && " +
//            //"args(.., request, response)" +
//            "!execution(public * controller.LoginController.*(..))")
//    public Object loginTest(ProceedingJoinPoint pjp, HttpServletRequest request) throws IOException {
//
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        ServletWebRequest servletWebRequest=new ServletWebRequest(req);
//        HttpServletResponse response=servletWebRequest.getResponse();
//
//        System.out.println("Login Status Testing");
//
//
//        System.out.println("Insert Finished");
//
//        Class returnType = ((MethodSignature)pjp.getSignature()).getReturnType();
//
//        if(UserInfoDAO.validateToken(ControllerUtil.getTidFromReq(request))){
//            try {
//                return pjp.proceed(pjp.getArgs());
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//
//            if(returnType == String.class) {
//                return "redirect:/";
//            } else {
//                response.sendRedirect("/");
//
//                ResultInfo rinfo = new ResultInfo();
//                rinfo.setResult("LOGINERROR");
//                rinfo.setReason("E_PROCESSING_ERROR");
//                return rinfo;
//            }
//        }
//
//
//        // 如果token无效，或原方法执行出错，则重定向到主页或返回错误信息
//        if(returnType == String.class) {
//            return "redirect:/";
//        } else {
//            ResultInfo rinfo = new ResultInfo();
//            rinfo.setResult("LOGINERROR");
//            rinfo.setReason("E_NOT_LOGIN");
//            return rinfo;
//        }
//
//    }
}
