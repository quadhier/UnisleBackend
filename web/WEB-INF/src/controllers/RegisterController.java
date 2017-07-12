package controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/12.
 */
public class RegisterController extends HttpServlet{
    public void doGet(HttpServletRequest req,HttpServletResponse resp)
        throws IOException{
        String jsonStr = (String)req.getAttribute("json");
        ObjectMapper parser = new ObjectMapper();
        Map map = parser.readValue(jsonStr,Map.class);
    }
}
