<%--suppress ELValidationInJSP --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--suppress ELValidationInJSP --%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<c:set var="currentUser" scope="page" value="${sessionScope.currentUser}"/>
<c:set var="refUser" scope="page" value="${sessionScope.referenceUser}"/>
<c:set var="refProfile" scope="page" value="${sessionScope.referenceProfile}"/>

<c:set scope="page" var="id" value="${refUser.id}"/>
<c:set scope="page" var="firstName" value="${refUser.firstName}"/>
<c:set scope="page" var="lastName" value="${refUser.lastName}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-xs-3" id="avatar">
        <div class="row">
            <img src="<c:url value="/img/default_ava.png"/>" alt="avatar"/>
        </div>
        <c:if test="${refUser.id==currentUser.id}">
            <div class="row">
                <a href="/edit_profile">
                    <button class="btn btn-success btn-block"><fmt:message key="profile.edit"/></button>
                </a>
            </div>
        </c:if>
    </div>
    <div class="col-xs-9">
        <div class="row">
            <h2>${firstName} ${lastName}</h2>
        </div>
        <div>
            <p>Информация о пользователе:</p>
            <div class="row">
                <div class="col-xs-6"><fmt:message key="profile.gender"/>:</div>
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
                        <div class="col-xs-6"><fmt:message key="profile.birthday"/>:</div>
                        <div class="col-xs-6">${refProfile.birthday}</div>
                    </div>
                </c:if>
                <c:if test="${not empty refProfile.telephone}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.telephone"/>:</div>
                        <div class="col-xs-6">${refProfile.telephone}</div>
                    </div>
                </c:if>
                <c:if test="${not empty refProfile.city}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.city"/>:</div>
                        <div class="col-xs-6">${refProfile.city}</div>
                    </div>
                </c:if>
                <c:if test="${not empty refProfile.university}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.university"/>:</div>
                        <div class="col-xs-6">${refProfile.university}</div>
                    </div>
                </c:if>
                <c:if test="${refProfile.team!=0}">
                    <div class="row">
                        <div class="col-xs-6">Отряд:</div>
                        <div class="col-xs-6">${refProfile.team}</div>
                    </div>
                </c:if>

                <c:if test="${refProfile.position!=0}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.position"/>:</div>
                        <div class="col-xs-6">
                            <fmt:message key="profile.position.${refProfile.position}"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty refProfile.about}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.about"/>:</div>
                        <div class="col-xs-6">
                            ${refProfile.about}
                        </div>
                    </div>
                </c:if>
            </c:if>
        </div>
    </div>
</div>