package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class EditrecordEntityPK implements Serializable {
    private String userid;
    private String articleid;
    private Timestamp editdatetime;

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

    public Timestamp getEditdatetime() {
        return editdatetime;
    }

    public void setEditdatetime(Timestamp editdatetime) {
        this.editdatetime = editdatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditrecordEntityPK that = (EditrecordEntityPK) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (articleid != null ? !articleid.equals(that.articleid) : that.articleid != null) return false;
        return editdatetime != null ? editdatetime.equals(that.editdatetime) : that.editdatetime == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (articleid != null ? articleid.hashCode() : 0);
        result = 31 * result + (editdatetime != null ? editdatetime.hashCode() : 0);
        return result;
    }
}
