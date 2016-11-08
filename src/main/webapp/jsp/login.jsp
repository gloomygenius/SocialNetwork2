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
    <title>Страница авторизации</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">Левая колонка</div>
    <div id="aside">Правая колонка</div>
    <div id="content">
        Надо залогиниться! <br>
        <form method="POST" action="j_security_check">
            <input name="j_username" title="Login"/><br/>
            <input type="password" name="j_password" autocomplete="off" title="Password"/><br/>
            <input type="submit" value="submit"/>
        </form><br>
        <a href="/regpage">Зарегистрироваться</a>
    </div>
    <div id="footer">&copy; Василий Бобков</div>
</div>
</body>
</html>