package models;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/12.
 */
public class Pro {
    private String userID;
    private String activityID;
    private Date publicDatetime;

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

    public Pro(){}
}
