<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%--%>
<%--    response.sendRedirect(request.getContextPath() + "/");--%>
<%--%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
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
    <script>
        function getCurrentLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition, showError);
            } else {
                alert("이 브라우저는 Geolocation을 지원하지 않습니다.");
            }
        }

        function showPosition(position) {
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;

            document.getElementById("latitude").value = latitude;
            document.getElementById("longitude").value = longitude;
        }

        function showError(error) {
            switch (error.code) {
                case error.PERMISSION_DENIED:
                    alert("위치 정보 접근 권한이 거부되었습니다.");
                    break;
                case error.POSITION_UNAVAILABLE:
                    alert("위치 정보를 찾을 수 없습니다.");
                    break;
                case error.TIMEOUT:
                    alert("위치 정보 요청 시간 제한이 초과되었습니다.");
                    break;
                default:
                    alert("알 수 없는 오류가 발생했습니다.");
            }
        }

        function isLatAndLntValid() {
            const latitude = document.getElementById("latitude").value;
            const longitude = document.getElementById("longitude").value;

            if (latitude == 0.0 || longitude == 0.0) {
                alert("위치 정보를 먼저 가져와주세요.");
                return false;
            }
            return true;
        }

        function saveMyLocation(lat, lng) {
            if (!isLatAndLntValid()) {
                return;
            }

            const params = new URLSearchParams();
            params.append('latitude', lat);
            params.append('longitude', lng);

            fetch('/history', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: params.toString()
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                })
                .catch(error => console.error('Error:', error));
        }

        function loadWifiInfo() {
            if (!isLatAndLntValid()) {
                return;
            }
            const latitude = document.getElementById("latitude").value;
            const longitude = document.getElementById("longitude").value;
            saveMyLocation(latitude, longitude);

            window.location.href = "/home?latitude=" + latitude + "&longitude=" + longitude;
        }
        window.onload = function() {
            const wifiDataJson = '<%= (String) request.getAttribute("wifiData") %>';
            if (wifiDataJson) {
                const wifiData = JSON.parse(wifiDataJson);
                const tbody = document.getElementById("wifiDataBody");

                wifiData.forEach(wifi => {
                    const tr = document.createElement("tr");

                    const distanceTd = document.createElement("td");
                    distanceTd.textContent = wifi.distance;
                    tr.appendChild(distanceTd);

                    const mgrNoTd = document.createElement("td");
                    mgrNoTd.textContent = wifi.mgrNo;
                    tr.appendChild(mgrNoTd);

                    const wrdOfcTd = document.createElement("td");
                    wrdOfcTd.textContent = wifi.wrdOfc;
                    tr.appendChild(wrdOfcTd);


                    const mainNmTd = document.createElement("td");
                    const mainNmLink = document.createElement("a");
                    mainNmLink.textContent = wifi.mainNm;
                    mainNmLink.href = "#";
                    mainNmLink.onclick = function() {
                        localStorage.setItem("distance", JSON.stringify(wifi.distance));
                        window.location.href = "/detail?id=" + wifi.ID;
                    };

                    mainNmTd.appendChild(mainNmLink);
                    tr.appendChild(mainNmTd);


                    const addr1Td = document.createElement("td");
                    addr1Td.textContent = wifi.addr1;
                    tr.appendChild(addr1Td);

                    const addr2Td = document.createElement("td");
                    addr2Td.textContent = wifi.addr2;
                    tr.appendChild(addr2Td);

                    const instlFloorTd = document.createElement("td");
                    instlFloorTd.textContent = wifi.instlFloor;
                    tr.appendChild(instlFloorTd);

                    const instlTyTd = document.createElement("td");
                    instlTyTd.textContent = wifi.instlTy;
                    tr.appendChild(instlTyTd);

                    const instlMbyTd = document.createElement("td");
                    instlMbyTd.textContent = wifi.instlMby;
                    tr.appendChild(instlMbyTd);

                    const svcSeTd = document.createElement("td");
                    svcSeTd.textContent = wifi.svcSe;
                    tr.appendChild(svcSeTd);

                    const cmcWrTd = document.createElement("td");
                    cmcWrTd.textContent = wifi.cmcWr;
                    tr.appendChild(cmcWrTd);

                    const cnstcYearTd = document.createElement("td");
                    cnstcYearTd.textContent = wifi.cnstcYear;
                    tr.appendChild(cnstcYearTd);

                    const inoutDoorTd = document.createElement("td");
                    inoutDoorTd.textContent = wifi.inoutDoor;
                    tr.appendChild(inoutDoorTd);

                    const remars3Td = document.createElement("td");
                    remars3Td.textContent = wifi.remars3;
                    tr.appendChild(remars3Td);

                    const latTd = document.createElement("td");
                    latTd.textContent = wifi.lat;
                    tr.appendChild(latTd);

                    const lntTd = document.createElement("td");
                    lntTd.textContent = wifi.lnt;
                    tr.appendChild(lntTd);

                    const workDttmTd = document.createElement("td");

                    const date = new Date(Number(wifi.workDttm));

                    const padZero = (num) => num.toString().padStart(2, '0');
                    const year = date.getFullYear();
                    const month = padZero(date.getMonth() + 1); // 월은 0부터 시작하므로 1을 더해야 함
                    const day = padZero(date.getDate());
                    const hours = padZero(date.getHours());
                    const minutes = padZero(date.getMinutes());
                    const seconds = padZero(date.getSeconds());
                    const content = year+"-"+month+"-"+day+"T"+hours+":"+minutes+":"+seconds;
                    workDttmTd.textContent = content;
                    tr.appendChild(workDttmTd);

                    tbody.appendChild(tr);
                });
            }
        };
    </script>
</head>
<body>
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

<div>
    <!-- 파라미터가 null인 경우 기본값을 설정 -->
    <c:set var="latitude" value="${param.latitude != null ? param.latitude : 0.0}" />
    <c:set var="longitude" value="${param.longitude != null ? param.longitude : 0.0}" />


    <div>
        <label for="latitude">LAT: </label>
        <input type="text" id="latitude" name="latitude" value="<c:out value='${latitude}'/>">
        <label for="longitude">LNT: </label>
        <input type="text" id="longitude" name="longitude" value="<c:out value='${longitude}'/>">
        <button onclick="getCurrentLocation()">내 위치 가져오기</button>
        <button onclick="loadWifiInfo()">근처 WiFi 정보 보기</button>
    </div>




</div>
<br>

<table>
    <thead>
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내/외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    </thead>
    <tbody id="wifiDataBody">
    </tbody>
</table>

<div id="response"></div>

</body>
</html>
