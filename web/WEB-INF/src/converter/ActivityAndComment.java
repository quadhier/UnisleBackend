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

    String userName;

    ActivityEntity activity;

    ActivitycommentEntity[] comments;

    String[] commenters;

    boolean pro;

    public String getUserName() {
        return userName;
    }

    public ActivityEntity getActivity() {
        return activity;
    }

    public ActivitycommentEntity[] getComments() {
        return comments;
    }

    public String[] getCommenters() {
        return commenters;
    }

    public boolean getPro() {
        return pro;
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

    public void setPro(boolean pro) {
        this.pro = pro;
    }

}
