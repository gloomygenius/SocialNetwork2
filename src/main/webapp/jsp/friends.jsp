<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="row">
    <div class="col-md-12">
        <p>Поиск друзей</p>
        <div id="custom-search-input">
            <div class="input-group">
                <input type="text" class="form-control input-lg" placeholder="Введите имя и фамилию"/>
                <span class="input-group-btn">
                        <button class="btn btn-info btn-lg" type="button">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </span>
            </div>
        </div>
    </div>
</div>

<c:if test="${sessionScope.newFriends!=0 && param.section !='incoming'}">
    <div class="row">
        <div class="col-xs-12">
            <p>
                Есть новые заявки в друзья!
                <a href="/friends?section=incoming">Перейти к заявкам</a>
            </p>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-xs-12" id="friends">

        <c:forEach var="user" items="${requestScope.friendsSet}">
            <div class="row">
                <div class="col-xs-3">
                    <img src="img/default_ava_min.png">
                </div>
                <div class="col-xs-6">
                    <p style="font-size: 2em">${user.firstName} ${user.lastName}</p>
                </div>
                <div class="col-xs-3">
                    <a href="#">Написать сообщение</a><br>
                    <a href="/id${user.id}">Перейти на страницу</a><br>
                    <c:if test="${param.section !='incoming'}">
                        <a href="/friends?delete=${user.id}">Удалить из друзей</a><br>
                    </c:if>
                    <c:if test="${param.section =='incoming'}">
                        <a href="/friends?add=${user.id}">Добавить в друзья</a><br>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</div>