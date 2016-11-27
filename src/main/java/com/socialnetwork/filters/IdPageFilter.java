package com.socialnetwork.filters;

import com.socialnetwork.common.HttpFilter;
import com.socialnetwork.dao.ProfileDao;
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

import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.listeners.Initializer.PROFILE_DAO;
import static com.socialnetwork.listeners.Initializer.USER_DAO;
import static com.socialnetwork.servlets.ErrorHandler.ERROR_MSG;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.NOT_AUTH;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.USER_NOT_FOUND;
import static com.socialnetwork.servlets.FriendsServlet.INCLUDED_PAGE;

@Log4j
public class IdPageFilter implements HttpFilter {
    private UserDao userDao;
    private ProfileDao profileDao;
    private Pattern pattern;
    public final static String REFERENCE_USER = "referenceUser";
    public final static String REFERENCE_PROFILE = "referenceProfile";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
        profileDao = (ProfileDao) servletContext.getAttribute(PROFILE_DAO);
        pattern = Pattern.compile("\\/id([\\d]+)$");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Matcher matcher = pattern.matcher(request.getRequestURL());
        HttpSession session = request.getSession(true);
        if (matcher.find()) {
            if (session.getAttribute(CURRENT_USER) == null) {
                request.setAttribute(INCLUDED_PAGE, "login");
                request.setAttribute(ERROR_MSG, NOT_AUTH.getPropertyName());
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                int id = Integer.parseInt(matcher.group(1));
                Optional<User> refUser = Optional.empty();
                try {
                    refUser = userDao.getById(id);
                } catch (DaoException e) {
                    log.error("UserDao exception in IdFilter", e);
                    request.setAttribute(ERROR_MSG, USER_NOT_FOUND.getPropertyName());
                    request.getRequestDispatcher("/error").forward(request, response);
                }
                if (refUser.isPresent()) {
                    session.setAttribute(REFERENCE_USER, refUser.get());
                    Long idLoc = refUser.get().getId();
                    Optional<Profile> profile = Optional.empty();
                    try {
                        profile = profileDao.getByUserId(idLoc);
                    } catch (DaoException e) {
                        log.error("ProfileDao exception in IdFilter", e);
                        request.setAttribute(ERROR_MSG, USER_NOT_FOUND.getPropertyName());
                        request.getRequestDispatcher("/error").forward(request, response);
                    }
                    if (profile.isPresent())
                        session.setAttribute(REFERENCE_PROFILE, profile.get());
                } else {
                    request.setAttribute(ERROR_MSG, USER_NOT_FOUND.getPropertyName());
                    request.getRequestDispatcher("/error").forward(request, response);
                }
                request.setAttribute(INCLUDED_PAGE, "profile");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } else
            chain.doFilter(request, response);
    }
}