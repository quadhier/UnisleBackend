package entity;

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
    private String createdatetime;
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

    public String getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(String createdatetime) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UgroupEntity that = (UgroupEntity) o;

        if (groupid != null ? !groupid.equals(that.groupid) : that.groupid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        if (director != null ? !director.equals(that.director) : that.director != null) return false;
        if (school != null ? !school.equals(that.school) : that.school != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (createdatetime != null ? !createdatetime.equals(that.createdatetime) : that.createdatetime != null)
            return false;
        if (!Arrays.equals(grouppic, that.grouppic)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return numnotice != null ? numnotice.equals(that.numnotice) : that.numnotice == null;
    }

    @Override
    public int hashCode() {
        int result = groupid != null ? groupid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (createdatetime != null ? createdatetime.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(grouppic);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (numnotice != null ? numnotice.hashCode() : 0);
        return result;
    }
}
