package com.socialnetwork.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Vasiliy Bobkov on 23.11.2016.
 */
@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    private void requestProcess(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        session.setAttribute("language", request.getParameter("language"));
        String uri = request.getHeader("referer");
        try {
            response.sendRedirect(uri);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 23.11.2016 Обработать
        }
    }
}
