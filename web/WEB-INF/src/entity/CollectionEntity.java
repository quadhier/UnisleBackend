package entity;

import java.sql.Timestamp;

public class CollectionEntity {
    private CollectionEntityPK collectionEntityPK;
    private Timestamp collectdatetime;

    public CollectionEntityPK getCollectionEntityPK() {
        return collectionEntityPK;
    }

    public void setCollectionEntityPK(CollectionEntityPK collectionEntityPK) {
        this.collectionEntityPK = collectionEntityPK;
    }

    public Timestamp getCollectdatetime() {
        return collectdatetime;
    }

    public void setCollectdatetime(Timestamp collectdatetime) {
        this.collectdatetime = collectdatetime;
    }
}