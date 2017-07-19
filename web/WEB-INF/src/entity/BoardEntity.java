package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class BoardEntity {
    private String boardname;
    private String manager;
    private Timestamp creatingdate;

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Timestamp getCreatingdate() {
        return creatingdate;
    }

    public void setCreatingdate(Timestamp creatingdate) {
        this.creatingdate = creatingdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardEntity that = (BoardEntity) o;

        if (boardname != null ? !boardname.equals(that.boardname) : that.boardname != null) return false;
        if (manager != null ? !manager.equals(that.manager) : that.manager != null) return false;
        if (creatingdate != null ? !creatingdate.equals(that.creatingdate) : that.creatingdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = boardname != null ? boardname.hashCode() : 0;
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        result = 31 * result + (creatingdate != null ? creatingdate.hashCode() : 0);
        return result;
    }
}
