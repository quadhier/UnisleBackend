package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class FriendshipEntity {
    private FriendshipEntityPK friendshipEntityPK;
    private Timestamp friendshipdatetime;
    private String user1Note;
    private String user2Note;

    public FriendshipEntityPK getFriendshipEntityPK() {
        return friendshipEntityPK;
    }

    public void setFriendshipEntityPK(FriendshipEntityPK friendshipEntityPK) {
        this.friendshipEntityPK = friendshipEntityPK;
    }

    public Timestamp getFriendshipdatetime() {
        return friendshipdatetime;
    }

    public void setFriendshipdatetime(Timestamp friendshipdatetime) {
        this.friendshipdatetime = friendshipdatetime;
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

}
