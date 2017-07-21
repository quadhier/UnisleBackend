package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/21.
 */
public class ChatrecordEntityPK implements Serializable {
    private String sender;
    private String receiver;
    private Timestamp senddatedtime;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Timestamp getSenddatedtime() {
        return senddatedtime;
    }

    public void setSenddatedtime(Timestamp senddatedtime) {
        this.senddatedtime = senddatedtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatrecordEntityPK that = (ChatrecordEntityPK) o;

        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;
        if (senddatedtime != null ? !senddatedtime.equals(that.senddatedtime) : that.senddatedtime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (senddatedtime != null ? senddatedtime.hashCode() : 0);
        return result;
    }
}
