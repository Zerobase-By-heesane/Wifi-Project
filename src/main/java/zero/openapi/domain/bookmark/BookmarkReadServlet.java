package zero.openapi.domain.bookmark;

import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "Bookmark", value = "/bookmark")
public class BookmarkReadServlet extends HttpServlet {
    DBHandler db;
    public void init() {
        db = new DBHandler();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LinkedList<LinkedList<String>> bookmarks = db.getBookmarkAndWifiRelation();

        request.setAttribute("bookmarkData", bookmarks);

        request.getRequestDispatcher("/bookmark.jsp").forward(request, response);
    }
}
