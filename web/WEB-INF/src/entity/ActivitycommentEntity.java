package entity;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ActivitycommentEntity {
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
