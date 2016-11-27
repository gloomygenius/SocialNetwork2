package com.socialnetwork.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;

public class LogoutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(CURRENT_USER) != null) {
            session.removeAttribute(CURRENT_USER);
        }
        response.sendRedirect("/");
    }
}