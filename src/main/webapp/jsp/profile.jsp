<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<jsp:useBean id="user" type="com.socialnetwork.entities.User" scope="request"/>
<jsp:useBean id="profile" type="com.socialnetwork.entities.Profile" scope="request"/>
<jsp:useBean id="currentUser" type="com.socialnetwork.entities.User" scope="session"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-xs-3" id="avatar">
        <div class="row">
            <img src="<c:url value="/img/default_ava.png"/>" alt="avatar"/>
        </div>
        <c:if test="${user.id==currentUser.id}">
            <div class="row">
                <a href="<c:url value="/edit_profile"/>">
                    <button class="btn btn-success btn-block"><fmt:message key="profile.edit"/></button>
                </a>
            </div>
        </c:if>
    </div>
    <div class="col-xs-9">
        <div class="row">
            <h2>${user.firstName} ${user.lastName}</h2>
        </div>
        <div>
            <p>Информация о пользователе:</p>
            <div class="row">
                <div class="col-xs-6"><fmt:message key="profile.gender"/>:</div>
                <div class="col-xs-6">
                    <c:if test="${user.gender==0}">
                        <fmt:message key="profile.gender.male"/>
                    </c:if>
                    <c:if test="${user.gender==1}">
                        <fmt:message key="profile.gender.female"/>
                    </c:if>
                </div>
            </div>
            <c:if test="${not empty profile}">
                <c:if test="${not empty profile.birthday}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.birthday"/>:</div>
                        <div class="col-xs-6">${profile.birthday}</div>
                    </div>
                </c:if>
                <c:if test="${not empty profile.telephone}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.telephone"/>:</div>
                        <div class="col-xs-6">${profile.telephone}</div>
                    </div>
                </c:if>
                <c:if test="${not empty profile.city}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.city"/>:</div>
                        <div class="col-xs-6">${profile.city}</div>
                    </div>
                </c:if>
                <c:if test="${not empty profile.university}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.university"/>:</div>
                        <div class="col-xs-6">${profile.university}</div>
                    </div>
                </c:if>
                <c:if test="${profile.team!=0}">
                    <div class="row">
                        <div class="col-xs-6">Отряд:</div>
                        <div class="col-xs-6">${profile.team}</div>
                    </div>
                </c:if>

                <c:if test="${profile.position!=0}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.position"/>:</div>
                        <div class="col-xs-6">
                            <fmt:message key="profile.position.${profile.position}"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty profile.about}">
                    <div class="row">
                        <div class="col-xs-6"><fmt:message key="profile.about"/>:</div>
                        <div class="col-xs-6">
                            ${profile.about}
                        </div>
                    </div>
                </c:if>
            </c:if>
        </div>
    </div>
</div>