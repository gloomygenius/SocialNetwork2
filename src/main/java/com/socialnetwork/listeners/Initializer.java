package com.socialnetwork.listeners;

import com.socialnetwork.common.DataScriptExecutor;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.h2.*;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Log4j
@WebListener
public class Initializer implements ServletContextListener {
    public static final String USER_DAO = "userDao";
    public static final String PROFILE_DAO = "profileDao";
    public static final String RELATION_DAO = "relationDao";
    public static final String MESSAGE_DAO = "messageDao";
    public static final String DIALOG_DAO = "dialogDao";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ConnectionPool connectionPool;
        String pathToDbConfig = context.getRealPath("/") + "WEB-INF/classes/";
        ConnectionPool.create(pathToDbConfig + "db.properties");
        connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
            log.info("Connection pool successfully initialized");
        } catch (ConnectionPoolException e) {
            log.error("Connection pool initialization error ", e);
        }

        DataScriptExecutor.initSqlData(pathToDbConfig + "H2Init.sql");
        DataScriptExecutor.initSqlData(pathToDbConfig + "usersH2Init.sql");
        log.info("SQL initialization has done successfully");

        context.setAttribute(USER_DAO, new UserDaoImpl(connectionPool));
        context.setAttribute(PROFILE_DAO, new ProfileDaoImpl(connectionPool));
        context.setAttribute(RELATION_DAO, new RelationDaoImpl(connectionPool));
        context.setAttribute(MESSAGE_DAO, new MessageDaoImpl(connectionPool));
        context.setAttribute(DIALOG_DAO, new DialogDaoImpl(connectionPool));
    }
}