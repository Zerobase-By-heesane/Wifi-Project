package zero.openapi.domain.wifi;

import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "historyServlet", value = "/history")
public class historyServlet extends HttpServlet {
    private static DBHandler dbHandler;
    public void init() {
        // 초기화 코드 (필요시 추가)
        dbHandler = new DBHandler();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // LinkedList<LinkedList<String>> historyData
        LinkedList<LinkedList<String>> histories = dbHandler.getHistoryData();
        request.setAttribute("histories", histories);
        request.getRequestDispatcher("/history.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        float lat = Float.parseFloat(request.getParameter("latitude"));
        float lnt = Float.parseFloat(request.getParameter("longitude"));


        if(dbHandler.insertHistoryData(lat, lnt)){
            System.out.println("데이터 삽입 성공");
        } else {
            System.out.println("데이터 삽입 실패");
        }

        // 위치 정보를 처리하고 JSP로 전달
        request.setAttribute("latitude", lat);
        request.setAttribute("longitude", lnt);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 삭제 코드
        int id = Integer.parseInt(request.getParameter("id"));
        if(dbHandler.deleteHistoryData(id)){
            System.out.println("데이터 삭제 성공");
        } else {
            System.out.println("데이터 삭제 실패");
        }
    }
}
