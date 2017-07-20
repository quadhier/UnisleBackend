package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ArticlecommentEntity {
    private ArticlecommentEntityPK articlecommentEntityPK;
    private String content;

    public ArticlecommentEntityPK getArticlecommentEntityPK() {
        return articlecommentEntityPK;
    }

    public void setArticlecommentEntityPK(ArticlecommentEntityPK articlecommentEntityPK) {
        this.articlecommentEntityPK = articlecommentEntityPK;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
