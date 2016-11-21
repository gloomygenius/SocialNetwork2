<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<c:set var="currentUser" scope="page" value="${sessionScope.currentUser}"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <title>Отрядники</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="css/theme.css" rel="stylesheet">

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Отрядники</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">Выбрать язык<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/?language=ru_RU">Русский</a></li>
                        <li><a href="/?language=en_US">English</a></li>
                    </ul>
                </li>
                <c:if test="${not empty currentUser}">
                    <li><a href="${pageContext.request.contextPath}/logout"><fmt:message key="menu.logout"/></a></li>
                    <c:if test="${currentUser.role==2}">
                        <li><a href="#">Админка</a></li>
                    </c:if>
                </c:if>

            </ul>
        </div>
    </div>
</nav>
<div class="container" role="main">
    <div class="row">
        <div class="col-xs-2 col-md-2 col-lg-2">
            <c:if test="${not empty currentUser}">
                <ul class="nav nav-list">
                    <li class="nav-header">Навигация</li>
                    <li ><a href="/id${currentUser.id}"><fmt:message key="menu.mypage"/></a></li>
                    <li>
                        <a href="/dialogues"><fmt:message key="menu.dialogues"/></a></li>
                    <li>
                        <a href="/friends"><fmt:message key="menu.friends"/>
                        <c:if test="${sessionScope.newFriends!=0 and not empty sessionScope.newFriends}">
                            [+${sessionScope.newFriends}]
                        </c:if>
                    </a></li>
                    <li><a href="#"><fmt:message key="menu.myteam"/></a></li>
                    <li><a href="#"><fmt:message key="menu.events"/></a></li>

                </ul>
            </c:if>
        </div>
        <div class="col-xs-9 col-md-9 col-lg-9">

            <c:choose>
                <c:when test="${requestScope.includedPage=='login'}">
                    <jsp:include page="jsp/login.jsp"/>
                </c:when>
                <c:when test="${requestScope.includedPage=='profile'}">
                    <jsp:include page="jsp/profile.jsp"/>
                </c:when>
                <c:when test="${requestScope.includedPage=='friends'}">
                    <jsp:include page="jsp/friends.jsp"/>
                </c:when>
                <c:when test="${requestScope.includedPage=='dialogues'}">
                    <jsp:include page="jsp/dialogues.jsp"/>
                </c:when>
                <c:when test="${requestScope.includedPage=='messages'}">
                    <jsp:include page="jsp/messages.jsp"/>
                </c:when>
                <c:when test="${requestScope.includedPage=='regpage'}">
                    <jsp:include page="jsp/regpage.jsp"/>
                </c:when>
                <c:when test="${requestScope.includedPage=='error'}">
                    <jsp:include page="jsp/error.jsp"/>
                </c:when>
                <c:otherwise>
                    <p>Добро пожаловать в социальную сеть "Отрядники"!</p>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-xs-1 col-md-1 col-lg-1">
            <p class="text-center">Здесь может быть ваша реклама!</p>
        </div>
    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>