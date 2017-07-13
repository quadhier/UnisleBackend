package entity;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/12.
 */
public class UuserEntity {
    private String userid;
    private String password;
    private String name;
    private Integer sex;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UuserEntity that = (UuserEntity) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (!Arrays.equals(userpic, that.userpic)) return false;
        if (hometown != null ? !hometown.equals(that.hometown) : that.hometown != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (numnotice != null ? !numnotice.equals(that.numnotice) : that.numnotice != null) return false;
        if (activityvisibility != null ? !activityvisibility.equals(that.activityvisibility) : that.activityvisibility != null)
            return false;
        return registerdatetime != null ? registerdatetime.equals(that.registerdatetime) : that.registerdatetime == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(userpic);
        result = 31 * result + (hometown != null ? hometown.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (numnotice != null ? numnotice.hashCode() : 0);
        result = 31 * result + (activityvisibility != null ? activityvisibility.hashCode() : 0);
        result = 31 * result + (registerdatetime != null ? registerdatetime.hashCode() : 0);
        return result;
    }
}
