package zero.openapi.domain.bookmark;

import zero.openapi.domain.bookmark.model.BookmarkDTO;
import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "bookmarkInsert", value = "/bookmark-insert")
public class BookmarkCreateServlet extends HttpServlet {
    DBHandler db;
    public void init() {
        db = new DBHandler();

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.getRequestDispatcher("/bookmark-insert.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String order = request.getParameter("order");

        BookmarkDTO bookmark = new BookmarkDTO(
                title,Integer.valueOf(order)
        );

        db.insertBookmarkData(bookmark);

        response.sendRedirect("/bookmark-manager");
    }
}
