package entity;

import java.sql.Timestamp;

public class CollentionEntity {
    private CollentionEntityPK collentionEntityPK;
    private Timestamp collectdatetime;

    public CollentionEntityPK getCollentionEntityPK() {
        return collentionEntityPK;
    }

    public void setCollentionEntityPK(CollentionEntityPK collentionEntityPK) {
        this.collentionEntityPK = collentionEntityPK;
    }

    public Timestamp getCollectdatetime() {
        return collectdatetime;
    }

    public void setCollectdatetime(Timestamp collectdatetime) {
        this.collectdatetime = collectdatetime;
    }
}