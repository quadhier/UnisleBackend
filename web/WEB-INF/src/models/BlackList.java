package models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/12.
 */
public class BlackList implements Serializable{
    private String coaction;
    private String coactee;
    private Date createDatetime;

    public String getCoaction() {
        return coaction;
    }

    public void setCoaction(String coaction) {
        this.coaction = coaction;
    }

    public String getCoactee() {
        return coactee;
    }

    public void setCoactee(String coactee) {
        this.coactee = coactee;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatatime) {
        this.createDatetime = createDatatime;
    }

    public BlackList(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlackList blackList = (BlackList) o;

        if (!coaction.equals(blackList.coaction)) return false;
        return coactee.equals(blackList.coactee);
    }

    @Override
    public int hashCode() {
        int result = coaction.hashCode();
        result = 31 * result + coactee.hashCode();
        return result;
    }
}
