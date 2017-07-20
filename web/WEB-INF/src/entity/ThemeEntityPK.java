package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ThemeEntityPK implements Serializable {
    private String themename;
    private String boardname;

    public String getThemename() {
        return themename;
    }

    public void setThemename(String themename) {
        this.themename = themename;
    }

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThemeEntityPK that = (ThemeEntityPK) o;

        if (themename != null ? !themename.equals(that.themename) : that.themename != null) return false;
        return boardname != null ? boardname.equals(that.boardname) : that.boardname == null;
    }

    @Override
    public int hashCode() {
        int result = themename != null ? themename.hashCode() : 0;
        result = 31 * result + (boardname != null ? boardname.hashCode() : 0);
        return result;
    }
}
