package com.socialnetwork.servlets;

import com.socialnetwork.dao.RelationDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.User;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.socialnetwork.dao.enums.RelationType.FRIEND;
import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.listeners.Initializer.RELATION_DAO;
import static com.socialnetwork.listeners.Initializer.USER_DAO;
import static com.socialnetwork.servlets.AuthorizationServlet.NEW_FRIENDS;

/**
 * Created by Vasiliy Bobkov on 09.11.2016.
 */
@Log4j
@WebServlet("/friends")
public class FriendsServlet extends HttpServlet {
    public static final String INCLUDED_PAGE = "includedPage";
    public static final String FRIENDS_SET = "friendsSet";

    private static RelationDao relationDao;
    private static UserDao userDao;
    private static final int maxFriendPerPage = 10;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        relationDao = (RelationDao) servletContext.getAttribute(RELATION_DAO);
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("delete") != null || request.getParameter("add") != null) {
            HttpSession session = request.getSession(true);
            User currentUser = (User) session.getAttribute(CURRENT_USER);

            if (request.getParameter("delete") != null) {
                try {
                    long id = Long.parseLong(request.getParameter("delete"));
                    relationDao.remove(currentUser.getId(), id, FRIEND);
                } catch (DaoException e) {
                    log.warn("Error when user try to remove relation");
                    e.printStackTrace();
                }
            }
            if (request.getParameter("add") != null) {
                try {
                    long id = Long.parseLong(request.getParameter("add"));
                    relationDao.add(currentUser.getId(), id, FRIEND);
                    int count = (int) session.getAttribute(NEW_FRIENDS);
                    session.setAttribute(NEW_FRIENDS,--count);
                } catch (DaoException e) {
                    log.warn("Error when user try to add friend");
                    e.printStackTrace();
                }
            }
            response.sendRedirect("/friends");
        } else
            doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute(INCLUDED_PAGE, "friends");
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute(CURRENT_USER);
        Set<Long> friendIdSet = new HashSet<>();
        try {
            if (request.getParameter("section") != null) {
                if (request.getParameter("section").equals("incoming")) {
                    friendIdSet = relationDao.getIncoming(currentUser.getId()).getIdSet();
                }
                if (request.getParameter("section").equals("request")) {
                    friendIdSet = relationDao.getRequest(currentUser.getId()).getIdSet();
                }
            } else friendIdSet = relationDao.getFriends(currentUser.getId()).getIdSet();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        int since = request.getParameter("since") != null ? Integer.parseInt(request.getParameter("since")) : 0;
        Optional<User> userOptional = Optional.empty();
        Set<User> friendSet = new HashSet<>();
        int count = 0;
        for (long id : friendIdSet) {
            if (count < since) {
                count++;
                continue;
            }
            try {
                userOptional = userDao.getById(id);
                if (userOptional.isPresent()) {
                    friendSet.add(userOptional.get());
                }
            } catch (DaoException e) {
                e.printStackTrace();
            }
            if (count >= since + maxFriendPerPage) break;
        }
        request.setAttribute(FRIENDS_SET, friendSet);
        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            // TODO: 09.11.2016 Обработать
        }
    }
}