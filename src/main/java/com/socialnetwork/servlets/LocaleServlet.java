package com.socialnetwork.servlets;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.socialnetwork.servlets.ErrorHandler.ERROR_MSG;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.LOCALE_ERROR;

@Log4j
public class LocaleServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestProcess(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestProcess(request, response);
    }

    private void requestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute("language", request.getParameter("language"));
        String uri = request.getHeader("referer");
        try {
            response.sendRedirect(uri);
        } catch (IOException e) {
            log.error("Changing language error", e);
            request.setAttribute(ERROR_MSG, LOCALE_ERROR.getPropertyName());
            request.getRequestDispatcher("/error").forward(request, response);
        }
    }
}
