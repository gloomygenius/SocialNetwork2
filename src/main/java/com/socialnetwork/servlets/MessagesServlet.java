package com.socialnetwork.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vasiliy Bobkov on 10.11.2016.
 */
@WebServlet("/messages")
public class MessagesServlet extends HttpServlet{
    public static final String INCLUDED_PAGE = "includedPage";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(INCLUDED_PAGE, "messages");
        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            // TODO: 09.11.2016 Обработать
        }
    }
}
