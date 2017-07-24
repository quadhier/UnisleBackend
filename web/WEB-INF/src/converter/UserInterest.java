package converter;

import entity.InterestEntity;
import entity.UuserEntity;

/**
 * Created by qudaohan on 2017/7/24.
 */
public class UserInterest {

    UuserEntity user;
    InterestEntity interest;

    public UuserEntity getUser() {
        return user;
    }

    public InterestEntity getInterest() {
        return interest;
    }

    public void setUser(UuserEntity user) {
        this.user = user;
    }

    public void setInterest(InterestEntity interest) {
        this.interest = interest;
    }

}
