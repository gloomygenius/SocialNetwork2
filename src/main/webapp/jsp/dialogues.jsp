<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="dialogues" type="java.util.Map" scope="request"/>
<div class="row">
    <div class="col-xs-12" id="dialogues">
        <c:forEach var="dialog" items="${dialogues}">
            <div class="row">
                <div class="col-xs-6">
                    <p style="font-size: 2em">${dialog.value.firstName} ${dialog.value.lastName}</p>
                </div>
                <div class="col-xs-4">
                    <a href="<c:url value="/messages?dialog=${dialog.key.id}"/>" class="btn btn-success center-block">Открыть
                        диалог</a>
                </div>
                <div class="col-xs-2">
                    <p>Последнее сообщение:<br>
                            ${dialog.key.lastUpdate}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>