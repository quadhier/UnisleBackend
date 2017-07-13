package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ActivitycommentEntityPK implements Serializable {
    private String userid;
    private String activityid;
    private Timestamp publicdatetime;

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

    public Timestamp getPublicdatetime() {
        return publicdatetime;
    }

    public void setPublicdatetime(Timestamp publicdatetime) {
        this.publicdatetime = publicdatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivitycommentEntityPK that = (ActivitycommentEntityPK) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (activityid != null ? !activityid.equals(that.activityid) : that.activityid != null) return false;
        return publicdatetime != null ? publicdatetime.equals(that.publicdatetime) : that.publicdatetime == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (activityid != null ? activityid.hashCode() : 0);
        result = 31 * result + (publicdatetime != null ? publicdatetime.hashCode() : 0);
        return result;
    }
}
