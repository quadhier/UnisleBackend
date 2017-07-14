package entity;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/13.
 */
public class ActivityEntity {
    private String activityid;
    private String type;
    private String content;
    private byte[] attachment;
    private String publisher;
    private Timestamp publicdatetime;
    private Short pros;
    private String originalactivityid;

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Timestamp getPublicdatetime() {
        return publicdatetime;
    }

    public void setPublicdatetime(Timestamp publicdatetime) {
        this.publicdatetime = publicdatetime;
    }

    public Short getPros() {
        return pros;
    }

    public void setPros(Short pros) {
        this.pros = pros;
    }

    public String getOriginalactivityid() {
        return originalactivityid;
    }

    public void setOriginalactivityid(String originalactivityid) {
        this.originalactivityid = originalactivityid;
    }

}
