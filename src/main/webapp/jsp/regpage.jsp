<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--suppress ELValidationInJSP --%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<div class="row">
    <div class="col-xs-12">
        <form class="form-horizontal" action="/registration" method="POST">
            <fieldset>
                <div id="legend">
                    <legend class="area-legend-symbol">Register</legend>
                </div>
                <c:if test="${not empty errorMsg}">
                    <div class="alert alert-danger">
                        <strong><fmt:message key="error.danger"/></strong> <fmt:message key="${errorMsg}"/>
                    </div>
                </c:if>
                <div class="control-group">
                    <!-- FirstName -->
                    <label class="control-label" for="first_name">First name</label>
                    <div class="controls">
                        <input type="text" id="first_name" name="first_name" placeholder="" class="input-xlarge"
                               value="${param.first_name}">
                        <p class="help-block">Username can contain any letters or numbers, without spaces</p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- FirstName -->
                    <label class="control-label" for="last_name">Last name</label>
                    <div class="controls">
                        <input type="text" id="last_name" name="last_name" placeholder="" class="input-xlarge"
                               value="${param.last_name}">
                        <p class=" help-block">Username can contain any letters or numbers, without spaces</p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- E-mail -->
                    <label class="control-label" for="email">E-mail</label>
                    <div class="controls">
                        <input type="text" id="email" name="email" placeholder="" class="input-xlarge"
                               value="${param.email}">
                        <p class=" help-block">Please provide your E-mail</p>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="gender">Gender</label>
                    <select id="gender" name="gender" class="form-control input-xlarge">
                        <option
                                <c:if test="${param.gender=='male'}">selected</c:if> value="male">Male
                        </option>
                        <option
                                <c:if test="${param.gender=='female'}">selected</c:if> value="female">Female
                        </option>
                    </select>
                </div>

                <div class="control-group">
                    <!-- Password-->
                    <label class="control-label" for="password">Password</label>
                    <div class="controls">
                        <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
                        <p class="help-block">Password should be at least 4 characters</p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Password -->
                    <label class="control-label" for="password_confirm">Password (Confirm)</label>
                    <div class="controls">
                        <input type="password" id="password_confirm" name="password_confirm" placeholder=""
                               class="input-xlarge">
                        <p class="help-block">Please confirm password</p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Button -->
                    <div class="controls">
                        <button class="btn btn-success">Register</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>