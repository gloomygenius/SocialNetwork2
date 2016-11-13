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

<div class="row">
    <div class="col-xs-6" id="avatar">
        <img src="/img/default_ava.png" alt="avatar"/>
    </div>
    <div class="col-xs-6">
        <div class="row">
            <p class="text-center">
            <h2>${firstName} ${lastName}</h2></p>
        </div>
        <div>
            <p>Информация о пользователе:</p>
            <div class="row">
                <div class="col-xs-6">Пол:</div>
                <div class="col-xs-6">
                    <c:if test="${refUser.gender==0}">
                        <fmt:message key="profile.gender.male"/>
                    </c:if>
                    <c:if test="${refUser.gender==1}">
                        <fmt:message key="profile.gender.female"/>
                    </c:if>
                </div>
            </div>
            <c:if test="${not empty refProfile}">
                <c:if test="${not empty refProfile.birthday}">
                    <div class="row">
                        <div class="col-xs-6">День рождения:</div>
                        <div class="col-xs-6">${refProfile.birthday}</div>
                    </div>
                </c:if>
                <c:if test="${not empty refProfile.city}">
                    <div class="row">
                        <div class="col-xs-6">Город:</div>
                        <div class="col-xs-6">${refProfile.city}</div>
                    </div>
                </c:if>
                <c:if test="${not empty refProfile.university}">
                    <div class="row">
                        <div class="col-xs-6">Место учёбы:</div>
                        <div class="col-xs-6">${refProfile.university}</div>
                    </div>
                </c:if>
                <c:if test="${not empty refProfile.team}">
                    <div class="row">
                        <div class="col-xs-6">Отряд:</div>
                        <div class="col-xs-6">${refProfile.team}</div>
                    </div>
                </c:if>

                <c:if test="${not empty refProfile.position}">
                    <div class="row">
                        <div class="col-xs-6">Должность:</div>
                        <div class="col-xs-6">${refProfile.position}</div>
                    </div>
                </c:if>
            </c:if>
        </div>
    </div>
</div>