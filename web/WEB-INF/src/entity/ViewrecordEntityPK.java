package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ViewrecordEntityPK implements Serializable {
    private String userid;
    private String articleid;
    private Timestamp viewdatetime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public Timestamp getViewdatetime() {
        return viewdatetime;
    }

    public void setViewdatetime(Timestamp viewdatetime) {
        this.viewdatetime = viewdatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewrecordEntityPK that = (ViewrecordEntityPK) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (articleid != null ? !articleid.equals(that.articleid) : that.articleid != null) return false;
        if (viewdatetime != null ? !viewdatetime.equals(that.viewdatetime) : that.viewdatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (articleid != null ? articleid.hashCode() : 0);
        result = 31 * result + (viewdatetime != null ? viewdatetime.hashCode() : 0);
        return result;
    }
}
