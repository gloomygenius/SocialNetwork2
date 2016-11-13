package com.socialnetwork.filters;

import com.socialnetwork.common.HttpFilter;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.User;
import lombok.extern.log4j.Log4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.socialnetwork.listeners.Initializer.USER_DAO;
import static com.socialnetwork.servlets.FriendsServlet.INCLUDED_PAGE;


@Log4j
public class SecurityFilter implements HttpFilter {
    public static String CURRENT_USER = "currentUser";
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        log.info("Start SecurityFilter");
        if (session.getAttribute(CURRENT_USER) != null)
            chain.doFilter(request, response);
        else {
            request.setAttribute(INCLUDED_PAGE, "login");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}