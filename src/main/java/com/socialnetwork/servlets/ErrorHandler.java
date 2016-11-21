package com.socialnetwork.servlets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vasiliy Bobkov on 16.11.2016.
 */
@WebServlet("/error")
public class ErrorHandler extends HttpServlet {
    public static final String INCLUDED_PAGE = "includedPage";
    public static final String ERROR_MSG = "errorMsg";
    static public final String ERROR_HANDLER = "errorHandler";

    @RequiredArgsConstructor
    public enum ErrorCode {
        ERROR_404("error.404"),
        REGISTRATION_FAIL("error.registrationFail"),
        LOGIN_FAIL("error.loginFail"),
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
        request.getSession().setAttribute("errorMsg", "error.common");
        request.getSession().setAttribute(INCLUDED_PAGE, "error");

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}