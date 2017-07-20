package entity;

import java.io.Serializable;

public class CollectionEntityPK implements Serializable {
    private String userid;
    private String articleid;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionEntityPK that = (CollectionEntityPK) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (articleid != null ? !articleid.equals(that.articleid) : that.articleid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (articleid != null ? articleid.hashCode() : 0);
        return result;
    }
}
