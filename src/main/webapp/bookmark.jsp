<%--
  Created by IntelliJ IDEA.
  User: heesang
  Date: 2024. 6. 5.
  Time: PM 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.lang.Integer" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        nav a {
            margin-right: 10px;
            text-decoration: none;
            color: blue;
        }
        nav a:hover {
            text-decoration: underline;
        }
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
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .action a {
            color: red;
            text-decoration: none;
        }
        .action a:hover {
            text-decoration: underline;
        }
        tr.top {
            background-color: #37ab6e !important;
        }
    </style>
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
<br><br>
<table>
    <thead>
    <tr class="top">
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>액션</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="bookmark" items="${bookmarkData}">
        <tr>
            <td>${bookmark[0]}</td>
            <td>${bookmark[1]}</td>
            <td>${bookmark[2]}</td>
            <td>${bookmark[3]}</td>
            <td class="action"><a href="${pageContext.request.contextPath}/bookmark-delete?id=${bookmark[0]}">삭제</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
