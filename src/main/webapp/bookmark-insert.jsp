<%--
  Created by IntelliJ IDEA.
  User: heesang
  Date: 2024. 6. 5.
  Time: PM 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        h1 {
            text-align: center;
        }
        .menu {
            text-align: center;
            margin-bottom: 20px;
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
<h1>
    북마크 그룹 추가
</h1>
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
<br>
<div class="form-container">
    <form action="/bookmark-insert" method="post">
        <div class="form-group">
            <label for="bookmarkName">북마크 이름</label>
            <input type="text" id="bookmarkName" name="title">
        </div>
        <div class="form-group">
            <label for="order">순서</label>
            <input type="text" id="order" name="order">
        </div>
        <button type="submit" class="submit-btn">추가</button>
    </form>
</div>
</body>
</html>
