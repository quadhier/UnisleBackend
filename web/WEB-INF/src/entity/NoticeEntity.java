package entity;

/**
 * Created by Administrator on 2017/7/12.
 */
public class NoticeEntity {
    private NoticeEntityPK noticeEntityPK;
    private String type;
    private String content;
    private Integer status;

    public NoticeEntityPK getNoticeEntityPK() {
        return noticeEntityPK;
    }

    public void setNoticeEntityPK(NoticeEntityPK noticeEntityPK) {
        this.noticeEntityPK = noticeEntityPK;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
