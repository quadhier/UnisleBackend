package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12.
 */
public class GroupmemberEntityPK implements Serializable {
    private String userid;
    private String groupid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupmemberEntityPK that = (GroupmemberEntityPK) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        return groupid != null ? groupid.equals(that.groupid) : that.groupid == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (groupid != null ? groupid.hashCode() : 0);
        return result;
    }
}
