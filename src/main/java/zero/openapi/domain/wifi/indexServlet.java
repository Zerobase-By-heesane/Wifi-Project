package zero.openapi.domain.wifi;

import com.google.gson.Gson;
import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "indexServlet", value = "/home")
public class indexServlet extends HttpServlet {
    private DBHandler db;
    private Gson gs;
    public void init() {
        db = new DBHandler();
        gs = new Gson();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String latitudeStr = request.getParameter("latitude");
        String longitudeStr = request.getParameter("longitude");

        if (latitudeStr != null && longitudeStr != null) {
            try {
                float lat = Float.parseFloat(latitudeStr);
                float lnt = Float.parseFloat(longitudeStr);



                // Get the WiFi data based on latitude and longitude
                List<WiFiInfo> wifiData = db.getWifiData(lat, lnt);
                String wifiDataJson = gs.toJson(wifiData);


                // Pass the WiFi data and location to the JSP
                request.setAttribute("wifiData", wifiDataJson);
                request.setAttribute("latitude", lat);
                request.setAttribute("longitude", lnt);
            } catch (NumberFormatException e) {
                request.setAttribute("latitude", 0.0);
                request.setAttribute("longitude", 0.0);
                request.setAttribute("wifiData", null);
            }
        } else {
            request.setAttribute("latitude", 0.0);
            request.setAttribute("longitude", 0.0);
            request.setAttribute("wifiData", null);
        }

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
