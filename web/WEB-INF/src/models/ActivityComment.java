package models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ActivityComment implements Serializable{
    private String userID;
    private String activityID;
    private Date publicDatetime;
    private String content;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public Date getPublicDatetime() {
        return publicDatetime;
    }

    public void setPublicDatetime(Date publicDatetime) {
        this.publicDatetime = publicDatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ActivityComment(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityComment that = (ActivityComment) o;

        if (!userID.equals(that.userID)) return false;
        if (!activityID.equals(that.activityID)) return false;
        return publicDatetime.equals(that.publicDatetime);
    }

    @Override
    public int hashCode() {
        int result = userID.hashCode();
        result = 31 * result + activityID.hashCode();
        result = 31 * result + publicDatetime.hashCode();
        return result;
    }
}
