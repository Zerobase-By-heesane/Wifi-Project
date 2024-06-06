<%--
  Created by IntelliJ IDEA.
  User: heesang
  Date: 2024. 6. 5.
  Time: PM 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.lang.String" %>
<%@ page import="zero.openapi.domain.wifi.WiFiInfo" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
        }
        th {
            background-color: green;
            color: white;
        }
        .detail {
            background-color: green;
            color: white;
            text-align: left;
        }
    </style>
</head>
<body>
<script>
    function registerInBookmark(){
        const selectElement = document.getElementById('group-select');
        const selectedOption = selectElement.options[selectElement.selectedIndex].value;
        const wifiId = document.getElementById('hidden').textContent;

        const params = new URLSearchParams();

        params.append('wifiId', wifiId);
        params.append('bookmarkGroup', selectedOption);
        console.log("selectedOption: ", selectedOption);
        console.log("wifiId: ", wifiId);
        fetch('/detail',{
            method: 'POST',
            body: params.toString(),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(response => {
            window.location.href = "bookmark"
        })

    }

    window.onload = function() {
        // localStorage에서 'wifiDetail' 데이터를 가져옴
        const distance = localStorage.getItem('distance');
        document.getElementById('distance').textContent = distance;


        const workDttm = document.getElementById('workDate').textContent;
        const date = new Date(Number(workDttm));
        const padZero = (num) => num.toString().padStart(2, '0');
        const year = date.getFullYear();
        const month = padZero(date.getMonth() + 1); // 월은 0부터 시작하므로 1을 더해야 함
        const day = padZero(date.getDate());
        const hours = padZero(date.getHours());
        const minutes = padZero(date.getMinutes());
        const seconds = padZero(date.getSeconds());
        const workcontent = year+"-"+month+"-"+day+"T"+hours+":"+minutes+":"+seconds;
        console.log(workcontent)
        document.getElementById('workDate').textContent = workcontent;

        const hide = document.getElementById('hidden');
        console.log(hide.textContent);
    };
</script>
<br>
<h1>와이파이 정보 구하기</h1>

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
<div id="hidden" style="display: none">${wifi.ID}</div>
<div>
    <label>
        <select id="group-select">
            <option>북마크 그룹 이름 선택</option>
            <%
                LinkedList<String> bookmarkGroupList = (LinkedList<String>) request.getAttribute("titles");
                for (String bookmarkGroup : bookmarkGroupList) {
            %>
            <option><%= bookmarkGroup %></option>
            <%
                }
            %>
        </select>
    </label>
    <button onclick="registerInBookmark()">북마크 추가하기</button>
</div>
<div class="container">
    <table>
        <tr>
            <td class="detail">거리(Km)</td>
            <td id="distance"></td>
        </tr>
        <tr>
            <td class="detail">관리번호</td>
            <td id="mgrNo">${wifi.mgrNo}</td>
        </tr>
        <tr>
            <td class="detail">자치구</td>
            <td id="district">${wifi.wrdOfc}</td>
        </tr>
        <tr>
            <td class="detail">와이파이명</td>
            <td id="name">${wifi.mainNm}</td>
        </tr>
        <tr>
            <td class="detail">도로명주소</td>
            <td id="roadAddress">${wifi.addr1}</td>
        </tr>
        <tr>
            <td class="detail">상세주소</td>
            <td id="detailAddress">${wifi.addr2}</td>
        </tr>
        <tr>
            <td class="detail">설치위치(층)</td>
            <td id="installLocation">${wifi.instlFloor}</td>
        </tr>
        <tr>
            <td class="detail">설치유형</td>
            <td id="installType">${wifi.instlTy}</td>
        </tr>
        <tr>
            <td class="detail">설치기관</td>
            <td id="installAgency">${wifi.instlMby}</td>
        </tr>
        <tr>
            <td class="detail">서비스구분</td>
            <td id="serviceType">${wifi.svcSe}</td>
        </tr>
        <tr>
            <td class="detail">망종류</td>
            <td id="networkType">${wifi.cmcWr}</td>
        </tr>
        <tr>
            <td class="detail">설치년도</td>
            <td id="installYear">${wifi.cnstcYear}</td>
        </tr>
        <tr>
            <td class="detail">실내외구분</td>
            <td id="indoorOutdoor">${wifi.inoutDoor}</td>
        </tr>
        <tr>
            <td class="detail">WIFI접속환경</td>
            <td id="wifiEnv">${wifi.remars3}</td>
        </tr>
        <tr>
            <td class="detail">X좌표</td>
            <td id="xCoord">${wifi.lat}</td>
        </tr>
        <tr>
            <td class="detail">Y좌표</td>
            <td id="yCoord">${wifi.lnt}</td>
        </tr>
        <tr>
            <td class="detail">작업일자</td>
            <td id="workDate">${wifi.workDttm}</td>
        </tr>

    </table>
</div>



<br>


</body>
</html>
