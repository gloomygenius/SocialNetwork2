<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--suppress ELValidationInJSP --%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div class="row">
    <div class="col-xs-12" id="dialogues">
        <c:if test="${requestScope.messages.size() == requestScope.limit}">
            <a href="<c:url value="/messages?dialog=${param.dialog}&limit=${requestScope.limit+15}&offset=${requestScope.offset}"/>">
                Посмотреть предыдущие сообщения</a>
        </c:if>
        <table class="table">
            <thead>
            <tr>
                <th>Отправитель</th>
                <th>Сообщение</th>
                <th>Дата</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="message" items="${requestScope.messages}">
                <tr>
                    <td>${requestScope.userMap[message.sender]}</td>
                    <td>${message.message}</td>
                    <td>${message.time}</td>
                </tr>

            </c:forEach>
            </tbody>
        </table>
        <%--<c:forEach var="message" items="${requestScope.messages}">
            <div class="row">
                <div class="col-xs-3">
                    <p style="font-size: 2em">${requestScope.userMap[message.sender]}</p>
                </div>
                <div class="col-xs-7">
                    <p>${message.message}</p>
                </div>
                <div class="col-xs-2">
                    <p>${message.time}</p>
                </div>
            </div>
        </c:forEach>--%>
        <form action="/messages" method="post">
            <div class="form-group">
                <label for="message">Сообщение:</label>
                <input type="hidden" class="form-control" name="dialog" value="${param.dialog}">
                <input type="hidden" class="form-control" name="limit" value="${requestScope.limit}">
                <input type="hidden" class="form-control" name="offset" value="${requestScope.offset}">
                <input type="text" class="form-control" id="message" name="message">
            </div>
            <button type="submit" class="btn btn-default">Отправить</button>
        </form>
    </div>
</div>