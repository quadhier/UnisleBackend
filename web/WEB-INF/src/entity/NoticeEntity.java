package entity;

/**
 * Created by Administrator on 2017/7/12.
 */
public class NoticeEntity {
    private NoticeEntityPK noticeEntityPK;
    private String type;
    private String content;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
