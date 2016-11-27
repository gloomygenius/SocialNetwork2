<%--suppress JspAbsolutePathInspection --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="<c:url value="/favicon.ico"/>">

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
            <a class="navbar-brand" href="<c:url value="/"/>">Отрядники</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">Выбрать язык<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/locale?language=ru_RU<c:if test="${not empty pageContext.request.queryString}">&${pageContext.request.queryString}</c:if>">Русский</a>
                        </li>
                        <li>
                            <a href="/locale?language=en_US<c:if test="${not empty pageContext.request.queryString}">&${pageContext.request.queryString}</c:if>">English</a>
                        </li>
                    </ul>
                </li>
                <c:if test="${not empty currentUser}">
                    <li><a href="${pageContext.request.contextPath}/logout"><fmt:message key="menu.logout"/></a></li>
                    <%--suppress ELValidationInJSP --%>
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
                    <li><a href="/id${currentUser.id}"><fmt:message key="menu.mypage"/></a></li>
                    <li>
                        <a href="<c:url value="/dialogues"/>"><fmt:message key="menu.dialogues"/></a></li>
                    <li>
                        <a href="<c:url value="/friends"/>"><fmt:message key="menu.friends"/>
                            <c:if test="${sessionScope.newFriends!=0 and not empty sessionScope.newFriends}">
                                [+${sessionScope.newFriends}]
                            </c:if>
                        </a></li>
                </ul>
            </c:if>
        </div>
        <div class="col-xs-10 col-md-10 col-lg-10">
            <c:choose>
                <c:when test="${not empty requestScope.includedPage}">
                    <jsp:include page="jsp/${requestScope.includedPage}.jsp"/>
                </c:when>
                <c:otherwise>
                    <h1>Добро пожаловать в социальную сеть "Отрядники"!</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>