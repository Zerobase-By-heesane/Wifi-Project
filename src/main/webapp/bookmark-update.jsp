<%--
  Created by IntelliJ IDEA.
  User: heesang
  Date: 2024. 6. 5.
  Time: PM 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="zero.openapi.domain.bookmark.model.BookmarkDTO" %>
<%@ page import="zero.openapi.domain.bookmark.model.Bookmark" %>
<%@ page import="java.util.LinkedList" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 50%;
            margin: 0 auto;
        }
        label{
            background-color: green;
            border-radius: 5px;
            color: white;
            align-content: center;
            align-items: center;
            text-align: center;
        }
        .menu {
            text-align: center;
            margin-bottom: 20px;
        }
        .submit-space{
            display: flex;
            align-items: center;
            align-content: center;
            justify-content: center;
        }
        .menu a {
            margin: 0 10px;
            text-decoration: none;
            color: blue;
        }
        .form-group {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }
        .form-group label {
            flex: 1;
        }
        .form-group input {
            flex: 2;
            padding: 5px;
        }
        .submit-btn {
            display: block;
            align-items: center;
            align-content:center;
            padding: 10px;
            background-color: green;
            color: white;
            border: none;
            cursor: pointer;
            text-align: center;
        }
        .submit-btn:hover {
            background-color: darkgreen;
        }
    </style>
</head>
<body>
<br>
<h1> 북마크 그룹 수정</h1>
<br>
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
<% LinkedList<String> bookmark = (LinkedList<String>) request.getAttribute("Targetbookmark");
    Integer id = (Integer) request.getAttribute("id");
%>
<div class="form-container">
    <form action="/bookmark-update" method="post">
        <div class="form-group">
            <label for="bookmarkName">북마크 이름</label>
            <input type="text" id="bookmarkName" name="title" value="<%= bookmark.get(1) %>">
        </div>
        <div class="form-group">
            <label for="order">순서</label>
            <input type="text" id="order" name="order" value="<%= bookmark.get(2) %>">
        </div>
        <input type="hidden" name="id" value="<%= id %>">
        <div class="submit-space">
            <a href="/bookmark-manager">돌아가기</a>
            <button type="submit" class="submit-btn">수정</button>
        </div>

    </form>
</div>


</body>
</html>
