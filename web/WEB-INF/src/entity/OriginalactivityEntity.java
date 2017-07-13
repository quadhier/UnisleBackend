package entity;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ActivityEntity {
    private String activityid;
    private String content;
    private byte[] attachment;
    private String publisher;
    private Timestamp publicdatetime;
    private Short pros;

    public String getOriginalactivityid() {
        return originalactivityid;
    }

    public void setOriginalactivityid(String originalactivityid) {
        this.originalactivityid = originalactivityid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Timestamp getPublicdatetime() {
        return publicdatetime;
    }

    public void setPublicdatetime(Timestamp publicdatetime) {
        this.publicdatetime = publicdatetime;
    }

    public Short getPros() {
        return pros;
    }

    public void setPros(Short pros) {
        this.pros = pros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OriginalactivityEntity that = (OriginalactivityEntity) o;

        if (originalactivityid != null ? !originalactivityid.equals(that.originalactivityid) : that.originalactivityid != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (!Arrays.equals(attachment, that.attachment)) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (publicdatetime != null ? !publicdatetime.equals(that.publicdatetime) : that.publicdatetime != null)
            return false;
        if (pros != null ? !pros.equals(that.pros) : that.pros != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originalactivityid != null ? originalactivityid.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(attachment);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (publicdatetime != null ? publicdatetime.hashCode() : 0);
        result = 31 * result + (pros != null ? pros.hashCode() : 0);
        return result;
    }
}
