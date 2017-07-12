package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/12.
 */
public class BlacklistEntity {
    private BlacklistEntityPK blacklistEntityPK;
    private Timestamp createdatetime;

    public BlacklistEntityPK getBlacklistEntityPK() {
        return blacklistEntityPK;
    }

    public void setBlacklistEntityPK(BlacklistEntityPK blacklistEntityPK) {
        this.blacklistEntityPK = blacklistEntityPK;
    }

    public Timestamp getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Timestamp createdatetime) {
        this.createdatetime = createdatetime;
    }
}
