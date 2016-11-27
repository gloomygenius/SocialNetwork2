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
        <form action="/messages?dialog=${requestScope.dialog}&limit=${requestScope.limit}&offset=${requestScope.offset}" method="post">
            <div class="form-group">
                <label for="message">Сообщение:</label>
                <input type="hidden" class="form-control" name="dialog" value="${requestScope.dialog}">
                <input type="hidden" class="form-control" name="limit" value="${requestScope.limit}">
                <input type="hidden" class="form-control" name="offset" value="${requestScope.offset}">
                <input type="text" class="form-control" id="message" name="message">
            </div>
            <button type="submit" class="btn btn-success">Отправить</button>
        </form>
        <a href="/messages?dialog=${requestScope.dialog}&limit=${requestScope.limit}&offset=${requestScope.offset}">
            <button type="submit" class="btn btn-primary">Обновить</button>
        </a>
    </div>
</div>