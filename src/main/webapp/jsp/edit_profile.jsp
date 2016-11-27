<%--suppress ELValidationInJSP --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--suppress ELValidationInJSP --%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<c:set var="currentUser" scope="page" value="${sessionScope.currentUser}"/>
<c:set var="refUser" scope="page" value="${sessionScope.referenceUser}"/>
<c:set var="refProfile" scope="page" value="${sessionScope.referenceProfile}"/>

<c:choose>
    <c:when test="${refUser.id eq 0}">
        <%--This user does not exist--%>
        <jsp:forward page="/jsp/error.jsp"/>
    </c:when>
    <c:otherwise>
        <c:set scope="page" var="id" value="${refUser.id}"/>
        <c:set scope="page" var="firstName" value="${refUser.firstName}"/>
        <c:set scope="page" var="lastName" value="${refUser.lastName}"/>
    </c:otherwise>
</c:choose>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-xs-6">
        <div class="row">
            <h2>${firstName} ${lastName}</h2>
        </div>
        <div>
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                    <strong><fmt:message key="error.danger"/></strong> <fmt:message key="${errorMsg}"/>
                </div>
            </c:if>
            <c:if test="${not empty successMsg}">
                <div class="alert alert-success">
                    <strong><fmt:message key="success"/></strong> <fmt:message key="success.changes"/>
                </div>
            </c:if>
            <form class="form-horizontal" action="/edit_profile" method="POST">
                <fieldset>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="telephone"><fmt:message
                                    key="profile.telephone"/></label>
                            <div class="controls">
                                <input type="tel" id="telephone" name="telephone" placeholder="" class="input-xlarge"
                                       value="${refProfile.telephone}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="birthday"><fmt:message key="profile.birthday"/></label>
                            <div class="controls">
                                <input type="date" id="birthday" name="birthday" placeholder="" class="input-xlarge"
                                       value="${refProfile.birthday}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="country"><fmt:message
                                    key="profile.country"/></label>
                            <div class="controls">
                                <input type="text" id="country" name="country" placeholder="" class="input-xlarge"
                                       value="${refProfile.country}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="city"><fmt:message key="profile.city"/></label>
                            <div class="controls">
                                <input type="text" id="city" name="city" placeholder="" class="input-xlarge"
                                       value="${refProfile.city}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="university"><fmt:message
                                    key="profile.university"/></label>
                            <div class="controls">
                                <input type="text" id="university" name="university" placeholder="" class="input-xlarge"
                                       value="${refProfile.university}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="position"><fmt:message
                                    key="profile.position"/></label>
                            <div class="controls">
                                <select name="position" id="position">
                                    <option
                                            <c:if test="${refProfile.position==0}">selected</c:if> value="0">
                                        <fmt:message key="profile.position.0"/></option>
                                    <option
                                            <c:if test="${refProfile.position==1}">selected</c:if> value="1">
                                        <fmt:message key="profile.position.1"/>
                                    </option>
                                    <option
                                            <c:if test="${refProfile.position==2}">selected</c:if> value="2">
                                        <fmt:message key="profile.position.2"/>
                                    </option>
                                    <option
                                            <c:if test="${refProfile.position==3}">selected</c:if> value="3">
                                        <fmt:message key="profile.position.3"/>
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="about"><fmt:message
                                    key="profile.about"/></label>
                            <div class="controls">
                                <textarea id="about" name="about" rows="6" cols="51"
                                          placeholder="Напишите немного о себе">${refProfile.about}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <!-- Button -->
                        <div class="controls">
                            <button class="btn btn-success"><fmt:message key="profile.save"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>