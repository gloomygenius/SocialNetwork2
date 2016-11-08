<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><fmt:message key="title.mainpage"/></title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
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
            <li class="mypage"><a href="#"><fmt:message key="menu.friends"/></a></li>
            <li class="mypage"><a href="#"><fmt:message key="menu.myteam"/></a></li>
            <li class="mypage"><a href="#"><fmt:message key="menu.events"/></a></li>
        </ul>
    </div>
    <div id="aside">Правая колонка<br>
        <a href="/logout"><fmt:message key="menu.logout"/></a>
    </div>
    <div id="content">Добро пожаловать!</div>
    <div id="footer">
        <p>Язык сайта (site language):<br>
            <a href="/?language=ru_RU">Русский</a> | <a href="/?language=en_US">English</a> <br></p>
        &copy; <fmt:message key="copyright"/>
    </div>
</div>
</body>
</html>