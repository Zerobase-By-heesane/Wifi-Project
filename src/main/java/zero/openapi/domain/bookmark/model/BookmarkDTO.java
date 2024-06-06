package zero.openapi.domain.bookmark.model;

public class BookmarkDTO {
    private String title;
    private Integer order;

    public BookmarkDTO(String title, Integer order) {
        this.title = title;
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public Integer getOrder() {
        return order;
    }
}

