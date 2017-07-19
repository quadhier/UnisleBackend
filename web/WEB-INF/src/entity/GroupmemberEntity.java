package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class GroupmemberEntity {
    private GroupmemberEntityPK groupmemberEntityPK;
    private String position;
    private Timestamp joindatetime;
    private String visibility;

    public GroupmemberEntityPK getGroupmemberEntityPK() {
        return groupmemberEntityPK;
    }

    public void setGroupmemberEntityPK(GroupmemberEntityPK groupmemberEntityPK) {
        this.groupmemberEntityPK = groupmemberEntityPK;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Timestamp getJoindatetime() {
        return joindatetime;
    }

    public void setJoindatetime(Timestamp joindatetime) {
        this.joindatetime = joindatetime;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
