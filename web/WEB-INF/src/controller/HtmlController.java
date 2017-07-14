package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.annotation.WebServlet;

/**
 * Created by qudaohan on 2017/7/13.
 */

@Controller
@RequestMapping("/000")
public class HtmlController {
    @RequestMapping(value = "{htmlPage}", method = RequestMethod.GET)
    public String returnHtml(@PathVariable("htmlPage") String htmlPage){
        System.out.println("This is htmlPage");
        return htmlPage;
    }

}
