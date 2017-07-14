package controller;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import entity.ActivityEntity;
import entity.ActivitycommentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/activities")
public class ActivityController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object allActivities() {
        return new Object();
    }


}
