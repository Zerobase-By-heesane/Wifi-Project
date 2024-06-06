package zero.openapi.domain.bookmark;

import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "BookmarkDelete", value = "/bookmark-delete")
public class BookmarkDeleteServlet extends HttpServlet{
    DBHandler db;
    public void init() {
        db = new DBHandler();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer deletedId = Integer.parseInt(request.getParameter("id"));
        System.out.println("deletedId: " + deletedId);
        LinkedList<String> deletedInfo = db.getBookmarkAndWifiRelationByBookmarkId(deletedId);

        if (deletedInfo.isEmpty()) {
            deletedInfo = db.getDeletedBookmarkDataById(deletedId);
        }
        System.out.println("deletedInfo: " + deletedInfo);
        request.setAttribute("deletedInfo", deletedInfo);
        request.getRequestDispatcher("/bookmark-delete.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        Integer id = Integer.parseInt(request.getParameter("id"));
        System.out.println("id: " + id);
        db.deleteBookmark(id);
        response.sendRedirect("/bookmark-manager");
    }
}
