<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<jsp:useBean id="errorMsg" class="java.lang.String" scope="request"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="custom" uri="/WEB_INF/taglib.tld" %>


<div class="row">
    <div class="col-md-12">
        <p class="text-center">Поиск друзей</p>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                <strong><fmt:message key="error.danger"/></strong> <fmt:message key="${errorMsg}"/>
            </div>
        </c:if>
        <form class="form-search" action="<c:url value="/friends"/>">
            <input type="hidden" class="hidden" name="action" value="search">
            <input type="text" class="input-lg search-query col-xs-10" name="names" value="${param.names}"
                   placeholder="Введите имя и фамилию">
            <button type="submit" class="btn btn-primary col-xs-2">Найти</button>
        </form>
        <c:if test="${param.action=='search'} && ${requestScope.friendSet.size==0}">
            <p>Ничего не найдено по запросу "${param.names}" </p>
        </c:if>
    </div>
</div>

<c:if test="${sessionScope.newFriends!=0 && param.action !='incoming'}">
    <div class="row">
        <div class="col-xs-12">
            <p style="font-size: 2em">
                Есть новые заявки в друзья!
                <a href="<c:url value="/friends?action=incoming"/>">Перейти к заявкам</a>
            </p>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-xs-12" id="friends">
        <c:forEach var="user" items="${requestScope.friendsSet}">
            <div class="row">
                <div class="col-xs-3">
                    <img class="center-block" src="<custom:link id="${user.id}" type="min_ava" alt="img/default_ava_min.png"/>">
                </div>
                <div class="col-xs-6">
                    <p style="font-size: 2em">${user.firstName} ${user.lastName}</p>
                </div>
                <div class="col-xs-3">
                    <a href="<c:url value="/messages?recipient=${user.id}"/>" class="btn btn-success btn-block">Написать
                        сообщение</a><br>
                    <a href="/id${user.id}" class="btn btn-info btn-block">Перейти на страницу</a><br>
                    <c:choose>
                        <c:when test="${requestScope.relationMap[user.id]==0}">
                            <a href="<c:url value="/friends?action=add&id=${user.id}"/>"
                               class="btn btn-primary btn-block">Добавить в друзья</a><br>
                        </c:when>
                        <c:when test="${requestScope.relationMap[user.id]==1}">
                            <a href="<c:url value="/friends?action=remove&id=${user.id}&relation=1"/>"
                               class="btn btn-warning btn-block">Отменить заявку в друзья</a><br>
                        </c:when>
                        <c:when test="${requestScope.relationMap[user.id]==2}">
                            <a href="<c:url value="/friends?action=add&id=${user.id}"/>"
                               class="btn btn-primary btn-block">Принять заявку в друзья</a><br>
                            <a href="<c:url value="/friends?action=remove&id=${user.id}&relation=2"/>"
                               class="btn btn-danger btn-block">Отклонить заявку в друзья</a><br>
                        </c:when>
                        <c:when test="${requestScope.relationMap[user.id]==3}">
                            <a href="<c:url value="/friends?action=remove&id=${user.id}&relation=3"/>"
                               class="btn btn-danger btn-block">Удалить из друзей</a><br>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
        <c:if test="${requestScope.offset!=0}"><a
                href="<c:url value="/friends?offset=${requestScope.offset-requestScope.limit}&limit=${requestScope.limit}"/>">
            Предыдущая страница</a> </c:if>
        <c:if test="${requestScope.hasNextPage}"><a
                href="<c:url value="/friends?offset=${requestScope.offset+requestScope.limit}&limit=${requestScope.limit}"/>">
            Следующая страница</a> </c:if>
    </div>
</div>