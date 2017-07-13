package entity;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/12.
 */
public class UuserEntity {
    private String userid;
    private String password;
    private String realname;
    private String nickname;
    private String sex;
    private Timestamp birthday;
    private String department;
    private String grade;
    private byte[] userpic;
    private String hometown;
    private String telephone;
    private String email;
    private Short numnotice;
    private Integer activityvisibility;
    private Timestamp registerdatetime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public byte[] getUserpic() {
        return userpic;
    }

    public void setUserpic(byte[] userpic) {
        this.userpic = userpic;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getNumnotice() {
        return numnotice;
    }

    public void setNumnotice(Short numnotice) {
        this.numnotice = numnotice;
    }

    public Integer getActivityvisibility() {
        return activityvisibility;
    }

    public void setActivityvisibility(Integer activityvisibility) {
        this.activityvisibility = activityvisibility;
    }

    public Timestamp getRegisterdatetime() {
        return registerdatetime;
    }

    public void setRegisterdatetime(Timestamp registerdatetime) {
        this.registerdatetime = registerdatetime;
    }


}
