package models;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/12.
 */
public class OriginalActivity {
    private String originalActivityID;
    private String userID;
    private String content;
    private int pros;
    private int attachment;
    private Date publicDatetime;

    public String getOriginalActivityID() {
        return originalActivityID;
    }

    public void setOriginalActivityID(String originalActivityID) {
        this.originalActivityID = originalActivityID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPros() {
        return pros;
    }

    public void setPros(int pros) {
        this.pros = pros;
    }

    public int getAttachment() {
        return attachment;
    }

    public void setAttachment(int attachment) {
        this.attachment = attachment;
    }

    public Date getPublicDatetime() {
        return publicDatetime;
    }

    public void setPublicDatetime(Date publicDatetime) {
        this.publicDatetime = publicDatetime;
    }

    public OriginalActivity(){}
}
