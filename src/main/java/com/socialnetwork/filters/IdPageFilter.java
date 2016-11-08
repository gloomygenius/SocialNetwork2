package com.socialnetwork.filters;

import com.socialnetwork.common.HttpFilter;
import com.socialnetwork.dao.ProfileDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Profile;
import com.socialnetwork.models.User;
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

@Log4j
public class IdPageFilter implements HttpFilter {
    private static final String USER_INFO = "userInfo";
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
        log.info("start id filter");
        Matcher matcher = pattern.matcher(request.getRequestURL());
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(CURRENT_USER);
        if (matcher.find()) {
            if (user == null) request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            else {
                log.info("ID in url was found, put into session");
                int id = Integer.parseInt(matcher.group(1));
                Optional<User> refUser = null;
                try {
                    refUser = userDao.getById(id);
                } catch (DaoException e) {
                    e.printStackTrace();
                    // TODO: 08.11.2016 Обработать
                }
                if (refUser.isPresent()) {
                    session.setAttribute(REFERENCE_USER, refUser.get());
                    Long idLoc = refUser.get().getId();
                    Optional<Profile> profile = Optional.empty();
                    try {
                        profile = profileDao.getByUserId(idLoc);
                    } catch (DaoException e) {
                        e.printStackTrace();
                        // TODO: 08.11.2016 Обработать
                    }
                    if (profile.isPresent())
                        session.setAttribute(REFERENCE_PROFILE, profile.get());
                } else {
                    session.setAttribute(REFERENCE_USER, new User(0, null, null, null, null, 0, 0));
                }
                log.info("forward to personal page");
                request.getRequestDispatcher("/jsp/personal_page.jsp").forward(request, response);
            }
        } else
            chain.doFilter(request, response);
    }
}