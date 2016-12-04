<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="errorMsg" class="java.lang.String" scope="request"/>
<div class="row">
    <div class="col-xs-12">
        <div class="alert alert-danger">
            <strong><fmt:message key="error.danger"/></strong> <fmt:message key="${errorMsg}"/>
        </div>
    </div>
</div>