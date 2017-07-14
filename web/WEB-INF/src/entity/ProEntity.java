package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ProEntity {
    private ProEntityPK proEntityPK;
    private Timestamp publicdatetime;

    public ProEntityPK getProEntityPK() {
        return proEntityPK;
    }

    public void setProEntityPK(ProEntityPK proEntityPK) {
        this.proEntityPK = proEntityPK;
    }

    public Timestamp getPublicdatetime() {
        return publicdatetime;
    }

    public void setPublicdatetime(Timestamp publicdatetime) {
        this.publicdatetime = publicdatetime;
    }
}
