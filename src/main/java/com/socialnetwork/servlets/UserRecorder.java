package com.socialnetwork.servlets;


import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.h2.UserDaoImpl;
import com.socialnetwork.entities.User;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.socialnetwork.dao.enums.Roles.USER;

@Log4j
public class UserRecorder extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }
    @SneakyThrows
    private void requestProcess(HttpServletRequest request, HttpServletResponse response) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        UserDao userDao = new UserDaoImpl(connectionPool);
        request.setCharacterEncoding("UTF-8");
        String nextUrl = "/";
        String firstName = (String) request.getParameter("first_name");
        String lastName = (String) request.getParameter("last_name");
        String email = (String) request.getParameter("email");
        String password = DigestUtils.md5Hex((String) request.getParameter("password"));
        // TODO: 08.11.2016  допилить
        int gender = Integer.parseInt(request.getParameter("gender"));
        int role = USER.ordinal();
        //Boolean male = true;

        try {
            userDao.add(new User(0, email, password, firstName, lastName, gender, role));
            log.info("New user registered!");
        } catch (RuntimeException e) {
            log.error("Error when user registering", e);
            nextUrl = "/error.jsp";
        }
        try {
            response.sendRedirect(nextUrl);
        } catch (IOException e) {
            log.warn("Redirect error",e);
        }
    }
}