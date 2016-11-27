package com.socialnetwork.servlets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorHandler extends HttpServlet {
    public static final String INCLUDED_PAGE = "includedPage";
    public static final String ERROR_MSG = "errorMsg";
    public static final String SUCCESS_MSG = "successMsg";
    static public final String ERROR_HANDLER = "errorHandler";

    @RequiredArgsConstructor
    public enum ErrorCode {
        ERROR_404("error.404"),
        REGISTRATION_FAIL("error.registrationFail"),
        LOGIN_FAIL("error.loginFail"),
        FRIENDS_SEARCH_FAIL("error.friends.search"),
        LOCALE_ERROR("error.locale"),
        COMMON_ERROR("error.common"),
        USER_NOT_FOUND("error.userNotFound"),
        NOT_AUTH("error.notAuth"),
        EMAIL_ALREADY_EXIST("error.emailExist");

        @Getter
        private final String propertyName;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestProcess(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestProcess(request, response);
    }

    private void requestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = request.getAttribute(ERROR_MSG) == null ? "error.common" : (String) request.getAttribute(ERROR_MSG);
        request.setAttribute(ERROR_MSG, msg);
        request.setAttribute(INCLUDED_PAGE, "error");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}