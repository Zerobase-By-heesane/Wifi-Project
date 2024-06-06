<%--
  Created by IntelliJ IDEA.
  User: heesang
  Date: 24. 5. 25.
  Time: 오전 4:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <br>
    <h1 align="center" > <%= request.getAttribute("wifiCount")%>개의 WiFi 정보를 정상적으로 저장하였습니다. </h1>
    <br>
    <div align="center">
        <a href="home.jsp">홈 으로 가기</a>
    </div>

</body>
</html>
