package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class AricleEntity {
    private String articleid;
    private String author;
    private String themename;
    private String boardname;
    private Timestamp publicdatetime;
    private Timestamp lastmoddatetime;
    private Timestamp lastcomdatetime;
    private Short viewtimes;
    private String content;
    private String visibility;
    private String commentallowed;

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThemename() {
        return themename;
    }

    public void setThemename(String themename) {
        this.themename = themename;
    }

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    public Timestamp getPublicdatetime() {
        return publicdatetime;
    }

    public void setPublicdatetime(Timestamp publicdatetime) {
        this.publicdatetime = publicdatetime;
    }

    public Timestamp getLastmoddatetime() {
        return lastmoddatetime;
    }

    public void setLastmoddatetime(Timestamp lastmoddatetime) {
        this.lastmoddatetime = lastmoddatetime;
    }

    public Timestamp getLastcomdatetime() {
        return lastcomdatetime;
    }

    public void setLastcomdatetime(Timestamp lastcomdatetime) {
        this.lastcomdatetime = lastcomdatetime;
    }

    public Short getViewtimes() {
        return viewtimes;
    }

    public void setViewtimes(Short viewtimes) {
        this.viewtimes = viewtimes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getCommentallowed() {
        return commentallowed;
    }

    public void setCommentallowed(String commentallowed) {
        this.commentallowed = commentallowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AricleEntity that = (AricleEntity) o;

        if (articleid != null ? !articleid.equals(that.articleid) : that.articleid != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (themename != null ? !themename.equals(that.themename) : that.themename != null) return false;
        if (boardname != null ? !boardname.equals(that.boardname) : that.boardname != null) return false;
        if (publicdatetime != null ? !publicdatetime.equals(that.publicdatetime) : that.publicdatetime != null)
            return false;
        if (lastmoddatetime != null ? !lastmoddatetime.equals(that.lastmoddatetime) : that.lastmoddatetime != null)
            return false;
        if (lastcomdatetime != null ? !lastcomdatetime.equals(that.lastcomdatetime) : that.lastcomdatetime != null)
            return false;
        if (viewtimes != null ? !viewtimes.equals(that.viewtimes) : that.viewtimes != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (visibility != null ? !visibility.equals(that.visibility) : that.visibility != null) return false;
        if (commentallowed != null ? !commentallowed.equals(that.commentallowed) : that.commentallowed != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = articleid != null ? articleid.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (themename != null ? themename.hashCode() : 0);
        result = 31 * result + (boardname != null ? boardname.hashCode() : 0);
        result = 31 * result + (publicdatetime != null ? publicdatetime.hashCode() : 0);
        result = 31 * result + (lastmoddatetime != null ? lastmoddatetime.hashCode() : 0);
        result = 31 * result + (lastcomdatetime != null ? lastcomdatetime.hashCode() : 0);
        result = 31 * result + (viewtimes != null ? viewtimes.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (visibility != null ? visibility.hashCode() : 0);
        result = 31 * result + (commentallowed != null ? commentallowed.hashCode() : 0);
        return result;
    }
}
