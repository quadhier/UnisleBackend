package entity;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ForumaccountEntity {
    private String userid;
    private int rank;
    private int exp;
    private int privilige;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPrivilige() {
        return privilige;
    }

    public void setPrivilige(int privilige) {
        this.privilige = privilige;
    }

}
