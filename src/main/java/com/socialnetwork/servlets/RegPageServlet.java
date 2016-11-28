package com.socialnetwork.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vasiliy Bobkov on 13.11.2016.
 */
public class RegPageServlet extends HttpServlet {
    public static final String INCLUDED_PAGE = "includedPage";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(INCLUDED_PAGE, "regpage");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
