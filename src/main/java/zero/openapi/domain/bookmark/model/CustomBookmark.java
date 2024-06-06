package zero.openapi.domain.bookmark.model;

import java.sql.Timestamp;

public class CustomBookmark {
    private Integer ID;
    private String title;
    private Integer order;
    private Timestamp registerDate = null;
    private Timestamp updateDate = null;

    public CustomBookmark(Integer ID, String title, Integer order, Timestamp registerDate, Timestamp updateDate) {
        this.ID = ID;
        this.title = title;
        this.order = order;
        this.registerDate = registerDate;
        this.updateDate = updateDate;
    }

    public Integer getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public Integer getOrder() {
        return order;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }
}

