package com.socialnetwork.servlets;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.h2.UserDaoImpl;
import com.socialnetwork.entities.User;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.socialnetwork.dao.enums.Gender.FEMALE;
import static com.socialnetwork.dao.enums.Gender.MALE;
import static com.socialnetwork.dao.enums.Roles.USER;
import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;

/**
 * Created by Vasiliy Bobkov on 13.11.2016.
 */
@Log4j
public class RegistrationServlet extends HttpServlet {

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
        String password = (String) request.getParameter("password");
        int gender = request.getParameter("gender").equals("male") ? MALE.ordinal() : FEMALE.ordinal();

        try {
            userDao.add(new User(0, email, password, firstName, lastName, gender, USER.ordinal()));
            Optional<User> userOptional = userDao.getByEmail(email);
            if (userOptional.isPresent()) {
                log.info("New user registered!");
                request.getSession().setAttribute(CURRENT_USER, userOptional.get());
            } else {
                log.error("Add new user failed");
            }
        } catch (Exception e) {
            log.error("Error when user registering", e);
            nextUrl = "/error.jsp";
        }
        try {
            response.sendRedirect(nextUrl);
        } catch (IOException e) {
            log.warn("Redirect error", e);
        }
    }
}