<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<jsp:useBean id="errorMsg" class="java.lang.String" scope="request"/>

<div class="row">
    <div class="col-xs-12">
        <form class="form-horizontal" action="<c:url value="/registration"/>" method="POST">
            <fieldset>
                <div id="legend">
                    <legend class="area-legend-symbol"><fmt:message key="title.registration"/></legend>
                </div>
                <c:if test="${not empty errorMsg}">
                    <div class="alert alert-danger">
                        <strong><fmt:message key="error.danger"/></strong> <fmt:message key="${errorMsg}"/>
                    </div>
                </c:if>
                <div class="control-group">
                    <!-- FirstName -->
                    <label class="control-label" for="first_name"><fmt:message key="regform.fname"/></label>
                    <div class="controls">
                        <input type="text" id="first_name" name="first_name" placeholder="" class="input-xlarge"
                               value="${param.first_name}">
                        <p class="help-block"><fmt:message key="regform.fname.hint"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- FirstName -->
                    <label class="control-label" for="last_name"><fmt:message key="regform.lname"/></label>
                    <div class="controls">
                        <input type="text" id="last_name" name="last_name" placeholder="" class="input-xlarge"
                               value="${param.last_name}">
                        <p class="help-block"><fmt:message key="regform.lname.hint"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- E-mail -->
                    <label class="control-label" for="email">E-mail</label>
                    <div class="controls">
                        <input type="text" id="email" name="email" placeholder="" class="input-xlarge"
                               value="${param.email}">
                        <p class=" help-block"><fmt:message key="regform.email.hint"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="gender"><fmt:message key="regform.gender"/></label>
                    <select id="gender" name="gender" class="form-control input-xlarge">
                        <option
                                <c:if test="${param.gender=='male'}">selected</c:if> value="male">
                            <fmt:message key="regform.male"/>
                        </option>
                        <option
                                <c:if test="${param.gender=='female'}">selected</c:if> value="female">
                            <fmt:message key="regform.female"/>
                        </option>
                    </select>
                </div>

                <div class="control-group">
                    <!-- Password-->
                    <label class="control-label" for="password"><fmt:message key="regform.password"/></label>
                    <div class="controls">
                        <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
                        <p class="help-block"><fmt:message key="regform.password.hint"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Password -->
                    <label class="control-label" for="password_confirm"><fmt:message
                            key="regform.password.confirm"/></label>
                    <div class="controls">
                        <input type="password" id="password_confirm" name="password_confirm" placeholder=""
                               class="input-xlarge">
                        <p class="help-block"><fmt:message key="regform.password.confirm.hint"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Button -->
                    <div class="controls">
                        <button class="btn btn-success"><fmt:message key="regform.register"/></button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>