package zero.openapi.common.database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import zero.openapi.domain.bookmark.model.CustomBookmark;
import zero.openapi.common.util.DistanceCalculator;
import zero.openapi.domain.wifi.WiFiInfo;
import zero.openapi.domain.bookmark.model.BookmarkDTO;
import zero.openapi.domain.bookmark.model.BookmarkUpdateDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class DBHandler {

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public DBHandler() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
            return;
        }
        openConnection();
    }
    private void openConnection(){
        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:/Users/heesang/zero/openAPI/openapi.db");
            this.statement = this.connection.createStatement();
        }catch(SQLException e) {
            System.err.println("SQLite 데이터베이스에 연결할 수 없습니다.");
            e.printStackTrace();
        }
    }
    private void closeConnection(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public LinkedList<WiFiInfo> getWifiData(float lat, float lnt) {
        openConnection();
        try {
            String sql = "SELECT * FROM wifi_info";
            resultSet = statement.executeQuery(sql);

            LinkedList<WiFiInfo> wifiData = new LinkedList<>();
            while (resultSet.next()) {

                double distance = DistanceCalculator.calculateDistance(lat, lnt, resultSet.getFloat("lat"), resultSet.getFloat("lnt"));
                WiFiInfo info = new WiFiInfo(
                        resultSet.getInt("id"),
                        resultSet.getString("cnstcYear"),
                        resultSet.getString("mgrNo"),
                        resultSet.getString("wrdOfc"),
                        resultSet.getString("mainNm"),
                        resultSet.getString("addr1"),
                        resultSet.getString("addr2"),
                        resultSet.getString("instlFloor"),
                        resultSet.getString("instlTy"),
                        resultSet.getString("instlMby"),
                        resultSet.getString("svcSe"),
                        resultSet.getString("cmcWr"),
                        resultSet.getString("inoutDoor"),
                        resultSet.getString("remars3"),
                        resultSet.getFloat("lat"),
                        resultSet.getFloat("lnt"),
                        resultSet.getString("workDttm"),
                        distance
                );
                wifiData.add(info);
            }

            wifiData.sort((o1, o2) -> {
                if (o1.getDistance() > o2.getDistance()) {
                    return 1;
                } else if (o1.getDistance() < o2.getDistance()) {
                    return -1;
                } else {
                    return 0;
                }
            });

            // 20개만 추출
            if(wifiData.size() > 20){
                wifiData = new LinkedList<>(wifiData.subList(0, 20));
            }
            closeConnection(this.connection);
            return wifiData;
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
        }
        closeConnection(this.connection);
        return null;
    }

    public WiFiInfo getWifiInfo(Integer Id){
        openConnection();
        try {
            String sql = "SELECT * FROM wifi_info WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                WiFiInfo info = new WiFiInfo(
                        resultSet.getInt("id"),
                        resultSet.getString("cnstcYear"),
                        resultSet.getString("mgrNo"),
                        resultSet.getString("wrdOfc"),
                        resultSet.getString("mainNm"),
                        resultSet.getString("addr1"),
                        resultSet.getString("addr2"),
                        resultSet.getString("instlFloor"),
                        resultSet.getString("instlTy"),
                        resultSet.getString("instlMby"),
                        resultSet.getString("svcSe"),
                        resultSet.getString("cmcWr"),
                        resultSet.getString("inoutDoor"),
                        resultSet.getString("remars3"),
                        resultSet.getFloat("lat"),
                        resultSet.getFloat("lnt"),
                        resultSet.getString("workDttm"),
                        0
                );
                return info;
            }
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
            e.printStackTrace();
        } finally {
            closeConnection(this.connection);
        }
        return null;
    }

    public void insertWifiData(JsonElement wifiData) {
        openConnection();
        try {
            String sql = "INSERT INTO wifi_info (" +
                    "mgrNo, wrdOfc, mainNm, addr1, addr2, " +
                    "instlFloor, instlTy, instlMby, svcSe, cmcWr, " +
                    "cnstcYear, inoutDoor, remars3, lat, lnt, workDttm" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            JsonObject jsonObject = wifiData.getAsJsonObject();

            preparedStatement.setString(1, getStringValue(jsonObject, "X_SWIFI_MGR_NO"));
            preparedStatement.setString(2, getStringValue(jsonObject, "X_SWIFI_WRDOFC"));
            preparedStatement.setString(3, getStringValue(jsonObject, "X_SWIFI_MAIN_NM"));
            preparedStatement.setString(4, getStringValue(jsonObject, "X_SWIFI_ADRES1"));
            preparedStatement.setString(5, getStringValue(jsonObject, "X_SWIFI_ADRES2"));
            preparedStatement.setString(6, getStringValue(jsonObject, "X_SWIFI_INSTL_FLOOR"));
            preparedStatement.setString(7, getStringValue(jsonObject, "X_SWIFI_INSTL_TY"));
            preparedStatement.setString(8, getStringValue(jsonObject, "X_SWIFI_INSTL_MBY"));
            preparedStatement.setString(9, getStringValue(jsonObject, "X_SWIFI_SVC_SE"));
            preparedStatement.setString(10, getStringValue(jsonObject, "X_SWIFI_CMCWR"));
            preparedStatement.setString(11, getStringValue(jsonObject, "X_SWIFI_CNSTC_YEAR"));
            preparedStatement.setString(12, getStringValue(jsonObject, "X_SWIFI_INOUT_DOOR"));
            preparedStatement.setString(13, getStringValue(jsonObject, "X_SWIFI_REMARS3"));
            preparedStatement.setFloat(14, getFloatValue(jsonObject, "LAT"));
            preparedStatement.setFloat(15, getFloatValue(jsonObject, "LNT"));
            preparedStatement.setTimestamp(16, getTimestampValue(jsonObject, "WORK_DTTM"));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("데이터를 추가할 수 없습니다.");
            e.printStackTrace();
        } finally {
            closeConnection(this.connection);
        }
    }

    private String getStringValue(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        return element != null && !element.isJsonNull() ? element.getAsString() : null;
    }

    private Float getFloatValue(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        return element != null && !element.isJsonNull() ? element.getAsFloat() : null;
    }

    private Timestamp getTimestampValue(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        return element != null && !element.isJsonNull() ? Timestamp.valueOf(element.getAsString()) : null;
    }

    public LinkedList<LinkedList<String>> getHistoryData() {
        openConnection();
        try {
            String sql = "SELECT * FROM wifi_history ORDER BY view_date DESC";
            resultSet = statement.executeQuery(sql);

            LinkedList<LinkedList<String>> historyData = new LinkedList<>();
            while (resultSet.next()) {
                LinkedList<String> row = new LinkedList<>();
                row.add(resultSet.getString("id"));
                row.add(resultSet.getString("x"));
                row.add(resultSet.getString("y"));
                row.add(resultSet.getString("view_date"));
                historyData.add(row);
            }
            return historyData;
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
            e.printStackTrace();
        }finally {
            closeConnection(this.connection);
        }

        return null;
    }

    public boolean insertHistoryData(float x, float y){
        openConnection();
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDate = localDateTime.format(dtf);

            String sql = "INSERT INTO wifi_history (x, y,view_date) VALUES (?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setFloat(1, x);
            preparedStatement.setFloat(2, y);
            preparedStatement.setString(3, formattedDate);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("데이터를 추가할 수 없습니다.");
            e.printStackTrace();
            return false;
        }finally {
            closeConnection(this.connection);
        }
    }

    public boolean deleteHistoryData(int id){
        openConnection();
        try {
            String sql = "DELETE FROM wifi_history WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("데이터를 삭제할 수 없습니다.");
            e.printStackTrace();
            return false;
        }finally {
            closeConnection(this.connection);
        }
    }

    public LinkedList<CustomBookmark> getBookmarkData() {
        openConnection();
        try {
            String sql = "SELECT * FROM bookmark ORDER BY `order` ASC";
            resultSet = statement.executeQuery(sql);

            LinkedList<CustomBookmark> bookmarkData = new LinkedList<>();
            while (resultSet.next()) {
                CustomBookmark bookmark = new CustomBookmark(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("order"),
                        resultSet.getTimestamp("registerDate"),
                        resultSet.getTimestamp("updateDate")

                );
                bookmarkData.add(bookmark);
            }

            return bookmarkData;
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
            e.printStackTrace();
        }finally {
            closeConnection(this.connection);
        }
        return null;
    }

    public void insertBookmarkData(@NotNull BookmarkDTO bookmarkDTO){
        openConnection();
        try {

            String sql = "INSERT INTO bookmark (name, `order`,registerDate,updateDate) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, bookmarkDTO.getTitle());
            preparedStatement.setInt(2, bookmarkDTO.getOrder());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(4, null);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("데이터를 추가할 수 없습니다.");
            e.printStackTrace();
        }finally {
            closeConnection(this.connection);
        }
    }

    public LinkedList<String> getBookmarkDataById(int id) {
        openConnection();
        try {
            String sql = "SELECT * FROM bookmark WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                LinkedList<String> bookmarkData = new LinkedList<>();
                bookmarkData.add(resultSet.getString("id"));
                bookmarkData.add(resultSet.getString("name"));
                bookmarkData.add(resultSet.getString("order"));
                bookmarkData.add(resultSet.getTimestamp("registerDate").toString());
                return bookmarkData;
            }
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
            e.printStackTrace();
        }finally {
            closeConnection(this.connection);
        }
        return null;
    }
    public LinkedList<String> getDeletedBookmarkDataById(int id){
        openConnection();
        try {
            String sql = "SELECT * FROM bookmark WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                LinkedList<String> bookmarkData = new LinkedList<>();
                bookmarkData.add(resultSet.getString("id"));
                bookmarkData.add(resultSet.getString("name"));
                bookmarkData.add("등록된 WiFi 정보가 없습니다.");
                bookmarkData.add(resultSet.getTimestamp("registerDate").toString());
                return bookmarkData;
            }
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
            e.printStackTrace();
        }finally {
            closeConnection(this.connection);
        }
        return null;
    }
    public void updateBookmarkData(BookmarkUpdateDTO bookmarkUpdateDTO){
        openConnection();
        try {
            String sql = "UPDATE bookmark SET name = ?, `order` = ?, updateDate = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, bookmarkUpdateDTO.getTitle());
            preparedStatement.setInt(2, bookmarkUpdateDTO.getOrder());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(4, bookmarkUpdateDTO.getID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("데이터를 수정할 수 없습니다.");
            e.printStackTrace();
        }finally {
            closeConnection(this.connection);
        }
    }

    public boolean mappingBookmarkAndWiFi(int wifiId, String groupName){
        openConnection();
        try {
            // 중복 검사


            String innerSql2 = "SELECT id FROM bookmark WHERE name = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(innerSql2);
            preparedStatement2.setString(1, groupName);
            ResultSet innerResultSet2 = preparedStatement2.executeQuery();
            Integer bookmarkId = innerResultSet2.getInt("id");

            String innerSql = "SELECT * FROM relation WHERE bookmark_id = ? AND wifi_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(innerSql);
            preparedStatement.setInt(1, bookmarkId);
            preparedStatement.setInt(2, wifiId);

            ResultSet innerResultSet = preparedStatement.executeQuery();
            if(innerResultSet.next()){
                return false;
            }

            String sql = "INSERT INTO relation (bookmark_id, wifi_id, createdAt) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookmarkId);
            preparedStatement.setInt(2, wifiId);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("데이터를 추가할 수 없습니다.");
            e.printStackTrace();
            return false;
        }finally {
            closeConnection(this.connection);
        }
    }

    public LinkedList<LinkedList<String>> getBookmarkAndWifiRelation() {
        openConnection();
        LinkedList<LinkedList<String>> relationData = new LinkedList<>();

        try {
            String sql = "SELECT * FROM relation";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Integer bookmarkId = resultSet.getInt("bookmark_id");
                Integer wifiId = resultSet.getInt("wifi_id");

                // 북마크 이름과 등록일자 가져오기
                String bookmarkName = "";
                String registerDate = "";
                String bookmarkQuery = "SELECT name, registerDate FROM Bookmark WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(bookmarkQuery)) {
                    preparedStatement.setInt(1, bookmarkId);
                    try (ResultSet innerResultSet = preparedStatement.executeQuery()) {
                        if (innerResultSet.next()) {
                            bookmarkName = innerResultSet.getString("name");
                            registerDate = innerResultSet.getTimestamp("registerDate").toString();
                        } else {
                            System.err.println("No bookmark found with id: " + bookmarkId);
                        }
                    }
                }

                // 와이파이 이름 가져오기
                String wifiName = "";
                String wifiQuery = "SELECT mainNm FROM wifi_info WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(wifiQuery)) {
                    preparedStatement.setInt(1, wifiId);
                    try (ResultSet innerResultSet = preparedStatement.executeQuery()) {
                        if (innerResultSet.next()) {
                            wifiName = innerResultSet.getString("mainNm");
                        } else {
                            System.err.println("No wifi found with id: " + wifiId);
                        }
                    }
                }

                LinkedList<String> row = new LinkedList<>();
                row.add(bookmarkId.toString());
                row.add(bookmarkName);
                row.add(wifiName);
                row.add(registerDate);
                relationData.add(row);
            }
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
            e.printStackTrace();
        } finally {
            closeConnection(this.connection);
        }

        return relationData;
    }

    public LinkedList<String> getBookmarkAndWifiRelationByBookmarkId(Integer bookmarkId){
        openConnection();
        try {
            String sql = "SELECT * FROM relation WHERE bookmark_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookmarkId);
            resultSet = preparedStatement.executeQuery();

            LinkedList<String> relationData = new LinkedList<>();

            while (resultSet.next()) {
                // 북마크 이름 가져오기
                String innerSql = "SELECT name,registerDate FROM bookmark WHERE id = ?";
                preparedStatement = connection.prepareStatement(innerSql);
                preparedStatement.setInt(1, bookmarkId);
                ResultSet innerResultSet = preparedStatement.executeQuery();
                String bookmarkName = "";
                String registerDate = "";
                if(innerResultSet.next()){
                    bookmarkName = innerResultSet.getString("name");
                    registerDate = innerResultSet.getTimestamp("registerDate").toString();
                }

                // 와이파이 이름 가져오기
                Integer wifiId = resultSet.getInt("wifi_id");
                innerSql = "SELECT mainNm FROM wifi_info WHERE id = ?";
                preparedStatement = connection.prepareStatement(innerSql);
                preparedStatement.setInt(1, wifiId);
                innerResultSet = preparedStatement.executeQuery();
                String wifiName = "";
                if(innerResultSet.next()){
                    wifiName = innerResultSet.getString("mainNm");
                }
                relationData.add(bookmarkId.toString());
                relationData.add(bookmarkName);
                relationData.add(wifiName);
                relationData.add(registerDate);
            }

            return relationData;
        } catch (SQLException e) {
            System.err.println("데이터를 가져올 수 없습니다.");
            e.printStackTrace();
            return null;
        } finally {
            closeConnection(this.connection);
        }
    }
    public void deleteBookmark(Integer id) {
        openConnection();
        try {
            String sql = "DELETE FROM bookmark WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            // realation 테이블 조회 -> 있으면 삭제, 없으면 pass
            String innerSql = "SELECT * FROM relation WHERE bookmark_id = ?";
            preparedStatement = connection.prepareStatement(innerSql);
            preparedStatement.setInt(1, id);
            ResultSet innerResultSet = preparedStatement.executeQuery();
            if(innerResultSet.next()){
                innerSql = "DELETE FROM relation WHERE bookmark_id = ?";
                preparedStatement = connection.prepareStatement(innerSql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("데이터를 삭제할 수 없습니다.");
            e.printStackTrace();
        } finally {
            closeConnection(this.connection);
        }
    }
}
