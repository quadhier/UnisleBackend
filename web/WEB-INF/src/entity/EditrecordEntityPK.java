package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class EditrecordEntityPK implements Serializable {
    private String userid;
    private String type;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditrecordEntityPK that = (EditrecordEntityPK) o;

        if (!userid.equals(that.userid)) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
