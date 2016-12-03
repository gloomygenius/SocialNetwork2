<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="text"/>
<jsp:useBean id="profile" type="com.socialnetwork.entities.Profile" scope="request"/>
<jsp:useBean id="currentUser" type="com.socialnetwork.entities.User" scope="session"/>
<jsp:useBean id="errorMsg" class="java.lang.String" scope="request"/>
<jsp:useBean id="successMsg" class="java.lang.String" scope="request"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-xs-6">
        <div class="row">
            <h2>${currentUser.firstName} ${currentUser.lastName}</h2>
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
            <form class="form-horizontal" action="<c:url value="/edit_profile"/>" method="POST">
                <fieldset>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="telephone"><fmt:message
                                    key="profile.telephone"/></label>
                            <div class="controls">
                                <input type="tel" id="telephone" name="telephone" placeholder="" class="input-xlarge"
                                       value="${profile.telephone}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="birthday"><fmt:message key="profile.birthday"/></label>
                            <div class="controls">
                                <input type="date" id="birthday" name="birthday" placeholder="" class="input-xlarge"
                                       value="${profile.birthday}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="country"><fmt:message
                                    key="profile.country"/></label>
                            <div class="controls">
                                <input type="text" id="country" name="country" placeholder="" class="input-xlarge"
                                       value="${profile.country}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="city"><fmt:message key="profile.city"/></label>
                            <div class="controls">
                                <input type="text" id="city" name="city" placeholder="" class="input-xlarge"
                                       value="${profile.city}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="control-group col-xs-12">
                            <label class="control-label" for="university"><fmt:message
                                    key="profile.university"/></label>
                            <div class="controls">
                                <input type="text" id="university" name="university" placeholder="" class="input-xlarge"
                                       value="${profile.university}">
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
                                            <c:if test="${profile.position==0}">selected</c:if> value="0">
                                        <fmt:message key="profile.position.0"/></option>
                                    <option
                                            <c:if test="${profile.position==1}">selected</c:if> value="1">
                                        <fmt:message key="profile.position.1"/>
                                    </option>
                                    <option
                                            <c:if test="${profile.position==2}">selected</c:if> value="2">
                                        <fmt:message key="profile.position.2"/>
                                    </option>
                                    <option
                                            <c:if test="${profile.position==3}">selected</c:if> value="3">
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
                                          placeholder="<fmt:message key="profile.writeAbout"/>">${profile.about}</textarea>
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
            <h3><fmt:message key="profile.avaUpload"/></h3>
            <fmt:message key="profile.selectPhoto"/>: <br />
            <form action="${pageContext.request.contextPath}/upload_photo" method="post"
                  enctype="multipart/form-data">
                <input type="file" name="file" size="50" accept="image/jpeg"/>
                <br />
                <input type="submit" value="<fmt:message key="profile.upload"/>" />
            </form>
        </div>
    </div>
</div>