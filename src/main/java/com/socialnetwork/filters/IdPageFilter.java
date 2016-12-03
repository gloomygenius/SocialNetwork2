package com.socialnetwork.filters;

import com.socialnetwork.common.HttpFilter;
import com.socialnetwork.dao.ProfileDao;
import com.socialnetwork.dao.RelationDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Profile;
import com.socialnetwork.entities.User;
import lombok.extern.log4j.Log4j;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.socialnetwork.listeners.Initializer.*;
import static com.socialnetwork.servlets.CommonHttpServlet.CURRENT_USER;
import static com.socialnetwork.servlets.ErrorHandler.ERROR_MSG;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.NOT_AUTH;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.USER_NOT_FOUND;
import static com.socialnetwork.servlets.FriendsServlet.INCLUDED_PAGE;

@Log4j
public class IdPageFilter implements HttpFilter {
    private static final String RELATION = "relation";
    private UserDao userDao;
    private ProfileDao profileDao;
    private Pattern pattern;
    private final static String USER = "user";
    private final static String PROFILE = "profile";
    private RelationDao relationDao;
    private User currentUser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
        profileDao = (ProfileDao) servletContext.getAttribute(PROFILE_DAO);
        pattern = Pattern.compile("\\/id([\\d]+)$");
        relationDao = (RelationDao) servletContext.getAttribute(RELATION_DAO);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Matcher matcher = pattern.matcher(request.getRequestURL());
        HttpSession session = request.getSession(true);
        currentUser = (User) session.getAttribute(CURRENT_USER);
        if (matcher.find()) {
            if (currentUser == null) {
                request.setAttribute(INCLUDED_PAGE, "login");
                request.setAttribute(ERROR_MSG, NOT_AUTH.getPropertyName());
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                int id = Integer.parseInt(matcher.group(1));
                Optional<User> refUser;
                try {
                    refUser = userDao.getById(id);
                    if (refUser.isPresent()) {
                        User user = refUser.get();
                        request.setAttribute(USER, user);
                        Long idLoc = user.getId();
                        Optional<Profile> profile;
                        profile = profileDao.getByUserId(idLoc);
                        if (user.getId() != currentUser.getId())
                            request.setAttribute(RELATION, relationDao.getRelationBetween(currentUser.getId(), user.getId()));
                        profile.ifPresent(profile1 -> request.setAttribute(PROFILE, profile1));
                        request.setAttribute(INCLUDED_PAGE,"profile");
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    } else {
                        request.setAttribute(ERROR_MSG, USER_NOT_FOUND.getPropertyName());
                        request.getRequestDispatcher("/error").forward(request, response);
                    }
                } catch (DaoException e) {
                    request.setAttribute(ERROR_MSG, USER_NOT_FOUND.getPropertyName());
                    log.error("Dao exception in IdFilter", e);
                    request.getRequestDispatcher("/error").forward(request, response);
                }
            }
        } else
            chain.doFilter(request, response);
    }
}