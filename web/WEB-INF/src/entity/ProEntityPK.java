package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ProEntityPK implements Serializable {
    private String userid;
    private String activityid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProEntityPK that = (ProEntityPK) o;

        if (!userid.equals(that.userid)) return false;
        return activityid.equals(that.activityid);
    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + activityid.hashCode();
        return result;
    }
}
