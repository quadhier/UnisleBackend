package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/19.
 */
public class EditrecordEntity {
    private EditrecordEntityPK editrecordEntityPK;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EditrecordEntityPK getEditrecordEntityPK() {
        return editrecordEntityPK;
    }

    public void setEditrecordEntityPK(EditrecordEntityPK editrecordEntityPK) {
        this.editrecordEntityPK = editrecordEntityPK;
    }
}
