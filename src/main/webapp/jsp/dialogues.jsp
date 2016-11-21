<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-xs-12" id="dialogues">
        <h3>Здесь будут сообщения</h3>
        <c:forEach var="dialog" items="${requestScope.dialogues}">
            <div class="row">
                <div class="col-xs-6">
                    <p style="font-size: 2em">${dialog.value.firstName} ${dialog.value.lastName}</p>
                </div>
                <div class="col-xs-4">
                    <a href="<c:url value="/messages?dialog=${dialog.key.id}"/>" class="btn btn-success center-block">Открыть диалог</a>
                </div>
                <div class="col-xs-2">
                    <p>Последнее сообщение:<br>
                    ${dialog.key.lastUpdate}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>