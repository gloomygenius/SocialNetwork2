package com.socialnetwork.servlets;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Relation;
import com.socialnetwork.models.User;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.LOGIN_FAIL;

@Log4j
public class AuthorizationServlet extends CommonHttpServlet {
    static final String NEW_FRIENDS = "newFriends";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("j_password") && parameterMap.containsKey("j_username")) {
            Optional<User> authorize = authorize(parameterMap);
            if (authorize.isPresent()) {
                HttpSession session = request.getSession(true);
                session.setAttribute(CURRENT_USER, authorize.get());
                session.setAttribute(NEW_FRIENDS, getCountNewFriend(authorize.get().getId()));
                response.sendRedirect("/");
            } else {
                log.info("Login fail");
                request.setAttribute(ERROR_MSG, LOGIN_FAIL.getPropertyName());
                request.setAttribute(INCLUDED_PAGE, "login");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } else {
            log.info("Redirecting to login page");
            request.setAttribute(INCLUDED_PAGE, "login");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private Optional<User> authorize(Map<String, String[]> parameterMap) {
        String login = parameterMap.get("j_username")[0];
        String password = DigestUtils.md5Hex(parameterMap.get("j_password")[0]);
        Optional<User> userOptional = Optional.empty();
        try {
            userOptional = userDao.getByEmail(login);
        } catch (DaoException e) {
            log.error("Error in userDao when user " + login + " try login", e);
        }
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) return userOptional;
        return Optional.empty();
    }

    private int getCountNewFriend(long id) {
        Relation incoming = null;
        try {
            incoming = relationDao.getIncoming(id);
        } catch (DaoException e) {
            log.error("Error in relationDao when user " + id + " try get count of new friends", e);
        }
        return incoming != null ? incoming.getIdSet().size() : 0;
    }
}