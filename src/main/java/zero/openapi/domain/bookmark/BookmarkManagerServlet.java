package zero.openapi.domain.bookmark;

import zero.openapi.domain.bookmark.model.CustomBookmark;
import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "BookmarkManager", value = "/bookmark-manager")
public class BookmarkManagerServlet extends HttpServlet {
    DBHandler db;
    public void init() {
        db = new DBHandler();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LinkedList<CustomBookmark> bookmarks = db.getBookmarkData();

        request.setAttribute("bookmarks", bookmarks);
        request.getRequestDispatcher("/bookmark-manager.jsp").forward(request, response);
    }
}
