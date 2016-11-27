package com.socialnetwork.servlets;

import com.socialnetwork.common.NameNormalizer;
import com.socialnetwork.dao.RelationDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.enums.RelationType;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Relation;
import com.socialnetwork.entities.User;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.socialnetwork.dao.enums.RelationType.FRIEND;
import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.listeners.Initializer.RELATION_DAO;
import static com.socialnetwork.listeners.Initializer.USER_DAO;
import static com.socialnetwork.servlets.AuthorizationServlet.NEW_FRIENDS;
import static com.socialnetwork.servlets.ErrorHandler.ERROR_MSG;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.COMMON_ERROR;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.FRIENDS_SEARCH_FAIL;

/**
 * Created by Vasiliy Bobkov on 09.11.2016.
 */
@Log4j
@WebServlet("/friends")
public class FriendsServlet extends HttpServlet {
    public static final String INCLUDED_PAGE = "includedPage";
    private static final String FRIENDS_SET = "friendsSet";
    private static final String RELATION_MAP = "relationMap";
    private static RelationDao relationDao;
    private static UserDao userDao;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        relationDao = (RelationDao) servletContext.getAttribute(RELATION_DAO);
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute(INCLUDED_PAGE, "friends");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(CURRENT_USER);
        Set<Long> friendIdSet = new HashSet<>();
        try {
            String section = request.getParameter("action") == null ? "friends" : request.getParameter("action");
            switch (section) {
                case "incoming":
                    friendIdSet = relationDao.getIncoming(currentUser.getId()).getIdSet();
                    break;
                case "request":
                    friendIdSet = relationDao.getRequest(currentUser.getId()).getIdSet();
                    break;
                case "search":
                    String[] names = request.getParameter("names").split(" ");
                    if (names.length != 2) {
                        request.setAttribute(ERROR_MSG, FRIENDS_SEARCH_FAIL.getPropertyName());
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    } else {
                        names[0] = NameNormalizer.normalize(names[0]);
                        names[1] = NameNormalizer.normalize(names[1]);
                        friendIdSet = searchFriends(names);
                    }
                    break;
                case "remove":
                    long id = Long.parseLong(request.getParameter("id"));
                    relationDao.remove(
                            currentUser.getId(),
                            id,
                            RelationType.getType(Integer.parseInt(request.getParameter("relation"))));
                    friendIdSet = relationDao.getFriends(currentUser.getId()).getIdSet();
                    session.setAttribute(NEW_FRIENDS, getCountNewFriend(currentUser.getId()));
                    break;
                case "add":
                    id = Long.parseLong(request.getParameter("id"));
                    relationDao.add(currentUser.getId(), id, FRIEND);
                    friendIdSet = relationDao.getFriends(currentUser.getId()).getIdSet();
                    session.setAttribute(NEW_FRIENDS, getCountNewFriend(currentUser.getId()));
                    break;
                default:
                    friendIdSet = relationDao.getFriends(currentUser.getId()).getIdSet();
            }
        } catch (DaoException e) {
            log.error("Friends page error", e);
            request.setAttribute(ERROR_MSG, COMMON_ERROR.getPropertyName());
            request.getRequestDispatcher("/error").forward(request, response);
        }

        loadUsersAndRelations(request, response, friendIdSet);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void loadUsersAndRelations(HttpServletRequest request, HttpServletResponse response, Set<Long> friendIdSet) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
        int offset = getIntParameter("offset", request, 0);
        int limit = getIntParameter("limit", request, 10);

        Optional<User> userOptional;
        Map<Long, Integer> relationSet = new HashMap<>();
        Set<User> friendSet = new TreeSet<>();
        int count = 0;
        boolean hasNextPage = false;
        for (long id : friendIdSet) {
            if (count < offset) {
                count++;
                continue;
            }
            if (count >= offset + limit) {
                count++;
                hasNextPage = true;
                continue;
            }
            count++;
            try {
                userOptional = userDao.getById(id);
                if (userOptional.isPresent()) {
                    friendSet.add(userOptional.get());
                    relationSet.put(id, relationDao.getRelationBetween(currentUser.getId(), id));
                }
            } catch (DaoException e) {
                log.error("Getting friends of id" + currentUser.getId() + " error", e);
                request.setAttribute(ERROR_MSG, COMMON_ERROR.getPropertyName());
                request.getRequestDispatcher("/error").forward(request, response);
            }

        }
        request.setAttribute("hasNextPage", hasNextPage);
        request.setAttribute(RELATION_MAP, relationSet);
        request.setAttribute(FRIENDS_SET, friendSet);
    }

    private int getIntParameter(String paramName, HttpServletRequest request, int defValue) {
        int value = defValue;
        if (request.getParameter(paramName) != null) {
            log.debug(paramName + " " + Integer.parseInt(request.getParameter(paramName)));
            value = Integer.parseInt(request.getParameter(paramName));
        }

        request.setAttribute(paramName, value);
        return value;
    }

    private Set<Long> searchFriends(String names[]) throws DaoException {
        Set<Long> idSet;
        idSet = userDao.getByNames(names[0], names[1]);
        return idSet;
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