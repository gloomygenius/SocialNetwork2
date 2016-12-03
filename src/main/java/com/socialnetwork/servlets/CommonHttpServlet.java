package com.socialnetwork.servlets;

import com.socialnetwork.dao.*;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import static com.socialnetwork.listeners.Initializer.*;

@Log4j
public abstract class CommonHttpServlet extends HttpServlet {
    public static String CURRENT_USER = "currentUser";
    public static final String INCLUDED_PAGE = "includedPage";
    public static final String ERROR_MSG = "errorMsg";
    static final String SUCCESS_MSG = "successMsg";
    static DialogDao dialogDao;
    static UserDao userDao;
    static RelationDao relationDao;
    static MessageDao messageDao;
    static ProfileDao profileDao;
    @SuppressWarnings("WeakerAccess")
    static boolean isDaoInitialized = false;

    @Override
    public void init() {
        if (!isDaoInitialized) {
            ServletContext servletContext = getServletContext();
            dialogDao = (DialogDao) servletContext.getAttribute(DIALOG_DAO);
            userDao = (UserDao) servletContext.getAttribute(USER_DAO);
            relationDao = (RelationDao) servletContext.getAttribute(RELATION_DAO);
            messageDao = (MessageDao) servletContext.getAttribute(MESSAGE_DAO);
            profileDao = (ProfileDao) servletContext.getAttribute(PROFILE_DAO);
            isDaoInitialized = true;
            log.info("Dao in servlets was initialized successfully");
        }
    }
}