package util;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qudaohan on 2017/7/15.
 */

public class ActivityCreation {

    String[] shieldIDList;

    String content;

    byte[] attachment;

    public String[] getShieldIDList() {
        return shieldIDList;
    }

    public String getContent() {
        return content;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setShieldIDList(String[] shieldIDList) {
        this.shieldIDList = shieldIDList;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public void setContent(String content) {
        this.content = content;
    }

}