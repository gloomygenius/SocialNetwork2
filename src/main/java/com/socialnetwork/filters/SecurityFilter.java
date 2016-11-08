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
        if (session.getAttribute(CURRENT_USER) != null)
            chain.doFilter(request, response);
        else {
            log.info("Start security filter");
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.containsKey("j_password") && parameterMap.containsKey("j_username")) {
                log.info("User try to login...");
                Optional<User> authorize = authorize(parameterMap);
                if (authorize.isPresent()) {
                    log.info("Login success");
                    session.setAttribute(CURRENT_USER, authorize.get());
                    response.sendRedirect("/");
                } else {
                    log.info("Login fail");
                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                }
            } else {
                log.info("Redirecting to login page");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/login.jsp");
                // TODO: 22/10/2016 посмотреть что можно сделать что бы не терять информацию о странице куда пользователь зашёл
                requestDispatcher.forward(request, response);
            }
        }
    }

    private Optional<User> authorize(Map<String, String[]> parameterMap) {
        String login = parameterMap.get("j_username")[0];
        String password = parameterMap.get("j_password")[0];
        Optional<User> userOptional = Optional.empty();
        try {
            userOptional = userDao.getByEmail(login);
        } catch (DaoException e) {
            e.printStackTrace();
            // TODO: 08.11.2016 отправить к Error
        }
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) return userOptional;
        return Optional.empty();
    }
}