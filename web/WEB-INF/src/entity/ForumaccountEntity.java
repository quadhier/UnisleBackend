package entity;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ForumaccountEntity {
    private String userid;
    private String rank;
    private String exp;
    private String privilige;

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

    public String getPrivilige() {
        return privilige;
    }

    public void setPrivilige(String privilige) {
        this.privilige = privilige;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForumaccountEntity that = (ForumaccountEntity) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (exp != null ? !exp.equals(that.exp) : that.exp != null) return false;
        if (privilige != null ? !privilige.equals(that.privilige) : that.privilige != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (exp != null ? exp.hashCode() : 0);
        result = 31 * result + (privilige != null ? privilige.hashCode() : 0);
        return result;
    }
}
