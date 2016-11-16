package com.socialnetwork.servlets;

import com.socialnetwork.dao.RelationDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Relation;
import com.socialnetwork.entities.User;
import lombok.extern.log4j.Log4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.listeners.Initializer.RELATION_DAO;
import static com.socialnetwork.listeners.Initializer.USER_DAO;
import static com.socialnetwork.servlets.FriendsServlet.INCLUDED_PAGE;

/**
 * Created by Vasiliy Bobkov on 09.11.2016.
 */
@Log4j
@WebServlet("/j_security_check")
public class AuthorizationServlet extends HttpServlet {
    private static UserDao userDao;
    private static RelationDao relationDao;
    public static final String NEW_FRIENDS = "newFriends";
    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
        relationDao = (RelationDao) servletContext.getAttribute(RELATION_DAO);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Start security filter");
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("j_password") && parameterMap.containsKey("j_username")) {
            log.info("User try to login...");
            Optional<User> authorize = authorize(parameterMap);
            if (authorize.isPresent()) {
                log.info("Login success");
                HttpSession session = request.getSession(true);
                session.setAttribute(CURRENT_USER, authorize.get());
                session.setAttribute(NEW_FRIENDS,getCountNewFriend(authorize.get().getId()));
                response.sendRedirect("/");
            } else {
                log.info("Login fail");
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }
        } else {
            log.info("Redirecting to login page");
            request.setAttribute(INCLUDED_PAGE, "login");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            // TODO: 22/10/2016 посмотреть что можно сделать что бы не терять информацию о странице куда пользователь зашёл
            requestDispatcher.forward(request, response);
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

    private int getCountNewFriend(long id) {
        Relation incoming = null;
        try {
            incoming = relationDao.getIncoming(id);
        } catch (DaoException e) {
            e.printStackTrace();
            // TODO: 13.11.2016 обработать
        }
        int count = incoming.getIdSet().size();
        return count;
    }
}