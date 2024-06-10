package zero.openapi.domain.wifi;

import zero.openapi.common.database.DBHandler;
import zero.openapi.domain.bookmark.model.CustomBookmark;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "WiFiDetail", value = "/detail")
public class WiFiDetail extends HttpServlet {
    DBHandler db;
    public void init() {
        db = new DBHandler();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = Integer.valueOf(request.getParameter("id"));

        LinkedList<CustomBookmark> bookmarks = db.getBookmarkData();
        LinkedList<String> titles = new LinkedList<>();
        for (CustomBookmark bookmark : bookmarks) {
            titles.add(bookmark.getTitle());
        }
        WiFiInfo wiFiInfo = db.getWifiInfo(id);

        request.setAttribute("wifi", wiFiInfo);
        request.setAttribute("titles", titles);
        request.getRequestDispatcher("/wifi-detail.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        try {
            String wifiIdParam = request.getParameter("wifiId");
            if (wifiIdParam == null || wifiIdParam.isEmpty()) {
                throw new IllegalArgumentException("wifiId parameter is missing or empty");
            }
            Integer wifiId = Integer.valueOf(wifiIdParam);

            String groupName = request.getParameter("bookmarkGroup");
            if (groupName == null || groupName.isEmpty()) {
                throw new IllegalArgumentException("bookmarkGroup parameter is missing or empty");
            }

            if(db.mappingBookmarkAndWiFi(wifiId, groupName)){
                response.sendRedirect("/bookmark");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to map bookmark and wifi");

            }

        } catch (Exception e) {
            // 예외가 발생하면 500 에러를 전송
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            e.printStackTrace();
        }
    }

}
