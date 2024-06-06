package zero.openapi.domain.bookmark.model;

import java.sql.Timestamp;

public class Bookmark {
    private Integer ID;
    private String title;
    private Integer order;
    private Timestamp registerDate;
    private Timestamp updateDate;

    public Bookmark(Integer ID, String title, Integer order, Timestamp registerDate, Timestamp updateDate) {
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

    @Override
    public String toString() {
        return "Bookmark{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", order=" + order +
                ", registerDate=" + registerDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
