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
    <div class="col-md-12">
        <p class="text-center">Поиск друзей</p>
        <form class="form-search" action="/friends">
            <input type="hidden" class="hidden" name="section" value="search">
            <input type="text" class="input-lg search-query col-xs-10" name="names" value="${param.names}" placeholder="Введите имя и фамилию">
            <button type="submit" class="btn btn-primary col-xs-2">Найти</button>
        </form>
    </div>
</div>

<c:if test="${sessionScope.newFriends!=0 && param.section !='incoming'}">
    <div class="row">
        <div class="col-xs-12">
            <p style="font-size: 2em">
                Есть новые заявки в друзья!
                <a href="/friends?section=incoming">Перейти к заявкам</a>
            </p>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-xs-12" id="friends">
        <c:if test="${param.section=='search' && requestScope.friendSet.isEmpty}">
            <p>Ничего не найдено по запросу "${param.names}" </p>
        </c:if>
        <c:forEach var="user" items="${requestScope.friendsSet}">
            <div class="row">
                <div class="col-xs-3">
                    <img src="img/default_ava_min.png">
                </div>
                <div class="col-xs-6">
                    <p style="font-size: 2em">${user.firstName} ${user.lastName}</p>
                </div>
                <div class="col-xs-3">
                    <a href="#" class="btn btn-success btn-block">Написать сообщение</a><br>
                    <a href="/id${user.id}" class="btn btn-info btn-block">Перейти на страницу</a><br>
                    <c:choose>
                        <c:when test="${requestScope.relationMap[user.id]==0}">
                            <a href="/friends?add=${user.id}" class="btn btn-primary btn-block">Добавить в друзья</a><br>
                        </c:when>
                        <c:when test="${requestScope.relationMap[user.id]==1}">
                            <a href="/friends?remove=${user.id}&relation=1" class="btn btn-warning btn-block">Отменить заявку в друзья</a><br>
                        </c:when>
                        <c:when test="${requestScope.relationMap[user.id]==2}">
                            <a href="/friends?add=${user.id}" class="btn btn-primary btn-block">Принять заявку в друзья</a><br>
                            <a href="/friends?remove=${user.id}&relation=2" class="btn btn-danger btn-block">Отклонить заявку в друзья</a><br>
                        </c:when>
                        <c:when test="${requestScope.relationMap[user.id]==3}">
                            <a href="/friends?remove=${user.id}&relation=3" class="btn btn-danger btn-block">Удалить из друзей</a><br>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</div>