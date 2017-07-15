package util;

import entity.ActivityEntity;
import entity.ActivitycommentEntity;
import entity.UuserEntity;

import java.util.HashMap;

/**
 * Created by qudaohan on 2017/7/15.
 */
public class ActivityAndComment {

    String[] userNames;

    ActivityEntity[] activities;

    HashMap<String, ActivitycommentEntity[]> allComments;

    public String[] getUsers() {
        return userNames;
    }

    public ActivityEntity[] getActivities() {
        return activities;
    }

    public HashMap<String, ActivitycommentEntity[]> getAllComments() {
        return allComments;
    }

    public void setUsers(String[] userNames) {
        this.userNames = userNames;
    }

    public void setActivities(ActivityEntity[] activities) {
        this.activities = activities;
    }

    public void setAllComments(HashMap<String, ActivitycommentEntity[]> allComments) {
        this.allComments = allComments;
    }

}
