package zero.openapi.domain.bookmark;

import zero.openapi.domain.bookmark.model.BookmarkUpdateDTO;
import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "BookmarkUpdate", value = "/bookmark-update")
public class BookmarkUpdateServlet extends HttpServlet {
    DBHandler db;
    public void init() {
        db = new DBHandler();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
        LinkedList<String> bookmark = db.getBookmarkDataById(id);
        request.setAttribute("Targetbookmark", bookmark);
        request.setAttribute("id", id);
        request.getRequestDispatcher("/bookmark-update.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Integer id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        Integer order = request.getParameter("order") == null ? 0 : Integer.parseInt(request.getParameter("order"));

        BookmarkUpdateDTO bookmarkUpdateDTO = new BookmarkUpdateDTO(id, title, order);
        db.updateBookmarkData(bookmarkUpdateDTO);

        response.sendRedirect("/bookmark-list");
    }
}
