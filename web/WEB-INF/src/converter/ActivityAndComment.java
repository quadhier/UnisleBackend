package converter;

import dao.ActivityDAO;
import entity.ActivityEntity;
import entity.ActivitycommentEntity;
import entity.UuserEntity;

import java.util.HashMap;

/**
 * Created by qudaohan on 2017/7/15.
 */
public class ActivityAndComment {

    boolean tag;

    String userName;

    ActivityEntity activity;

    ActivitycommentEntity[] comments;

    String[] commenters;

    public boolean isTag() {
        return tag;
    }

    public String getUserName() {
        return userName;
    }

    public ActivityEntity getActivity() {
        return activity;
    }

    public ActivitycommentEntity[] getComments() {
        return comments;
    }

    public String[] getCommentors() {
        return commenters;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
    }

    public void setComments(ActivitycommentEntity[] comments) {
        this.comments = comments;
    }

    public void setCommenters(String[] commenters) {
        this.commenters = commenters;
    }

}
