package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ArticleEntity {
    private String articleid;
    private String title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}
