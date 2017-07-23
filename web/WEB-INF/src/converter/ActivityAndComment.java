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

    String[] commenterids;

    Long[] publishtimes;

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

    public String[] getCommenterids() {
        return commenterids;
    }

    public Long[] getPublishtimes() {
        return publishtimes;
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

    public void setCommenterids(String[] commenterids) {
        this.commenterids = commenterids;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    public void setPublishtimes(Long[] publishtimes) {
        this.publishtimes = publishtimes;
    }
}
