package zero.openapi.domain.bookmark.model;

public class BookmarkUpdateDTO {
    private Integer ID;
    private String title;
    private Integer order;

    public BookmarkUpdateDTO(Integer ID, String title, Integer order) {
        this.ID = ID;
        this.title = title;
        this.order = order;
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
}

