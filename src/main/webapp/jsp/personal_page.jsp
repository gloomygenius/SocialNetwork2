<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<c:set var="currentUser" scope="page" value="${sessionScope.currentUser}"/>
<c:set var="refUser" scope="page" value="${sessionScope.referenceUser}"/>
<c:set var="refProfile" scope="page" value="${sessionScope.referenceProfile}"/>

<c:choose>
    <c:when test="${refUser.id eq 0}">
        <%--This user does not exist--%>
        <jsp:forward page="/jsp/error.jsp"/>
    </c:when>
    <c:otherwise>
        <c:set scope="page" var="id" value="${refUser.id}"/>
        <c:set scope="page" var="firstName" value="${refUser.firstName}"/>
        <c:set scope="page" var="lastName" value="${refUser.lastName}"/>
    </c:otherwise>
</c:choose>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Страница авторизации</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">
        <ul class="menu">
            <li class="mypage"><a href="/id${currentUser.id}">
                <fmt:message key="menu.mypage"/> </a>
            </li>
            <li class="mypage"><a href="#"><fmt:message key="menu.messages"/></a></li>
            <li class="mypage"><a href="/friends"><fmt:message key="menu.friends"/></a></li>
            <li class="mypage"><a href="#"><fmt:message key="menu.myteam"/></a></li>
            <li class="mypage"><a href="#"><fmt:message key="menu.events"/></a></li>
        </ul>
    </div>
    <div id="aside">Правая колонка<br>
        <a href="/logout"><fmt:message key="menu.logout"/></a>
    </div>
    <div id="content">
        <div id="avatar">
            <img src="/img/default_ava.png" alt="avatar"/>
        </div>
        <div>
            <div class="user_info">
                <div class="fio">
                    <p>${firstName} ${lastName}</p>
                </div>
                <c:if test="${not empty refProfile}">
                    <c:if test="${not empty refProfile.birthday}">
                        <div class="block_inf">
                            <div class="f_col">День рождения:</div>
                            <div class="s_col">${refProfile.birthday}</div>
                        </div>
                    </c:if>
                    <c:if test="${not empty refProfile.city}">
                        <div class="block_inf">
                            <div class="f_col">Город:</div>
                            <div class="s_col">${refProfile.city}</div>
                        </div>
                    </c:if>
                    <c:if test="${not empty refProfile.university}">
                        <div class="block_inf">
                            <div class="f_col">Место учёбы:</div>
                            <div class="s_col">${refProfile.university}</div>
                        </div>
                    </c:if>
                    <c:if test="${not empty refProfile.team}">
                        <div class="block_inf">
                            <div class="f_col">Отряд:</div>
                            <div class="s_col">${refProfile.team}</div>
                        </div>
                    </c:if>

                    <c:if test="${not empty refProfile.position}">
                        <div class="block_inf">
                            <div class="f_col">Должность:</div>
                            <div class="s_col">${refProfile.position}</div>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
    <div id="footer">
        <p>Язык сайта (site language):<br>
            <a href="/?language=ru_RU">Русский</a> | <a href="/?language=en_US">English</a> <br></p>
        &copy; <fmt:message key="copyright"/>
    </div>
</div>
</body>
</html>