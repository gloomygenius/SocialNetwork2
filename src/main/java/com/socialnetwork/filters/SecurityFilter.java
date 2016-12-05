package com.socialnetwork.filters;

import com.socialnetwork.common.HttpFilter;
import lombok.extern.log4j.Log4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.socialnetwork.servlets.CommonHttpServlet.CURRENT_USER;
import static com.socialnetwork.servlets.CommonHttpServlet.INCLUDED_PAGE;


@Log4j
public class SecurityFilter implements HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(CURRENT_USER) != null)
            chain.doFilter(request, response);
        else {
            request.setAttribute(INCLUDED_PAGE, "login");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}