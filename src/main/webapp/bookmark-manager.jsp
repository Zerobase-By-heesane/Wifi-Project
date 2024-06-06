<%--
  Created by IntelliJ IDEA.
  User: heesang
  Date: 2024. 6. 5.
  Time: PM 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="zero.openapi.domain.bookmark.model.CustomBookmark" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .action-links a {
            margin-right: 10px;
        }
    </style>
    <script>
        function bookmarkAdd(){
            window.location.href = "bookmark-insert";
        }
    </script>
</head>
<body>

<br>
    <h1>북마크 그룹</h1>

    <div>
        <a href="${pageContext.request.contextPath}/home">홈</a>
        |
        <a href="${pageContext.request.contextPath}/history">위치 히스토리 목록</a>
        |
        <a href="${pageContext.request.contextPath}/load-wifi">Open API 와이파이 정보 가져오기</a>
        |
        <a href="${pageContext.request.contextPath}/bookmark">북마크</a>
        |
        <a href="${pageContext.request.contextPath}/bookmark-manager">북마크 관리</a>
    </div>
    <div>
        <button id="insert-button" onclick= "bookmarkAdd()"> 북마크 그룹 이름 추가</button>
    </div>
    <br>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>북마크 이름</th>
            <th>순서</th>
            <th>등록일자</th>
            <th>수정일자</th>
            <th>비고</th>
        </tr>
        </thead>
        <tbody>
        <%
            LinkedList<CustomBookmark> bookmarks = (LinkedList<CustomBookmark>) request.getAttribute("bookmarks");
            if(bookmarks != null){
                for(CustomBookmark bookmark : bookmarks){
        %>
        <tr>
            <td><%= bookmark.getID() %></td>
            <td><%= bookmark.getTitle() %></td>
            <td><%= bookmark.getOrder() %></td>
            <td><%= bookmark.getRegisterDate() %></td>
            <td><%=(bookmark.getUpdateDate() == null) ? "" : bookmark.getUpdateDate()%></td>
            <td>
                <a href="bookmark-update?id=<%= bookmark.getID() %>">수정</a>
                <a href="bookmark-delete?id=<%= bookmark.getID() %>">삭제</a>
            </td>
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
        </tbody>
    </table>

</body>
</html>
