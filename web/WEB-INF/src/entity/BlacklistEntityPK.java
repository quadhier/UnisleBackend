package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12.
 */
public class BlacklistEntityPK implements Serializable {
    private String coaction;
    private String coactee;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlacklistEntityPK that = (BlacklistEntityPK) o;

        if (coaction != null ? !coaction.equals(that.coaction) : that.coaction != null) return false;
        return coactee != null ? coactee.equals(that.coactee) : that.coactee == null;
    }

    @Override
    public int hashCode() {
        int result = coaction != null ? coaction.hashCode() : 0;
        result = 31 * result + (coactee != null ? coactee.hashCode() : 0);
        return result;
    }
}
