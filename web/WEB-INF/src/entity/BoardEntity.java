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

}
