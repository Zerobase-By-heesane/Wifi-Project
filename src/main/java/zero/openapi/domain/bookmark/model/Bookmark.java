package zero.openapi.domain.bookmark.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Bookmark {
    private Integer ID;
    private String title;
    private Integer order;
    private Timestamp registerDate;
    private Timestamp updateDate;

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
