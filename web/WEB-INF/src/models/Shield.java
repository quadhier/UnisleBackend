package models;

/**
 * Created by Administrator on 2017/7/12.
 */
public class Shield {
    private String coaction;
    private String coactee;
    private String activityID;

    public String getCoaction() {
        return coaction;
    }

    public void setCoaction(String coaction) {
        this.coaction = coaction;
    }

    public String getCoactee() {
        return coactee;
    }

    public void setCoactee(String coactee) {
        this.coactee = coactee;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public Shield(){}
}
