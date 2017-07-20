package converter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qudaohan on 2017/7/15.
 */

public class ActivityCreation {

    String[] shieldIDList;

    String content;

    public String[] getShieldIDList() {
        return shieldIDList;
    }

    public String getContent() {
        return content;
    }

    public void setShieldIDList(String[] shieldIDList) {
        this.shieldIDList = shieldIDList;
    }

    public void setContent(String content) {
        this.content = content;
    }

}