<%@ page import="java.util.LinkedList" %><%--
  Created by IntelliJ IDEA.
  User: heesang
  Date: 24. 5. 25.
  Time: 오후 4:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f0f0f0;
        }
    </style>
</head>
<script>
    function remove(id) {
        if (confirm("정말 삭제하시겠습니까?")) {
            fetch("${pageContext.request.contextPath}/history?id=" + id, {
                method: "DELETE"
            }).then(response => {
                if (response.ok) {
                    location.href = "${pageContext.request.contextPath}/history"; // Redirect back to the history page
                } else {
                    alert("삭제 실패");
                }
            }).catch(error => {
                console.error('Error:', error);
                alert("삭제 중 오류 발생");
            });
        }
    }
</script>
<body>
<h1>위치 히스토리 목록</h1>
<div>
    <a href="${pageContext.request.contextPath}/home">홈</a>
    |
    <a href="${pageContext.request.contextPath}/history"> 위치 히스토리 목록 |</a>
    |
    <a href="${pageContext.request.contextPath}/load-wifi"> Open API 와이파이 정보 가져오기</a>
</div>

<br>
<br>

<table>
    <tr>
        <th>ID</th>
        <th> X 좌표 </th>
        <th> Y 좌표 </th>
        <th> 조회일자 </th>
        <th> 비고 </th>
    </tr>
    <%
        LinkedList<LinkedList<String>> histories = (LinkedList<LinkedList<String>>) request.getAttribute("histories");
        if (histories != null) {
            for (LinkedList<String> row : histories) {
    %>
    <tr>
        <td><%= row.get(0) %></td>
        <td><%= row.get(1) %></td>
        <td><%= row.get(2) %></td>
        <td><%= row.get(3) %></td>
        <td>
            <button align="center" onclick="remove(<%=row.get(0)%>)">삭제</button>
        </td>
        <td></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">No data available</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
