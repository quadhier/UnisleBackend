package entity;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/12.
 */
public class UgroupEntity {
    private String groupid;
    private String name;
    private String tag;
    private String director;
    private String school;
    private String department;
    private Timestamp createdatetime;
    private byte[] grouppic;
    private String description;
    private Short numnotice;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Timestamp getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Timestamp createdatetime) {
        this.createdatetime = createdatetime;
    }

    public byte[] getGrouppic() {
        return grouppic;
    }

    public void setGrouppic(byte[] grouppic) {
        this.grouppic = grouppic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getNumnotice() {
        return numnotice;
    }

    public void setNumnotice(Short numnotice) {
        this.numnotice = numnotice;
    }


}
