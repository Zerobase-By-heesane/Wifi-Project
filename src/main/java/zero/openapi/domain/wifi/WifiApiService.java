package zero.openapi.domain.wifi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import zero.openapi.common.database.DBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;

@WebServlet(name = "WifiApiService", value = "/load-wifi")
public class WifiApiService extends HttpServlet {

    private final static String privateKey = "74544b787667686b36354964736975";
    private final static String API_URL = "http://openapi.seoul.go.kr:8088/"; // 서울시 공공와이파이 API URL
    private final static String prefix = "/json/TbPublicWifiInfo";

    private DBHandler dbHandler;
    private Integer startIndex = 1;
    private Integer endIndex = 1000;

    public void init() {
        dbHandler = new DBHandler();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("WifiApiService doGet called");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        JsonArray allWifiArray = new JsonArray(); // 전체 데이터를 담을 JsonArray

        try {
            Integer TOTAL_COUNT = 24598;
            Integer PAGE_SIZE = 1000;
            for (int i = 0; i < TOTAL_COUNT / PAGE_SIZE; i++) {
                JsonObject pageData = getWifiData(); // 각 페이지의 데이터를 가져옴
                JsonArray wifiData = pageData.getAsJsonArray("row"); // "row" 데이터를 추출

                startIndex += PAGE_SIZE;
                endIndex = Math.min(endIndex + PAGE_SIZE, TOTAL_COUNT);

                for (int j = 0; j < wifiData.size(); j++) {
                    dbHandler.insertWifiData(wifiData.get(j).getAsJsonObject());
                    allWifiArray.add(wifiData.get(j).getAsJsonObject()); // 데이터를 JsonArray에 추가
                }
            }

            request.setAttribute("wifiCount", allWifiArray.size());
            request.getRequestDispatcher("/load-wifi.jsp").forward(request, response);

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    public JsonObject getWifiData() throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + privateKey + prefix + "/"+startIndex + "/" + endIndex)
                .build();

        try (Response apiResponse = client.newCall(request).execute()) {
            if (!apiResponse.isSuccessful()) {
                throw new IOException("Unexpected code " + apiResponse);
            }

            String responseData = apiResponse.body().string();

            JsonReader reader = new JsonReader(new StringReader(responseData));
            reader.setLenient(true);

            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            return jsonObject.getAsJsonObject("TbPublicWifiInfo");
        }
    }
}
