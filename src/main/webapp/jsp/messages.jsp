<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tfmt" uri="/WEB_INF/taglib.tld" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<jsp:useBean id="messages" type="java.util.Set" scope="request"/>
<jsp:useBean id="userMap" type="java.util.Map" scope="request"/>

<div class="row">
    <div class="col-xs-12" id="dialogues">
        <c:if test="${messages.size() == requestScope.limit}">
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
            <c:forEach var="message" items="${messages}">
                <tr>
                    <td>${userMap[message.sender]}</td>
                    <td>${message.message}</td>
                    <td><tfmt:format time="${message.time}"/></td>
                </tr>

            </c:forEach>
            </tbody>
        </table>
        <form action="<c:url value="/messages?dialog=${requestScope.dialog}&limit=${requestScope.limit}&offset=${requestScope.offset}"/>"
              method="post">
            <div class="form-group">
                <label for="message">Сообщение:</label>
                <input type="hidden" class="form-control" name="dialog" value="${requestScope.dialog}">
                <input type="hidden" class="form-control" name="limit" value="${requestScope.limit}">
                <input type="hidden" class="form-control" name="offset" value="${requestScope.offset}">
                <input type="text" class="form-control" id="message" name="message">
            </div>
            <button type="submit" class="btn btn-success">Отправить</button>
        </form>
        <a href="<c:url value="/messages?dialog=${requestScope.dialog}&limit=${requestScope.limit}&offset=${requestScope.offset}"/>">
            <button type="submit" class="btn btn-primary">Обновить</button>
        </a>
    </div>
</div>