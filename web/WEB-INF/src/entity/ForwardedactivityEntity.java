package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ForwardedactivityEntity {
    private String forwardedactivityid;
    private String forwardinguser;
    private String originalactivityid;
    private Timestamp forwardingdatetime;

    public String getForwardedactivityid() {
        return forwardedactivityid;
    }

    public void setForwardedactivityid(String forwardedactivityid) {
        this.forwardedactivityid = forwardedactivityid;
    }

    public String getForwardinguser() {
        return forwardinguser;
    }

    public void setForwardinguser(String forwardinguser) {
        this.forwardinguser = forwardinguser;
    }

    public String getOriginalactivityid() {
        return originalactivityid;
    }

    public void setOriginalactivityid(String originalactivityid) {
        this.originalactivityid = originalactivityid;
    }

    public Timestamp getForwardingdatetime() {
        return forwardingdatetime;
    }

    public void setForwardingdatetime(Timestamp forwardingdatetime) {
        this.forwardingdatetime = forwardingdatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForwardedactivityEntity that = (ForwardedactivityEntity) o;

        if (forwardedactivityid != null ? !forwardedactivityid.equals(that.forwardedactivityid) : that.forwardedactivityid != null)
            return false;
        if (forwardinguser != null ? !forwardinguser.equals(that.forwardinguser) : that.forwardinguser != null)
            return false;
        if (originalactivityid != null ? !originalactivityid.equals(that.originalactivityid) : that.originalactivityid != null)
            return false;
        if (forwardingdatetime != null ? !forwardingdatetime.equals(that.forwardingdatetime) : that.forwardingdatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = forwardedactivityid != null ? forwardedactivityid.hashCode() : 0;
        result = 31 * result + (forwardinguser != null ? forwardinguser.hashCode() : 0);
        result = 31 * result + (originalactivityid != null ? originalactivityid.hashCode() : 0);
        result = 31 * result + (forwardingdatetime != null ? forwardingdatetime.hashCode() : 0);
        return result;
    }
}
