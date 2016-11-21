<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--suppress ELValidationInJSP --%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-xs-12 col-md-8 col-lg-6 center-block">
        <h1 class="text-center login-title"><fmt:message key="login.signin"/></h1>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                <strong><fmt:message key="error.danger"/></strong> <fmt:message key="${errorMsg}"/>
            </div>
        </c:if>
        <div class="account-wall">
            <img class="profile-img center-block" src="/img/login/photo.png" alt="">
            <form class="form-signin" action="/j_security_check" method="post">
                <input type="text" class="form-control" placeholder="Email" name="j_username" value="${param.j_username}" required autofocus>
                <input type="password" class="form-control " placeholder="<fmt:message key="password"/>" name="j_password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    <fmt:message key="login.signin.btn"/>
                </button>
            </form>
        </div>
        <p class="text-center"><a href="/regpage" class="new-account btn btn-success btn-block">
            <fmt:message key="login.create"/></a></p>
    </div>
</div>