package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12.
 */
public class FriendshipEntityPK implements Serializable {
    private String userid1;
    private String userid2;

    public String getUserid1() {
        return userid1;
    }

    public void setUserid1(String userid1) {
        this.userid1 = userid1;
    }

    public String getUserid2() {
        return userid2;
    }

    public void setUserid2(String userid2) {
        this.userid2 = userid2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendshipEntityPK that = (FriendshipEntityPK) o;

        if (userid1 != null ? !userid1.equals(that.userid1) : that.userid1 != null) return false;
        return userid2 != null ? userid2.equals(that.userid2) : that.userid2 == null;
    }

    @Override
    public int hashCode() {
        int result = userid1 != null ? userid1.hashCode() : 0;
        result = 31 * result + (userid2 != null ? userid2.hashCode() : 0);
        return result;
    }
}
