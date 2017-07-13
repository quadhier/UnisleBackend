package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/12.
 */
public class PhotoEntityPK implements Serializable {
    private String albumid;
    private Timestamp uploaddatetime;
    private byte[] content;

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public Timestamp getUploaddatetime() {
        return uploaddatetime;
    }

    public void setUploaddatetime(Timestamp uploaddatetime) {
        this.uploaddatetime = uploaddatetime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoEntityPK that = (PhotoEntityPK) o;

        if (albumid != null ? !albumid.equals(that.albumid) : that.albumid != null) return false;
        if (uploaddatetime != null ? !uploaddatetime.equals(that.uploaddatetime) : that.uploaddatetime != null)
            return false;
        if (!Arrays.equals(content, that.content)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = albumid != null ? albumid.hashCode() : 0;
        result = 31 * result + (uploaddatetime != null ? uploaddatetime.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
