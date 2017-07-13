package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ProfileEntityPK implements Serializable {
    private String userid;
    private Timestamp startdate;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Timestamp getStartdate() {
        return startdate;
    }

    public void setStartdate(Timestamp startdate) {
        this.startdate = startdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileEntityPK that = (ProfileEntityPK) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        return startdate != null ? startdate.equals(that.startdate) : that.startdate == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);
        return result;
    }
}
