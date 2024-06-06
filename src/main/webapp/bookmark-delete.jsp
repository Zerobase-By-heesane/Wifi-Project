<%@ page import="java.util.LinkedList" %>
<%@ page import="java.lang.String" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            width: 50%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #37ab6e;
            color: white;
            width: 150px; /* 고정된 크기 */
            text-align: center; /* 텍스트 가운데 정렬 */
        }
        td {
            text-align: center; /* 텍스트 가운데 정렬 */
        }
        .buttons {
            margin-top: 20px;
        }
        .buttons a, .buttons input {
            margin-right: 10px;
            padding: 10px 20px;
            text-decoration: none;
            color: white;
            background-color: #37ab6e;
            border: none;
            border-radius: 5px;
        }
        .buttons input {
            cursor: pointer;
        }
        .buttons a:hover, .buttons input:hover {
            background-color: #2a9b5d;
        }
    </style>
</head>

<body>
<br>
<h1>
    북마크 그룹 삭제
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
<div>
    북마크를 삭제하시겠습니까?
</div>
<br>

<table>
    <tr>
        <th>북마크 이름</th>
        <td>${deletedInfo[1]}</td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td>${deletedInfo[2]}</td>
    </tr>
    <tr>
        <th>등록일자</th>
        <td>${deletedInfo[3]}</td>
    </tr>

</table>
<script>
    function confirmDelete(event) {
        event.preventDefault();

        if (confirm("정말로 삭제하시겠습니까?")) {
            event.target.closest("form").submit();
        }
    }
</script>
<div class="buttons">
    <a href="/bookmark-manager">돌아가기</a>
    <form action="/bookmark-delete" method="post" style="display:inline;">
        <input type="hidden" name="id" value="${deletedInfo[0]}">
        <input type="submit" value="삭제" onclick="confirmDelete(event)">
    </form>
</div>
</body>
</html>
