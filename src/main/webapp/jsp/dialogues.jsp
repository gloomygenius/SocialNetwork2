<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="/WEB_INF/taglib.tld" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="dialogues" type="java.util.Map" scope="request"/>
<div class="row">
    <div class="col-xs-12" id="dialogues">
        <c:forEach var="dialog" items="${dialogues}">
            <div class="row">
                <div class="col-xs-6">
                    <p style="font-size: 2em">
                        <a href="/id${dialog.value.id}">${dialog.value.firstName} ${dialog.value.lastName}</a>
                    </p>
                </div>
                <div class="col-xs-4">
                    <a href="<c:url value="/messages?dialog=${dialog.key.id}"/>" class="btn btn-success center-block">
                        <fmt:message key="dialogues.open"/></a>
                </div>
                <div class="col-xs-2">
                    <p><fmt:message key="dialogues.lastMessage"/>:<br>
                            <custom:format time="${dialog.key.lastUpdate}"/></p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>