package entity;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ForumaccountEntity {
    private String userid;
    private String rank;
    private String exp;
    private int privilige;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public int getPrivilige() {
        return privilige;
    }

    public void setPrivilige(int privilige) {
        this.privilige = privilige;
    }

}
