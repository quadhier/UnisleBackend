package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class EditrecordEntity {
    private String userid;
    private String articleid;
    private String content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

        EditrecordEntity that = (EditrecordEntity) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (articleid != null ? !articleid.equals(that.articleid) : that.articleid != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (editdatetime != null ? !editdatetime.equals(that.editdatetime) : that.editdatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (articleid != null ? articleid.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (editdatetime != null ? editdatetime.hashCode() : 0);
        return result;
    }
}
