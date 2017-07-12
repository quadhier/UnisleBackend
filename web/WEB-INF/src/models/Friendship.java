package models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/12.
 */
public class Friendship implements Serializable{
    private String userID1;
    private String userID2;
    private Date friendshipDatetime;
    private String user1Note;
    private String user2Note;

    public String getUserID1() {
        return userID1;
    }

    public void setUserID1(String userID1) {
        this.userID1 = userID1;
    }

    public String getUserID2() {
        return userID2;
    }

    public void setUserID2(String userID2) {
        this.userID2 = userID2;
    }

    public Date getFriendshipDatetime() {
        return friendshipDatetime;
    }

    public void setFriendshipDatetime(Date friendshipDatatime) {
        this.friendshipDatetime = friendshipDatatime;
    }

    public String getUser1Note() {
        return user1Note;
    }

    public void setUser1Note(String user1Note) {
        this.user1Note = user1Note;
    }

    public String getUser2Note() {
        return user2Note;
    }

    public void setUser2Note(String user2Note) {
        this.user2Note = user2Note;
    }

    public Friendship(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friendship that = (Friendship) o;

        if (!userID1.equals(that.userID1)) return false;
        return userID2.equals(that.userID2);
    }

    @Override
    public int hashCode() {
        int result = userID1.hashCode();
        result = 31 * result + userID2.hashCode();
        return result;
    }
}
