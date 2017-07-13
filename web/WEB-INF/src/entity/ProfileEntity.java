package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ProfileEntity {
    private ProfileEntityPK profileEntityPK;
    private Timestamp enddate;
    private String content;

    public ProfileEntityPK getProfileEntityPK() {
        return profileEntityPK;
    }

    public void setProfileEntityPK(ProfileEntityPK profileEntityPK) {
        this.profileEntityPK = profileEntityPK;
    }

    public Timestamp getEnddate() {
        return enddate;
    }

    public void setEnddate(Timestamp enddate) {
        this.enddate = enddate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
