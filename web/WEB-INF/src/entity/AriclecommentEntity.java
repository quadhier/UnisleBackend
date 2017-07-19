package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class AriclecommentEntity {
    private ActivitycommentEntityPK activitycommentEntityPK;
    private String content;

    public ActivitycommentEntityPK getActivitycommentEntityPK() {
        return activitycommentEntityPK;
    }

    public void setActivitycommentEntityPK(ActivitycommentEntityPK activitycommentEntityPK) {
        this.activitycommentEntityPK = activitycommentEntityPK;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
