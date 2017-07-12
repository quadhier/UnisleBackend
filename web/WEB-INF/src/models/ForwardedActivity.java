package models;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ForwardedActivity {
    private String forwardedActivityID;
    private String forwardingUserID;
    private String originalActivityID;
    private Date forwardingDatetime;

    public String getForwardedActivityID() {
        return forwardedActivityID;
    }

    public void setForwardedActivityID(String forwardedActivityID) {
        this.forwardedActivityID = forwardedActivityID;
    }

    public String getForwardingUserID() {
        return forwardingUserID;
    }

    public void setForwardingUserID(String forwardingUserID) {
        this.forwardingUserID = forwardingUserID;
    }

    public String getOriginalActivityID() {
        return originalActivityID;
    }

    public void setOriginalActivityID(String originalActivityID) {
        this.originalActivityID = originalActivityID;
    }

    public Date getForwardingDatetime() {
        return forwardingDatetime;
    }

    public void setForwardingDatetime(Date forwardingDatetime) {
        this.forwardingDatetime = forwardingDatetime;
    }

    public ForwardedActivity(){}
}
