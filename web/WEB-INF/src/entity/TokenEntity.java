package entity;

/**
 * Created by Administrator on 2017/7/13.
 */
public class TokenEntity {
    private String tokenid;
    private Long lastactivetime;
    private String userid;
    private Long viewActTime;

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public Long getLastactivetime() {
        return lastactivetime;
    }

    public void setLastactivetime(Long lastactivetime) {
        this.lastactivetime = lastactivetime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getViewActTime() {
        return viewActTime;
    }

    public void setViewActTime(Long viewActTime) {
        this.viewActTime = viewActTime;
    }
}
