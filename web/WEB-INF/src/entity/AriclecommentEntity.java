package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class AriclecommentEntity {
    private String userid;
    private String articleid;
    private Timestamp publicdatetime;
    private String content;

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

    public Timestamp getPublicdatetime() {
        return publicdatetime;
    }

    public void setPublicdatetime(Timestamp publicdatetime) {
        this.publicdatetime = publicdatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AriclecommentEntity that = (AriclecommentEntity) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (articleid != null ? !articleid.equals(that.articleid) : that.articleid != null) return false;
        if (publicdatetime != null ? !publicdatetime.equals(that.publicdatetime) : that.publicdatetime != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (articleid != null ? articleid.hashCode() : 0);
        result = 31 * result + (publicdatetime != null ? publicdatetime.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
