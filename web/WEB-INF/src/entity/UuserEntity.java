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
    private String activityvisibility;
    private String school;
    private Timestamp registerdatetime;
    private String signature;
    private String description;
    private String contactway;

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

    public String getContactway() {
        return contactway;
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

    public String getSignature() {
        return signature;
    }

    public String getDescription() {
        return description;
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

    public String getActivityvisibility() {
        return activityvisibility;
    }

    public void setActivityvisibility(String activityvisibility) {
        this.activityvisibility = activityvisibility;
    }

    public Timestamp getRegisterdatetime() {
        return registerdatetime;
    }

    public void setRegisterdatetime(Timestamp registerdatetime) {
        this.registerdatetime = registerdatetime;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContactway(String contactway) {
        this.contactway = contactway;
    }


}
